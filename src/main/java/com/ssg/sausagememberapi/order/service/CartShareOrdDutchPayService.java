package com.ssg.sausagememberapi.order.service;


import com.ssg.sausagememberapi.order.dto.response.CartShareOrdForDutchPayResponse;
import com.ssg.sausagememberapi.order.dto.response.CartShareOrdForDutchPayResponse.CartShareOrdAmtInfo;
import com.ssg.sausagememberapi.order.dto.response.CartShareOrdForDutchPayResponse.CartShareOrdShppInfo;
import com.ssg.sausagememberapi.order.dto.response.CartShareOrdTotalPymtAmtForDutchPayResponse;
import com.ssg.sausagememberapi.order.entity.CartShareOdrItem;
import com.ssg.sausagememberapi.order.repository.CartShareOrdItemRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CartShareOrdDutchPayService {

    private final CartShareOrdItemRepository cartShareOrdItemRepository;

    public CartShareOrdTotalPymtAmtForDutchPayResponse calculateOrdTotalPymtAmt(Long cartShareOrdId) {

        List<CartShareOdrItem> cartShareOdrItemList = cartShareOrdItemRepository.findAllByCartShareOrdId(
                cartShareOrdId);

        Integer totalPymtAmt = cartShareOdrItemList.stream()
                .map(CartShareOdrItem::getPaymtAmt)
                .reduce(0, Integer::sum);

        return CartShareOrdTotalPymtAmtForDutchPayResponse.of(totalPymtAmt);
    }

    public CartShareOrdForDutchPayResponse findCartShareOrd(Long cartShareOrdId) {

        List<CartShareOdrItem> cartShareOdrItemList = cartShareOrdItemRepository.findAllByCartShareOrdId(
                cartShareOrdId);

        HashMap<String, CartShareOrdShppInfo> cartShareOrdShppInfoMap = new HashMap<>();
        HashMap<Long, CartShareOrdAmtInfo> cartShareOrdAmtInfoMap = new HashMap<>();

        int totalPymtAmt = 0;
        int commAmt = 0;

        for (CartShareOdrItem cartShareOdrItem : cartShareOdrItemList) {

            // 총 결제 금액 더하기
            totalPymtAmt += cartShareOdrItem.getPaymtAmt();

            // 공통 금액 계산
            commAmt = calculateCommAmt(commAmt, cartShareOdrItem);

            calculateShppAmt(cartShareOrdShppInfoMap, cartShareOdrItem);

            calculateOrdAmt(cartShareOrdAmtInfoMap, cartShareOdrItem);
        }

        return CartShareOrdForDutchPayResponse.of(totalPymtAmt, commAmt,
                new ArrayList<>(cartShareOrdShppInfoMap.values()),
                new ArrayList<>(cartShareOrdAmtInfoMap.values()));
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
