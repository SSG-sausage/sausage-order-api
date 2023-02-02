package com.ssg.sausageorderapi.order.service;

import com.ssg.sausageorderapi.common.client.internal.CartShareApiClient;
import com.ssg.sausageorderapi.common.client.internal.dto.response.CartShareItemListResponse.CartShareItemInfo;
import com.ssg.sausageorderapi.common.kafka.service.CartShareProducerService;
import com.ssg.sausageorderapi.order.dto.response.CartShareTmpOrdFindResponse;
import com.ssg.sausageorderapi.order.dto.response.CartShareTmpOrdSaveResponse;
import com.ssg.sausageorderapi.order.entity.CartShareTmpOrd;
import com.ssg.sausageorderapi.order.entity.CartShareTmpOrdItem;
import com.ssg.sausageorderapi.order.entity.ShppCd;
import com.ssg.sausageorderapi.order.entity.TmpOrdStatCd;
import com.ssg.sausageorderapi.order.repository.CartShareTmpOrdItemRepository;
import com.ssg.sausageorderapi.order.repository.CartShareTmpOrdRepository;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartShareTmpOrdService {


    private final CartShareTmpOrdRepository cartShareTmpOrdRepository;

    private final CartShareTmpOrdItemRepository cartShareTmpOrdItemRepository;

    private final CartShareApiClient cartShareClient;

    private final CartShareTmpOrdUtilService cartShareTmpOrdUtilService;

    private final CartShareProducerService cartShareProducerService;


    @Transactional
    public CartShareTmpOrdFindResponse findCartShareTmpOrdInProgress(Long mbrId, Long cartShareId) {

        cartShareClient.validateCartShareMbr(cartShareId, mbrId);

        CartShareTmpOrd cartShareTmpOrd = cartShareTmpOrdUtilService.findCartShareTmpOrdInProgress(cartShareId);

        List<CartShareTmpOrdItem> cartShareTmpOrdItemList = cartShareTmpOrdItemRepository.findAllByCartShareTmpOrdId(
                cartShareTmpOrd.getCartShareTmpOrdId());

        return CartShareTmpOrdFindResponse.of(cartShareTmpOrd, cartShareTmpOrdItemList);
    }

    @Transactional
    public CartShareTmpOrdSaveResponse saveCartShareTmpOrd(Long mbrId, Long cartShareId) {

        cartShareClient.validateCartShareMastr(cartShareId, mbrId);

        CartShareTmpOrd cartShareTmpOrd = cartShareTmpOrdRepository.save(CartShareTmpOrd.newInstance(cartShareId));

        List<CartShareItemInfo> cartShareItemList = cartShareClient.findCartShareItemList(cartShareId).getData()
                .getCartShareItemList();

        List<CartShareTmpOrdItem> cartShareTmpOrdItems = cartShareItemList.stream()
                .map(cartShareItemInfo -> CartShareTmpOrdItem.newInstance(cartShareItemInfo,
                        cartShareTmpOrd.getCartShareTmpOrdId()))
                .collect(Collectors.toList());

        cartShareTmpOrdItemRepository.saveAll(cartShareTmpOrdItems);

        // 총결제 금액 변경
        int ttlPaymtAmt = calculateTtlPaymtAmt(cartShareTmpOrdItems);
        cartShareTmpOrd.changeTtlPaymtAmt(ttlPaymtAmt);

        cartShareProducerService.updateEditPsblYn(cartShareId, false);

        return CartShareTmpOrdSaveResponse.of(cartShareTmpOrd);
    }

    @Transactional
    public void cancelCartShareTmpOrd(Long mbrId, Long cartShareId) {

        cartShareClient.validateCartShareMastr(cartShareId, mbrId);

        CartShareTmpOrd cartShareTmpOrd = cartShareTmpOrdUtilService.findCartShareTmpOrdInProgress(cartShareId);

        cartShareTmpOrd.changeTmpOrdStat(TmpOrdStatCd.CANCELED);

        cartShareProducerService.updateEditPsblYn(cartShareId, true);
    }

    private int calculateTtlPaymtAmt(List<CartShareTmpOrdItem> cartShareOdrItems) {

        int ttlPaymtAmt = 0, ttlShppcst = 0;
        HashMap<String, Integer> shppCdMap = new HashMap<>();

        for (CartShareTmpOrdItem cartShareOdrItem : cartShareOdrItems) {

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
