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
import com.ssg.sausageorderapi.order.entity.CartShareOrd;
import com.ssg.sausageorderapi.order.entity.CartShareOrdItem;
import com.ssg.sausageorderapi.order.entity.ShppCd;
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

        CartShareOrd cartShareOrd = cartShareOrdUtilService.findById(cartShareOrdId);

        List<CartShareOrdItem> cartShareOrdItemList = cartShareOrdItemRepository.findAllByCartShareOrdId(
                cartShareOrdId);

        List<Long> mbrIdList = cartShareClient.findCartShareMbrIdList(cartShareOrd.getCartShareId()).getData()
                .getMbrIdList();

        HashSet<Long> cartShareMbrIdSet = new HashSet<>(mbrIdList);

        HashMap<String, CartShareOrdShppInfo> cartShareOrdShppInfoMap = new HashMap<>();
        HashMap<Long, CartShareOrdAmtInfo> cartShareOrdAmtInfoMap = new HashMap<>();
        HashMap<String, Integer> paymtAmtByShppCdMap = new HashMap<>();

        int commAmt = 0;

        for (CartShareOrdItem cartShareOrdItem : cartShareOrdItemList) {

            commAmt = calculateCommAmt(commAmt, cartShareOrdItem);

            addShppMbr(cartShareOrdShppInfoMap, cartShareOrdItem, mbrIdList);

            calculatePaymtAmtByShppCd(paymtAmtByShppCdMap, cartShareOrdItem);

            calculateOrdAmt(cartShareOrdAmtInfoMap, cartShareOrdItem);
        }

        // 총결제금액이 0원이 멤버 추가
        cartShareMbrIdSet.removeAll(cartShareOrdAmtInfoMap.keySet());
        cartShareMbrIdSet.forEach(mbrId -> cartShareOrdAmtInfoMap.put(mbrId, CartShareOrdAmtInfo.of(mbrId, 0)));

        // 배송비 계산
        for (String shppCd : paymtAmtByShppCdMap.keySet()) {

            int shppCst = ShppCd.calculateShppCst(ShppCd.valueOf(shppCd), paymtAmtByShppCdMap.get(shppCd));

            cartShareOrdShppInfoMap.get(shppCd).chagneShppCst(shppCst);
        }

        return CartShareOrdFindDetailForCartShareCalResponse.builder()
                .commAmt(commAmt)
                .shppInfoList(new ArrayList<>(cartShareOrdShppInfoMap.values()))
                .ordInfoList(new ArrayList<>(cartShareOrdAmtInfoMap.values())).build();
    }


    public CartShareOrdFindListForCartShareCalResponse findCartShareOrdList(Long cartShareId) {

        List<CartShareOrd> cartShareOrdList = cartShareOrdUtilService.findListByCartShareId(cartShareId);

        CartShareMbrIdListResponse cartShareMbrIdListResponse = cartShareClient.findCartShareMbrIdList(cartShareId)
                .getData();

        int cartShareMbrQty = cartShareMbrIdListResponse.getMbrIdList().size();

        String cartShareNm = cartShareMbrIdListResponse.getCartShareNm();

        List<CartShareOrdInfo> cartShareOrdInfoList = cartShareOrdList.stream()
                .map(cartShareOdr -> createCartShareOrdInfo(cartShareMbrQty, cartShareOdr, cartShareNm))
                .collect(Collectors.toList());

        return CartShareOrdFindListForCartShareCalResponse.of(cartShareOrdInfoList);
    }

    private int calculateCommAmt(int commAmt, CartShareOrdItem cartShareOrdItem) {
        if (cartShareOrdItem.getCommYn()) {
            commAmt += cartShareOrdItem.getPaymtAmt();
        }
        return commAmt;
    }

    private static void calculatePaymtAmtByShppCd(HashMap<String, Integer> paymtAmtByShppCd,
            CartShareOrdItem cartShareOrdItem) {

        int paymtAmt = paymtAmtByShppCd.getOrDefault(cartShareOrdItem.getShppCd().name(), 0)
                + cartShareOrdItem.getPaymtAmt();

        paymtAmtByShppCd.put(cartShareOrdItem.getShppCd().name(), paymtAmt);
    }

    private void addShppMbr(HashMap<String, CartShareOrdShppInfo> cartShareOrdShppInfoMap,
            CartShareOrdItem cartShareOrdItem, List<Long> mbrIdList) {

        CartShareOrdShppInfo cartShareOrdShppInfo = cartShareOrdShppInfoMap.getOrDefault(
                cartShareOrdItem.getShppCd().name(),
                CartShareOrdShppInfo.of(cartShareOrdItem.getShppCd()));

        if (cartShareOrdItem.getCommYn()) {

            // 공통인 경우 모든 유저 추가
            cartShareOrdShppInfo.addMbrIdList(new HashSet<>(mbrIdList));
        } else {
            cartShareOrdShppInfo.addMbrId(cartShareOrdItem.getMbrId());
        }

        cartShareOrdShppInfoMap.put(cartShareOrdItem.getShppCd().name(), cartShareOrdShppInfo);
    }

    private void calculateOrdAmt(HashMap<Long, CartShareOrdAmtInfo> cartShareOrdAmtInfoMap,
            CartShareOrdItem cartShareOrdItem) {

        if (cartShareOrdItem.getCommYn()) {
            return;
        }

        CartShareOrdAmtInfo cartShareOrdAmtInfo = cartShareOrdAmtInfoMap.getOrDefault(cartShareOrdItem.getMbrId(),
                CartShareOrdAmtInfo.of(cartShareOrdItem.getMbrId(), 0));

        cartShareOrdAmtInfo.addOrdAmt(cartShareOrdItem.getPaymtAmt());

        cartShareOrdAmtInfoMap.put(cartShareOrdItem.getMbrId(), cartShareOrdAmtInfo);
    }

    private CartShareOrdInfo createCartShareOrdInfo(int cartShareMbrQty, CartShareOrd cartShareOrd,
            String cartShareNm) {
        List<CartShareOrdItem> cartShareOrdItemList = cartShareOrdItemUtilService.findListByCartShareOrdId(
                cartShareOrd.getCartShareOrdId());

        int ttlOrdItemQty = (int) cartShareOrdItemList.stream()
                .map(CartShareOrdItem::getItemId)
                .distinct().count();

        CartShareOrdItem cartShareOrdItem = Optional.ofNullable(cartShareOrdItemList.get(0)).orElseThrow(() -> {
            throw new NotFoundException(
                    String.format("주문 상품이 존재하지 않는 주문 ID (%s) 입니다", cartShareOrd.getCartShareOrdId()),
                    ErrorCode.NOT_FOUND_CART_SHARE_ORD_ITEM_EXCEPTION);
        });

        return CartShareOrdInfo.builder()
                .cartShareOrdRcpDts(cartShareOrd.getCartShareOrdRcpDts())
                .cartShareOrdNo(cartShareOrd.getCartShareOrdNo())
                .ttlPaymtAmt(cartShareOrd.getTtlPaymtAmt())
                .ttlOrdItemQty(ttlOrdItemQty)
                .cartShareMbrQty(cartShareMbrQty)
                .repItemNm(cartShareOrdItem.getItemNm())
                .repItemImgUrl(cartShareOrdItem.getItemImgUrl())
                .cartShareNm(cartShareNm)
                .cartShareOrdNo(cartShareOrd.getCartShareOrdNo())
                .cartShareCalId(cartShareOrd.getCartShareCalId())
                .calStYn(cartShareOrd.getCalStYn())
                .build();
    }
}
