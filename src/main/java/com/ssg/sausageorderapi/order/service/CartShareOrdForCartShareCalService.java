package com.ssg.sausageorderapi.order.service;


import com.ssg.sausageorderapi.common.client.internal.CartShareApiClient;
import com.ssg.sausageorderapi.order.dto.response.CartShareOrdFindDetailForCartShareCalResponse;
import com.ssg.sausageorderapi.order.dto.response.CartShareOrdFindDetailForCartShareCalResponse.CartShareOrdAmtInfo;
import com.ssg.sausageorderapi.order.dto.response.CartShareOrdFindDetailForCartShareCalResponse.CartShareOrdShppInfo;
import com.ssg.sausageorderapi.order.entity.CartShareOdr;
import com.ssg.sausageorderapi.order.entity.CartShareOdrItem;
import com.ssg.sausageorderapi.order.repository.CartShareOrdItemRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CartShareOrdForCartShareCalService {

    private final CartShareOrdItemRepository cartShareOrdItemRepository;

    private final CartShareOrdUtilService cartShareOrdUtilService;

    private final CartShareApiClient cartShareClient;

    public CartShareOrdFindDetailForCartShareCalResponse findCartShareOrdDetail(Long cartShareOrdId) {

        CartShareOdr cartShareOdr = cartShareOrdUtilService.findById(cartShareOrdId);

        List<CartShareOdrItem> cartShareOdrItemList = cartShareOrdItemRepository.findAllByCartShareOrdId(
                cartShareOrdId);

        List<Long> mbrIdList = cartShareClient.findCartShareMbrIdList(cartShareOdr.getCartShareId()).getData()
                .getMbrIdList();

        HashSet<Long> cartShareMbrIdSet = new HashSet<>(mbrIdList);

        HashMap<String, CartShareOrdShppInfo> cartShareOrdShppInfoMap = new HashMap<>();
        HashMap<Long, CartShareOrdAmtInfo> cartShareOrdAmtInfoMap = new HashMap<>();

        int commAmt = 0;

        for (CartShareOdrItem cartShareOdrItem : cartShareOdrItemList) {

            commAmt = calculateCommAmt(commAmt, cartShareOdrItem);

            calculateShppCst(cartShareOrdShppInfoMap, cartShareOdrItem, mbrIdList);

            calculateOrdAmt(cartShareOrdAmtInfoMap, cartShareOdrItem);
        }

        // add zero paymtamt mbr
        cartShareMbrIdSet.removeAll(cartShareOrdAmtInfoMap.keySet());
        cartShareMbrIdSet.forEach(mbrId -> cartShareOrdAmtInfoMap.put(mbrId, CartShareOrdAmtInfo.of(mbrId, 0)));

        return CartShareOrdFindDetailForCartShareCalResponse.builder()
                .commAmt(commAmt)
                .shppInfoList(new ArrayList<>(cartShareOrdShppInfoMap.values()))
                .ordInfoList(new ArrayList<>(cartShareOrdAmtInfoMap.values())).build();
    }

    private int calculateCommAmt(int commAmt, CartShareOdrItem cartShareOdrItem) {
        if (cartShareOdrItem.getCommYn()) {
            commAmt += cartShareOdrItem.getPaymtAmt();
        }
        return commAmt;
    }

    private void calculateShppCst(HashMap<String, CartShareOrdShppInfo> cartShareOrdShppInfoMap,
            CartShareOdrItem cartShareOdrItem, List<Long> mbrIdList) {

        CartShareOrdShppInfo cartShareOrdShppInfo = cartShareOrdShppInfoMap.getOrDefault(
                cartShareOdrItem.getShppCd().name(),
                CartShareOrdShppInfo.of(cartShareOdrItem.getShppCd()));

        if (cartShareOdrItem.getCommYn()) {
            cartShareOrdShppInfo.addMbrIdList(new HashSet<>(mbrIdList));
        } else {
            cartShareOrdShppInfo.addMbrId(cartShareOdrItem.getMbrId());
        }
        
        cartShareOrdShppInfoMap.put(cartShareOdrItem.getShppCd().name(), cartShareOrdShppInfo);
    }

    private void calculateOrdAmt(HashMap<Long, CartShareOrdAmtInfo> cartShareOrdAmtInfoMap,
            CartShareOdrItem cartShareOdrItem) {

        CartShareOrdAmtInfo cartShareOrdAmtInfo = cartShareOrdAmtInfoMap.getOrDefault(cartShareOdrItem.getMbrId(),
                CartShareOrdAmtInfo.of(cartShareOdrItem.getMbrId(), cartShareOdrItem.getPaymtAmt()));

        cartShareOrdAmtInfo.addOrdAmt(cartShareOdrItem.getPaymtAmt());

        cartShareOrdAmtInfoMap.put(cartShareOdrItem.getMbrId(), cartShareOrdAmtInfo);
    }
}
