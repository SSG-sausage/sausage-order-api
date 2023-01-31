package com.ssg.sausageorderapi.common.client.internal;

import com.ssg.sausageorderapi.common.client.internal.dto.response.CartShareItemListResponse;
import com.ssg.sausageorderapi.common.client.internal.dto.response.CartShareItemListResponse.CartShareItemInfo;
import com.ssg.sausageorderapi.common.client.internal.dto.response.CartShareMbrIdListResponse;
import com.ssg.sausageorderapi.common.dto.SuccessResponse;
import com.ssg.sausageorderapi.common.success.SuccessCode;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;


@Service
public class CartShareApiClientMock implements CartShareApiClient {

    /*
    Mocling FeignClinet
     */

    @Override
    public SuccessResponse<CartShareItemListResponse> findCartShareItemList(Long cartShareId) {
        return SuccessResponse.success(SuccessCode.OK_SUCCESS, createMockData()).getBody();
    }

    @Override
    public SuccessResponse<CartShareMbrIdListResponse> findCartShareMbrIdList(Long cartShareId) {
        return SuccessResponse.success(SuccessCode.OK_SUCCESS,
                new CartShareMbrIdListResponse(1L, List.of(1L, 2L, 3L), "test")).getBody();
    }

    @Override
    public SuccessResponse<Boolean> validateCartShareMbr(Long cartShareId, Long mbrId) {
        return SuccessResponse.success(SuccessCode.OK_SUCCESS, Boolean.TRUE).getBody();
    }

    @Override
    public SuccessResponse<Boolean> validateCartShareMastr(Long cartShareId, Long mbrId) {
        return SuccessResponse.success(SuccessCode.OK_SUCCESS, Boolean.TRUE).getBody();
    }

    public CartShareItemListResponse createMockData() {

        List<CartShareItemInfo> cartShareItemList = new ArrayList<>();

        CartShareItemInfo cartShareItemInfo1 = CartShareItemInfo.builder()
                .cartShareItemId(1L)
                .itemId(1L)
                .mbrId(1L)
                .itemNm("test")
                .itemAmt(1000)
                .shppCd("SSG_SHPP")
                .itemQty(1)
                .commYn(true)
                .build();

        CartShareItemInfo cartShareItemInfo2 = CartShareItemInfo.builder()
                .cartShareItemId(2L)
                .itemId(2L)
                .mbrId(2L)
                .itemNm("test")
                .itemAmt(2000)
                .shppCd("SSG_SHPP")
                .itemQty(2)
                .commYn(false)
                .build();

        CartShareItemInfo cartShareItemInfo3 = CartShareItemInfo.builder()
                .cartShareItemId(3L)
                .itemId(3L)
                .mbrId(1L)
                .itemNm("test")
                .itemAmt(4000)
                .shppCd("EMART_TRADERS_SHPP")
                .itemQty(2)
                .commYn(true)
                .build();

        cartShareItemList.addAll(List.of(cartShareItemInfo1, cartShareItemInfo2, cartShareItemInfo3));

        return new CartShareItemListResponse(cartShareItemList);
    }
}
