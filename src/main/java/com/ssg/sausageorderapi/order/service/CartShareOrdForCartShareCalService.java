package com.ssg.sausageorderapi.order.service;


import com.ssg.sausageorderapi.common.client.internal.CartShareApiClient;
import com.ssg.sausageorderapi.common.client.internal.dto.response.CartShareMbrIdListResponse;
import com.ssg.sausageorderapi.common.exception.ErrorCode;
import com.ssg.sausageorderapi.common.exception.NotFoundException;
import com.ssg.sausageorderapi.order.dto.response.CartShareOrdFindDetailForCartShareCalResponse;
import com.ssg.sausageorderapi.order.dto.response.CartShareOrdFindDetailForCartShareCalResponse.CartShareOrdAmtInfo;
import com.ssg.sausageorderapi.order.dto.response.CartShareOrdFindDetailForCartShareCalResponse.CartShareOrdShppInfo;
import com.ssg.sausageorderapi.order.dto.response.CartShareOrdFindListForCartShareCalResponse;
import com.ssg.sausageorderapi.order.dto.response.CartShareOrdFindListForCartShareCalResponse.CartShareOrdInfo;
import com.ssg.sausageorderapi.order.entity.CartShareOdr;
import com.ssg.sausageorderapi.order.entity.CartShareOdrItem;
import com.ssg.sausageorderapi.order.repository.CartShareOrdItemRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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

    private final CartShareOrdItemUtilService cartShareOrdItemUtilService;

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

        // 총결제금액이 0원이 멤버 추가
        cartShareMbrIdSet.removeAll(cartShareOrdAmtInfoMap.keySet());
        cartShareMbrIdSet.forEach(mbrId -> cartShareOrdAmtInfoMap.put(mbrId, CartShareOrdAmtInfo.of(mbrId, 0)));

        return CartShareOrdFindDetailForCartShareCalResponse.builder()
                .commAmt(commAmt)
                .shppInfoList(new ArrayList<>(cartShareOrdShppInfoMap.values()))
                .ordInfoList(new ArrayList<>(cartShareOrdAmtInfoMap.values())).build();
    }

    public CartShareOrdFindListForCartShareCalResponse findCartShareOrdList(Long cartShareId) {

        List<CartShareOdr> cartShareOdrList = cartShareOrdUtilService.findListByCartShareId(cartShareId);

        CartShareMbrIdListResponse cartShareMbrIdListResponse = cartShareClient.findCartShareMbrIdList(cartShareId)
                .getData();

        int cartShareMbrQty = cartShareMbrIdListResponse.getMbrIdList().size();

        String cartShareNm = cartShareMbrIdListResponse.getCartShareNm();

        List<CartShareOrdInfo> cartShareOrdInfoList = cartShareOdrList.stream()
                .map(cartShareOdr -> createCartShareOrdInfo(cartShareMbrQty, cartShareOdr, cartShareNm))
                .collect(Collectors.toList());

        return CartShareOrdFindListForCartShareCalResponse.of(cartShareOrdInfoList);
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

    private CartShareOrdInfo createCartShareOrdInfo(int cartShareMbrQty, CartShareOdr cartShareOdr,
            String cartShareNm) {
        List<CartShareOdrItem> cartShareOdrItemList = cartShareOrdItemUtilService.findListByCartShareOrdId(
                cartShareOdr.getCartShareOrdId());

        int ttlOrdItemQty = (int) cartShareOdrItemList.stream()
                .map(CartShareOdrItem::getItemId)
                .distinct().count();

        CartShareOdrItem cartShareOdrItem = Optional.ofNullable(cartShareOdrItemList.get(0)).orElseThrow(() -> {
            throw new NotFoundException(
                    String.format("주문 상품이 존재하지 않는 주문 ID (%s) 입니다", cartShareOdr.getCartShareOrdId()),
                    ErrorCode.NOT_FOUND_CART_SHARE_ORD_ITEM_EXCEPTION);
        });

        return CartShareOrdInfo.builder()
                .cartShareOrdRcpDts(cartShareOdr.getCartShareOrdRcpDts())
                .cartShareOrdNo(cartShareOdr.getCartShareOrdNo())
                .ttlPaymtAmt(cartShareOdr.getTtlPaymtAmt())
                .ttlOrdItemQty(ttlOrdItemQty)
                .cartShareMbrQty(cartShareMbrQty)
                .repItemNm(cartShareOdrItem.getItemNm())
                .repItemImgUrl(cartShareOdrItem.getItemImgUrl())
                .cartShareNm(cartShareNm)
                .cartShareOrdNo(cartShareOdr.getCartShareOrdNo())
                .build();
    }
}
