package com.ssg.sausageorderapi.order.service;


import com.ssg.sausageorderapi.common.client.internal.CartShareApiClientMock;
import com.ssg.sausageorderapi.common.client.internal.CartShareCalApiClientMock;
import com.ssg.sausageorderapi.common.client.internal.dto.request.CartShareCalSaveRequest;
import com.ssg.sausageorderapi.common.kafka.service.CartShareProducerService;
import com.ssg.sausageorderapi.order.dto.response.CartShareOrdFindListResponse;
import com.ssg.sausageorderapi.order.dto.response.CartShareOrdFindResponse;
import com.ssg.sausageorderapi.order.entity.CartShareOdr;
import com.ssg.sausageorderapi.order.entity.CartShareOdrItem;
import com.ssg.sausageorderapi.order.entity.CartShareTmpOdr;
import com.ssg.sausageorderapi.order.entity.CartShareTmpOdrItem;
import com.ssg.sausageorderapi.order.entity.ShppCd;
import com.ssg.sausageorderapi.order.repository.CartShareOrdItemRepository;
import com.ssg.sausageorderapi.order.repository.CartShareOrdRepository;
import java.util.HashMap;
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

    private final CartShareApiClientMock cartShareClient;

    private final CartShareCalApiClientMock cartShareCalApiClient;

    private final CartShareProducerService cartShareProducerService;

    public CartShareCalSaveRequest saveCartShareOrdFromTmpOrd(Long mbrId, Long cartShareId) {

        // validate 'isFound' and 'isCartShareMaster' (internal api)
        cartShareClient.validateCartShareMastr(mbrId, cartShareId);

        CartShareTmpOdr cartShareTmpOdr = cartShareTmpOrdUtilService.findCartShareTmpOrdInProgress(cartShareId);

        // Ord created from TmpOrd
        CartShareOdr cartShareOdr = CartShareOdr.newInstance(cartShareTmpOdr);
        cartShareOrdRepository.save(cartShareOdr);

        // find CartShareTmpOdrItem from TmpOrdId
        List<CartShareTmpOdrItem> cartShareTmpOdrItemList = cartShareTmpOrdUtilService.findCartShareTmpOrdItemByTmpOrdId(
                cartShareOdr.getCartShareTmpOrdId());

        // covert CartShareTmpOdrItem to CartShareOdrItem
        List<CartShareOdrItem> cartShareOdrItems = cartShareTmpOdrItemList.stream()
                .map(cartShareTmpOdrItem -> CartShareOdrItem.newInstance(cartShareOdr.getCartShareOrdId(),
                        cartShareTmpOdrItem))
                .collect(Collectors.toList());

        cartShareOrdItemRepository.saveAll(cartShareOdrItems);

        // calculate ttlPaymtAmt and save
        int ttlPaymtAmt = calculateTtlPaymtAmt(cartShareOdrItems);
        cartShareOdr.changeTtlPaymtAmt(ttlPaymtAmt);

        // change tmpOrdStatCd to Completed
        cartShareTmpOrdUtilService.changeTmpOrdStatCdToCompleted(cartShareTmpOdr);

        // produce delete cartshareItemList
        cartShareProducerService.deleteCartShareItemList(cartShareId);

        // produce update cartshare editPsblYn to True api
        cartShareProducerService.updateEditPsblYn(cartShareId, true);

        // *to be added, produce 주문 완료 알림 생성 이벤트

        // invoke save cartShareCal api
        Long cartShareCalId = cartShareCalApiClient.saveCartShareCal(
                CartShareCalSaveRequest.of(cartShareOdr.getCartShareOrdId())).getData().getCartShareCalId();

        cartShareOdr.changeCartShareCalId(cartShareCalId);

        return CartShareCalSaveRequest.of(cartShareCalId);
    }

    public CartShareOrdFindListResponse findCartShareOrderList(Long mbrId, Long cartShareId) {

        // validate 'isFound' and 'isAccessibleCartShareByMbr'
        cartShareClient.validateCartShareMbr(mbrId, cartShareId);

        List<CartShareOdr> cartShareOdrList = cartShareOrdRepository.findAllByCartShareId(cartShareId);

        // * to be added, get DUTCH_PAY_ST_YN

        return CartShareOrdFindListResponse.of(cartShareOdrList);
    }

    public CartShareOrdFindResponse findCartShareOrder(Long mbrId, Long cartShareId, Long cartShareOrdId) {

        // validate 'isFound' and 'isAccessibleCartShareByMbr'
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
}
