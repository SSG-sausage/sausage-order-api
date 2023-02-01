package com.ssg.sausageorderapi.order.service;


import com.ssg.sausageorderapi.common.client.internal.CartShareApiClient;
import com.ssg.sausageorderapi.common.client.internal.CartShareCalApiClient;
import com.ssg.sausageorderapi.common.client.internal.dto.request.CartShareCalSaveRequest;
import com.ssg.sausageorderapi.common.client.internal.dto.response.CartShareMbrIdListResponse;
import com.ssg.sausageorderapi.common.kafka.service.CartShareProducerService;
import com.ssg.sausageorderapi.order.dto.response.CartShareOrdFindListResponse;
import com.ssg.sausageorderapi.order.dto.response.CartShareOrdFindResponse;
import com.ssg.sausageorderapi.order.dto.response.CartShareOrdSaveResponse;
import com.ssg.sausageorderapi.order.entity.CartShareOrd;
import com.ssg.sausageorderapi.order.entity.CartShareOrdItem;
import com.ssg.sausageorderapi.order.entity.CartShareTmpOrd;
import com.ssg.sausageorderapi.order.entity.CartShareTmpOrdItem;
import com.ssg.sausageorderapi.order.entity.ShppCd;
import com.ssg.sausageorderapi.order.entity.TmpOrdStatCd;
import com.ssg.sausageorderapi.order.repository.CartShareOrdItemRepository;
import com.ssg.sausageorderapi.order.repository.CartShareOrdRepository;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CartShareOrdService {

    private final CartShareOrdRepository cartShareOrdRepository;

    private final CartShareOrdItemRepository cartShareOrdItemRepository;

    private final CartShareOrdUtilService cartShareOrdUtilService;

    private final CartShareOrdItemUtilService cartShareOrdItemUtilService;

    private final CartShareTmpOrdUtilService cartShareTmpOrdUtilService;

    private final CartShareApiClient cartShareApiClient;

    private final CartShareCalApiClient cartShareCalApiClient;

    private final CartShareProducerService cartShareProducerService;

    private final CartShareOrdCreateNotiService cartShareOrdCreateNotiService;

    public CartShareOrdSaveResponse saveCartShareOrdFromTmpOrd(Long mbrId, Long cartShareId) {

        cartShareApiClient.validateCartShareMastr(cartShareId, mbrId);

        CartShareTmpOrd cartShareTmpOrd = cartShareTmpOrdUtilService.findCartShareTmpOrdInProgress(cartShareId);

        // 임시주문으로부터 주문 저장
        CartShareOrd cartShareOrd = CartShareOrd.newInstance(cartShareTmpOrd);

        cartShareOrdRepository.save(cartShareOrd);

        // 임시주문상품으로부터 주문상품 저장
        List<CartShareTmpOrdItem> cartShareTmpOrdItemList = cartShareTmpOrdUtilService.findCartShareTmpOrdItemByTmpOrdId(
                cartShareOrd.getCartShareTmpOrdId());

        List<CartShareOrdItem> cartShareOrdItems = cartShareTmpOrdItemList.stream()
                .map(cartShareTmpOdrItem -> CartShareOrdItem.newInstance(cartShareOrd.getCartShareOrdId(),
                        cartShareTmpOdrItem))
                .collect(Collectors.toList());

        cartShareOrdItemRepository.saveAll(cartShareOrdItems);

        // 총 결제 금액 변경
        int ttlPaymtAmt = calculateTtlPaymtAmt(cartShareOrdItems);
        cartShareOrd.changeTtlPaymtAmt(ttlPaymtAmt);

        // 주문 완료 이후, 장바구니 후속 이벤트 발생
        cartShareTmpOrdUtilService.changeTmpOrdStatCd(cartShareTmpOrd, TmpOrdStatCd.CANCELED);
        cartShareProducerService.deleteCartShareItemList(cartShareId);
        cartShareProducerService.updateEditPsblYn(cartShareId, true);
        cartShareOrdCreateNotiService.createOrdSaveNoti(cartShareOrd, cartShareId, mbrId, cartShareOrdItems);

        CartShareCalSaveRequest cartShareCalSaveRequest = createCartShareCalSaveRequest(cartShareOrd);
        Long cartShareCalId = cartShareCalApiClient.saveCartShareCal(cartShareCalSaveRequest).getData()
                .getCartShareCalId();

        cartShareOrd.changeCartShareCalId(cartShareCalId);

        return CartShareOrdSaveResponse.of(cartShareOrd);
    }


    public CartShareOrdFindListResponse findCartShareOrderList(Long mbrId, Long cartShareId) {

        cartShareApiClient.validateCartShareMbr(cartShareId, mbrId);

        List<CartShareOrd> cartShareOrdList = cartShareOrdUtilService.findListByCartShareId(cartShareId);

        return CartShareOrdFindListResponse.of(cartShareOrdList);
    }

    public CartShareOrdFindResponse findCartShareOrder(Long mbrId, Long cartShareId, Long cartShareOrdId) {

        cartShareApiClient.validateCartShareMbr(cartShareId, mbrId);

        CartShareOrd cartShareOrd = cartShareOrdUtilService.findById(cartShareOrdId);

        List<CartShareOrdItem> cartShareOrdItemList = cartShareOrdItemUtilService.findListByCartShareOrdId(
                cartShareOrd.getCartShareOrdId());

        return CartShareOrdFindResponse.of(cartShareOrd, cartShareOrdItemList);
    }

    private int calculateTtlPaymtAmt(List<CartShareOrdItem> cartShareOrdItems) {

        int ttlPaymtAmt = 0, ttlShppcst = 0;
        HashMap<String, Integer> shppCdMap = new HashMap<>();

        for (CartShareOrdItem cartShareOrdItem : cartShareOrdItems) {

            ttlPaymtAmt += cartShareOrdItem.getPaymtAmt();

            String shppName = cartShareOrdItem.getShppCd().name();
            shppCdMap.put(shppName, shppCdMap.getOrDefault(shppName, 0)
                    + cartShareOrdItem.getItemAmt() * cartShareOrdItem.getItemQty());
        }

        for (String shppCd : shppCdMap.keySet()) {
            ttlShppcst += ShppCd.calculateShppCst(ShppCd.valueOf(shppCd), shppCdMap.get(shppCd));
        }

        return ttlPaymtAmt + ttlShppcst;
    }

    private CartShareCalSaveRequest createCartShareCalSaveRequest(CartShareOrd cartShareOrd) {

        CartShareMbrIdListResponse cartShareMbrIdListResponse = cartShareApiClient.findCartShareMbrIdList(
                cartShareOrd.getCartShareId()).getData();

        return CartShareCalSaveRequest.builder()
                .cartShareId(cartShareOrd.getCartShareId())
                .cartShareOrdId(cartShareOrd.getCartShareOrdId())
                .cartShareOrdNo(cartShareOrd.getCartShareOrdNo())
                .mbrIdList(new HashSet<>(cartShareMbrIdListResponse.getMbrIdList()))
                .mastrMbrId(cartShareMbrIdListResponse.getMastrMbrId())
                .cartShareNm(cartShareMbrIdListResponse.getCartShareNm())
                .ttlPaymtAmt(cartShareOrd.getTtlPaymtAmt()).build();
    }
}
