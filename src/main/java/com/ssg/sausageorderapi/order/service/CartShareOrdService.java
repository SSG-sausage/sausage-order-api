package com.ssg.sausageorderapi.order.service;


import com.ssg.sausageorderapi.common.client.internal.CartShareApiClient;
import com.ssg.sausageorderapi.common.client.internal.CartShareCalApiClient;
import com.ssg.sausageorderapi.common.client.internal.ItemApiClient;
import com.ssg.sausageorderapi.common.client.internal.dto.request.CartShareCalSaveRequest;
import com.ssg.sausageorderapi.common.client.internal.dto.request.ItemInvQtyUpdateListRequest;
import com.ssg.sausageorderapi.common.client.internal.dto.request.ItemInvQtyUpdateListRequest.ItemInvQtyUpdateListRequestType;
import com.ssg.sausageorderapi.common.client.internal.dto.response.CartShareMbrIdListResponse;
import com.ssg.sausageorderapi.common.exception.ErrorCode;
import com.ssg.sausageorderapi.common.exception.InternalServerException;
import com.ssg.sausageorderapi.common.kafka.service.ProducerService;
import com.ssg.sausageorderapi.order.dto.response.CartShareOrdFindListResponse;
import com.ssg.sausageorderapi.order.dto.response.CartShareOrdFindResponse;
import com.ssg.sausageorderapi.order.dto.response.CartShareOrdSaveResponse;
import com.ssg.sausageorderapi.order.dto.response.ItemInvQtyUpdateInfo;
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
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
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

    private final ItemApiClient itemApiClient;

    private final ProducerService producerService;

    private final CartShareOrdCreateNotiService cartShareOrdCreateNotiService;

    private final CircuitBreakerFactory circuitBreakerFactory;

    private static final String ORDER_CIRCUIT_BREAKER_NAME = "orderCircuitBreaker";

    public CartShareOrdSaveResponse saveCartShareOrdFromTmpOrd(Long mbrId, Long cartShareId) {

        cartShareApiClient.validateCartShareMastr(cartShareId, mbrId);

        CartShareTmpOrd cartShareTmpOrd = cartShareTmpOrdUtilService.findCartShareTmpOrdInProgress(cartShareId);

        List<CartShareTmpOrdItem> cartShareTmpOrdItemList = cartShareTmpOrdUtilService.findCartShareTmpOrdItemByTmpOrdId(
                cartShareTmpOrd.getCartShareTmpOrdId());

        //???????????????
        List<ItemInvQtyUpdateInfo> itemInvQtyUpdateInfoList = decreaseItemInvQty(cartShareTmpOrdItemList);

        CartShareOrd cartShareOrd;
        List<CartShareOrdItem> cartShareOrdItemList;

        try {

            cartShareOrd = CartShareOrd.newInstance(cartShareTmpOrd);

            cartShareOrdRepository.save(cartShareOrd);

            cartShareOrdItemList = cartShareTmpOrdItemList.stream()
                    .map(cartShareTmpOdrItem -> CartShareOrdItem.newInstance(cartShareOrd.getCartShareOrdId(),
                            cartShareTmpOdrItem))
                    .collect(Collectors.toList());

            cartShareOrdItemRepository.saveAll(cartShareOrdItemList);

            // ??? ?????? ?????? ??????
            int ttlPaymtAmt = calculateTtlPaymtAmt(cartShareOrdItemList);
            cartShareOrd.changeTtlPaymtAmt(ttlPaymtAmt);

        } catch (Exception exception) {

            //?????? ?????? ?????? ??????

            producerService.updateItemInvQty(itemInvQtyUpdateInfoList);

            throw new InternalServerException("?????? ?????? ??? ??? ??? ?????? ????????? ??????????????????.",
                    ErrorCode.INTERNAL_SERVER_ORD_SAVE_EXCEPTION);
        }

        // ?????? ?????? ??????, ???????????? ?????? ????????? ??????
        cartShareTmpOrdUtilService.changeTmpOrdStatCd(cartShareTmpOrd, TmpOrdStatCd.CANCELED);
        producerService.deleteCartShareItemList(cartShareId);
        producerService.updateEditPsblYn(cartShareId, true);
        cartShareOrdCreateNotiService.createOrdSaveNoti(cartShareOrd, cartShareId, mbrId, cartShareOrdItemList);

        CartShareCalSaveRequest cartShareCalSaveRequest = createCartShareCalSaveRequest(cartShareOrd);


        CircuitBreaker circuitbreaker = circuitBreakerFactory.create(ORDER_CIRCUIT_BREAKER_NAME);
        Long cartShareCalId = circuitbreaker.run(()-> cartShareCalApiClient.saveCartShareCal(cartShareCalSaveRequest).getData()
                .getCartShareCalId(), throwable -> {
            producerService.retryCartShareCal(cartShareCalSaveRequest);
            return null;
        });

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

    private List<ItemInvQtyUpdateInfo> decreaseItemInvQty(List<CartShareTmpOrdItem> cartShareTmpOrdItemList) {

        List<ItemInvQtyUpdateInfo> itemInvQtyUpdateInfoList = cartShareTmpOrdItemList.stream()
                .map(cartShareTmpOrdItem ->
                        ItemInvQtyUpdateInfo.of(cartShareTmpOrdItem.getItemId(), cartShareTmpOrdItem.getItemQty()))
                .collect(Collectors.toList());

        itemApiClient.updateItemInvQty(
                ItemInvQtyUpdateListRequest.of(itemInvQtyUpdateInfoList, ItemInvQtyUpdateListRequestType.DECREASE));

        return itemInvQtyUpdateInfoList;
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
