package com.ssg.sausagememberapi.order.service;


import com.ssg.sausagememberapi.common.client.internal.CartShareClientMock;
import com.ssg.sausagememberapi.common.client.internal.dto.response.CartShareMbrIdListResponse;
import com.ssg.sausagememberapi.order.dto.response.CartShareOrdFindDetailForCartShareCalResponse;
import com.ssg.sausagememberapi.order.dto.response.CartShareOrdFindDetailForCartShareCalResponse.CartShareOrdAmtInfo;
import com.ssg.sausagememberapi.order.dto.response.CartShareOrdFindDetailForCartShareCalResponse.CartShareOrdShppInfo;
import com.ssg.sausagememberapi.order.dto.response.CartShareOrdFindForCartShareCalResponse;
import com.ssg.sausagememberapi.order.entity.CartShareOdr;
import com.ssg.sausagememberapi.order.entity.CartShareOdrItem;
import com.ssg.sausagememberapi.order.repository.CartShareOrdItemRepository;
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

    private final CartShareClientMock cartShareClient;


    public CartShareOrdFindForCartShareCalResponse findCartShareOrd(Long cartShareOrdId) {

        CartShareOdr cartShareOdr = cartShareOrdUtilService.findById(cartShareOrdId);

        CartShareMbrIdListResponse cartShareMbrIdListResponse = cartShareClient.findCartShareMbrIdList(
                cartShareOdr.getCartShareId()).getData();

        // calculate ttlPymtAmt
        Integer ttlPymtAmt = cartShareOrdItemRepository.findAllByCartShareOrdId(cartShareOrdId).stream()
                .map(CartShareOdrItem::getPaymtAmt)
                .reduce(0, Integer::sum);

        return CartShareOrdFindForCartShareCalResponse.builder()
                .cartShareId(cartShareOdr.getCartShareId())
                .cartShareMbrIdList(new HashSet<>(cartShareMbrIdListResponse.getCartShareMbrIdList()))
                .cartShareMastrMbrId(cartShareMbrIdListResponse.getCartShareMastrMbrId())
                .ttlPaymtAmt(ttlPymtAmt).build();
    }

    public CartShareOrdFindDetailForCartShareCalResponse findCartShareOrdDetail(Long cartShareOrdId) {

        CartShareOdr cartShareOdr = cartShareOrdUtilService.findById(cartShareOrdId);

        List<CartShareOdrItem> cartShareOdrItemList = cartShareOrdItemRepository.findAllByCartShareOrdId(
                cartShareOrdId);

        HashSet<Long> cartShareMbrIdSet = new HashSet<>(
                cartShareClient.findCartShareMbrIdList(cartShareOdr.getCartShareId()).getData()
                        .getCartShareMbrIdList());

        HashMap<String, CartShareOrdShppInfo> cartShareOrdShppInfoMap = new HashMap<>();
        HashMap<Long, CartShareOrdAmtInfo> cartShareOrdAmtInfoMap = new HashMap<>();

        int commAmt = 0;

        for (CartShareOdrItem cartShareOdrItem : cartShareOdrItemList) {

            // 공통 금액 계산
            commAmt = calculateCommAmt(commAmt, cartShareOdrItem);

            calculateShppAmt(cartShareOrdShppInfoMap, cartShareOdrItem);

            calculateOrdAmt(cartShareOrdAmtInfoMap, cartShareOdrItem);
        }

        // 결제금액이 0원인 mbr 추가.
        cartShareMbrIdSet.removeAll(cartShareOrdAmtInfoMap.keySet());
        cartShareMbrIdSet.forEach(mbrId -> cartShareOrdAmtInfoMap.put(mbrId, CartShareOrdAmtInfo.of(mbrId, 0)));

        return CartShareOrdFindDetailForCartShareCalResponse.builder()
                .commAmt(commAmt)
                .shppInfoList(new ArrayList<>(cartShareOrdShppInfoMap.values()))
                .ordInfoList(new ArrayList<>(cartShareOrdAmtInfoMap.values())).build();
    }

    private int calculateCommAmt(int commAmt, CartShareOdrItem cartShareOdrItem) {
        if (cartShareOdrItem.getComYn()) {
            commAmt += cartShareOdrItem.getPaymtAmt();
        }
        return commAmt;
    }

    private void calculateShppAmt(HashMap<String, CartShareOrdShppInfo> cartShareOrdShppInfoMap,
            CartShareOdrItem cartShareOdrItem) {

        if (cartShareOrdShppInfoMap.containsKey(cartShareOdrItem.getShppCd().name())) {

            // hashkey가 있을 경우, 멤버 추가
            CartShareOrdShppInfo cartShareOrdShppInfo = cartShareOrdShppInfoMap.get(
                    cartShareOdrItem.getShppCd().name());
            cartShareOrdShppInfo.addMbrId(cartShareOdrItem.getMbrId());

            return;
        }

        CartShareOrdShppInfo cartShareOrdShppInfo = CartShareOrdShppInfo.of(cartShareOdrItem.getShppCd());
        cartShareOrdShppInfo.addMbrId(cartShareOdrItem.getMbrId());

        // 없을 경우 새로운 CartShareOrdShppInfo 객체 맨들면서 해쉬값 생성.
        cartShareOrdShppInfoMap.put(cartShareOdrItem.getShppCd().name(), cartShareOrdShppInfo);
    }

    private void calculateOrdAmt(HashMap<Long, CartShareOrdAmtInfo> cartShareOrdAmtInfoMap,
            CartShareOdrItem cartShareOdrItem) {

        if (cartShareOrdAmtInfoMap.containsKey(cartShareOdrItem.getMbrId())) {

            // hashkey가 있을 경우, 멤버가 계산할 가격 더하기.
            cartShareOrdAmtInfoMap.get(cartShareOdrItem.getMbrId()).addOrdAmt(cartShareOdrItem.getPaymtAmt());
            return;
        }

        // 없을 경우 새로운 CartShareOrdAmtInfo 객체 맨들면서 해쉬값 생성
        cartShareOrdAmtInfoMap.put(cartShareOdrItem.getMbrId(),
                CartShareOrdAmtInfo.of(cartShareOdrItem.getMbrId(), cartShareOdrItem.getPaymtAmt()));

    }
}
