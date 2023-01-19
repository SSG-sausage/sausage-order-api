package com.ssg.sausagememberapi.order.service;


import com.ssg.sausagememberapi.common.client.internal.CartShareClient;
import com.ssg.sausagememberapi.common.client.internal.dto.response.CartShareItemListResponse.CartShareItemInfo;
import com.ssg.sausagememberapi.order.dto.response.CartShareOrdByDutchPayResponse;
import com.ssg.sausagememberapi.order.dto.response.CartShareOrdByDutchPayResponse.CartShareOrdAmtInfo;
import com.ssg.sausagememberapi.order.dto.response.CartShareOrdByDutchPayResponse.CartShareOrdShppInfo;
import com.ssg.sausagememberapi.order.dto.response.CartShareOrdFindListResponse;
import com.ssg.sausagememberapi.order.dto.response.CartShareOrdFindResponse;
import com.ssg.sausagememberapi.order.entity.CartShareOdr;
import com.ssg.sausagememberapi.order.entity.CartShareOdrItem;
import com.ssg.sausagememberapi.order.repository.CartShareOrdItemRepository;
import com.ssg.sausagememberapi.order.repository.CartShareOrdRepository;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CartShareOrdService {

    private final CartShareOrdRepository cartShareOrdRepository;

    private final CartShareOrdItemRepository cartShareOrdItemRepository;

    private final CartShareOrdUtilService cartShareOrdUtilService;

    private final CartShareClient cartShareClient;


    @Transactional
    public void saveCartShareOrd(Long mbrId, Long cartShareId) {

        // validate 'isFound' and 'isCartShareMaster' (internal api)
        cartShareClient.validateCartShareMasterAuth(mbrId, cartShareId);

        cartShareOrdRepository.save(CartShareOdr.newInstance(cartShareId));

        // find cartShareOrderItem-list by cart share id (internal api)
        List<CartShareItemInfo> cartShareItemList = cartShareClient.getCartShareItemList(cartShareId)
                .getData().getCartShareItemList();

        List<CartShareOdrItem> cartShareOdrItems = cartShareItemList.stream()
                .map(cartShareItemInfo -> CartShareOdrItem.newInstance(cartShareItemInfo, cartShareId))
                .collect(Collectors.toList());

        // (kafka producing) delete cartShareItem ==> to be added

        cartShareOrdItemRepository.saveAll(cartShareOdrItems);
    }

    public CartShareOrdFindListResponse findCartShareOrderList(Long mbrId, Long cartShareId) {

        // validate 'isFound' and 'isAccessibleCartShareByMbr' (internal api)
        cartShareClient.validateCartShareAuth(mbrId, cartShareId);

        List<CartShareOdr> cartShareOdrList = cartShareOrdRepository.findAllByCartShareId(cartShareId);

        return CartShareOrdFindListResponse.of(cartShareOdrList);
    }

    public CartShareOrdFindResponse findCartShareOrder(Long mbrId, Long cartShareId, Long cartShareOrdId) {

        // validate 'isFound' and 'isAccessibleCartShareByMbr' (internal api)
        cartShareClient.validateCartShareAuth(mbrId, cartShareId);

        CartShareOdr cartShareOdr = cartShareOrdUtilService.findById(cartShareOrdId);

        return CartShareOrdFindResponse.of(cartShareOdr);
    }

    public CartShareOrdByDutchPayResponse getCartShareOrdByDutchPay(Long cartShareOrdId) {

        List<CartShareOdrItem> cartShareOdrItemList = cartShareOrdItemRepository.findAllByCartShareOrdId(
                cartShareOrdId);

        HashMap<String, CartShareOrdShppInfo> cartShareOrdShppInfoMap = new HashMap<>();
        HashMap<Long, CartShareOrdAmtInfo> cartShareOrdAmtInfoMap = new HashMap<>();
        int totalPrice = 0;
        int commAmt = 0;

        for (CartShareOdrItem cartShareOdrItem : cartShareOdrItemList) {

            // 총 결제 금액 더하기
            commAmt += cartShareOdrItem.getPaymtAmt();

            // 공통 금액 조정
            commAmt = calculateCommAmt(commAmt, cartShareOdrItem);

            calculateShppAmt(cartShareOrdShppInfoMap, cartShareOdrItem);

            calculateOrdAmt(cartShareOrdAmtInfoMap, cartShareOdrItem);
        }

        return CartShareOrdByDutchPayResponse.of(totalPrice, commAmt,
                (List<CartShareOrdShppInfo>) cartShareOrdShppInfoMap.values(),
                (List<CartShareOrdAmtInfo>) cartShareOrdAmtInfoMap.values());
    }

    private int calculateCommAmt(int commAmt, CartShareOdrItem cartShareOdrItem) {
        if (cartShareOdrItem.getComYn()) {
            commAmt += cartShareOdrItem.getPaymtAmt();
        }
        return commAmt;
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

    private void calculateShppAmt(HashMap<String, CartShareOrdShppInfo> cartShareOrdShppInfoMap,
            CartShareOdrItem cartShareOdrItem) {

        if (cartShareOrdShppInfoMap.containsKey(cartShareOdrItem.getShppCd().getNm())) {

            // hashkey가 있을 경우, 멤버 추가
            cartShareOrdShppInfoMap.get(cartShareOdrItem.getShppCd().getNm())
                    .getMbrIdList().add(cartShareOdrItem.getMbrId());
            return;
        }

        // 없을 경우 새로운 CartShareOrdShppInfo 객체 맨들면서 해쉬값 생성
        cartShareOrdShppInfoMap.put(cartShareOdrItem.getShppCd().getNm(),
                CartShareOrdShppInfo.of(cartShareOdrItem.getShppCd()));

    }


}
