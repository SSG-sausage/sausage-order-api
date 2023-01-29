package com.ssg.sausageorderapi.order.service;


import com.ssg.sausageorderapi.common.client.internal.CartShareApiClient;
import com.ssg.sausageorderapi.common.client.internal.CartShareCalApiClient;
import com.ssg.sausageorderapi.common.client.internal.dto.request.CartShareCalSaveRequest;
import com.ssg.sausageorderapi.common.client.internal.dto.response.CartShareMbrIdListResponse;
import com.ssg.sausageorderapi.common.kafka.service.CartShareProducerService;
import com.ssg.sausageorderapi.order.dto.response.CartShareOrdFindListResponse;
import com.ssg.sausageorderapi.order.dto.response.CartShareOrdFindResponse;
import com.ssg.sausageorderapi.order.entity.CartShareOdr;
import com.ssg.sausageorderapi.order.entity.CartShareOdrItem;
import com.ssg.sausageorderapi.order.entity.CartShareTmpOdr;
import com.ssg.sausageorderapi.order.entity.CartShareTmpOdrItem;
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

    private final CartShareTmpOrdUtilService cartShareTmpOrdUtilService;

    private final CartShareApiClient cartShareClient;

    private final CartShareCalApiClient cartShareCalApiClient;

    private final CartShareProducerService cartShareProducerService;

    public void saveCartShareOrdFromTmpOrd(Long mbrId, Long cartShareId) {

        cartShareClient.validateCartShareMastr(mbrId, cartShareId);

        CartShareTmpOdr cartShareTmpOdr = cartShareTmpOrdUtilService.findCartShareTmpOrdInProgress(cartShareId);

        // 임시주문으로부터 주문 저장
        CartShareOdr cartShareOdr = CartShareOdr.newInstance(cartShareTmpOdr);

        cartShareOrdRepository.save(cartShareOdr);

        // 임시주문상품으로부터 주문상품 저장
        List<CartShareTmpOdrItem> cartShareTmpOdrItemList = cartShareTmpOrdUtilService.findCartShareTmpOrdItemByTmpOrdId(
                cartShareOdr.getCartShareTmpOrdId());

        List<CartShareOdrItem> cartShareOdrItems = cartShareTmpOdrItemList.stream()
                .map(cartShareTmpOdrItem -> CartShareOdrItem.newInstance(cartShareOdr.getCartShareOrdId(),
                        cartShareTmpOdrItem))
                .collect(Collectors.toList());

        cartShareOrdItemRepository.saveAll(cartShareOdrItems);

        // 총 결제 금액 변경
        int ttlPaymtAmt = calculateTtlPaymtAmt(cartShareOdrItems);
        cartShareOdr.changeTtlPaymtAmt(ttlPaymtAmt);

        // 주문 완료 이후, 장바구니 후속 이벤트 발생
        cartShareTmpOrdUtilService.changeTmpOrdStatCd(cartShareTmpOdr, TmpOrdStatCd.CANCELED);
        cartShareProducerService.deleteCartShareItemList(cartShareId);
        cartShareProducerService.updateEditPsblYn(cartShareId, true);

        // *to be added, produce 주문 완료 알림 생성 이벤트

        CartShareCalSaveRequest cartShareCalSaveRequest = createCartShareCalSaveRequest(cartShareOdr);
        Long cartShareCalId = cartShareCalApiClient.saveCartShareCal(cartShareCalSaveRequest).getData()
                .getCartShareCalId();

        cartShareOdr.changeCartShareCalId(cartShareCalId);
    }


    public CartShareOrdFindListResponse findCartShareOrderList(Long mbrId, Long cartShareId) {

        cartShareClient.validateCartShareMbr(mbrId, cartShareId);

        List<CartShareOdr> cartShareOdrList = cartShareOrdRepository.findAllByCartShareId(cartShareId);

        // * to be added, get DUTCH_PAY_ST_YN

        return CartShareOrdFindListResponse.of(cartShareOdrList);
    }

    public CartShareOrdFindResponse findCartShareOrder(Long mbrId, Long cartShareId, Long cartShareOrdId) {

        cartShareClient.validateCartShareMbr(mbrId, cartShareId);

        CartShareOdr cartShareOdr = cartShareOrdUtilService.findById(cartShareOrdId);

        List<CartShareOdrItem> cartShareOdrItemList = cartShareOrdItemRepository.findAllByCartShareOrdId(
                cartShareOdr.getCartShareId());

        return CartShareOrdFindResponse.of(cartShareOdr, cartShareOdrItemList);
    }

    private int calculateTtlPaymtAmt(List<CartShareOdrItem> cartShareOdrItems) {

        int ttlPaymtAmt = 0, ttlShppcst = 0;
        HashMap<String, Integer> shppCdMap = new HashMap<>();

        for (CartShareOdrItem cartShareOdrItem : cartShareOdrItems) {

            ttlPaymtAmt += cartShareOdrItem.getPaymtAmt();

            String shppName = cartShareOdrItem.getShppCd().name();
            shppCdMap.put(shppName, shppCdMap.getOrDefault(shppName, 0)
                    + cartShareOdrItem.getItemAmt() * cartShareOdrItem.getItemQty());
        }

        for (String shppCd : shppCdMap.keySet()) {
            ttlShppcst += ShppCd.calculateShppCst(ShppCd.valueOf(shppCd), shppCdMap.get(shppCd));
        }

        return ttlPaymtAmt + ttlShppcst;
    }

    private CartShareCalSaveRequest createCartShareCalSaveRequest(CartShareOdr cartShareOdr) {

        CartShareMbrIdListResponse cartShareMbrIdListResponse = cartShareClient.findCartShareMbrIdList(
                cartShareOdr.getCartShareId()).getData();

        return CartShareCalSaveRequest.builder()
                .cartShareId(cartShareOdr.getCartShareId())
                .cartShareOrdId(cartShareOdr.getCartShareOrdId())
                .mbrIdList(new HashSet<>(cartShareMbrIdListResponse.getMbrIdList()))
                .mastrMbrId(cartShareMbrIdListResponse.getMastrMbrId())
                .ttlPaymtAmt(cartShareOdr.getTtlPaymtAmt()).build();
    }
}
