package com.ssg.sausageorderapi.common.client.internal;

import com.ssg.sausageorderapi.common.client.internal.dto.response.CartShareItemListResponse;
import com.ssg.sausageorderapi.common.client.internal.dto.response.CartShareMbrIdListResponse;
import com.ssg.sausageorderapi.common.dto.SuccessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "SAUSAGE-CART-SHARE-API")
public interface CartShareApiClient {

    @GetMapping("/api/cart-share/{cartShareId}/cart-share-item")
    SuccessResponse<CartShareItemListResponse> findCartShareItemList(@PathVariable Long cartShareId);

    @GetMapping("/api/cart-share/{cartShareId}/cart-share-mbr-id")
    SuccessResponse<CartShareMbrIdListResponse> findCartShareMbrIdList(@PathVariable Long cartShareId);

    @GetMapping("/api/cart-share/{cartShareId}/mbr/{mbrId}/validation")
    SuccessResponse<Boolean> validateCartShareMbr(@PathVariable("cartShareId") Long cartShareId,
            @PathVariable("mbrId") Long mbrId);

    @GetMapping("/api/cart-share/{cartShareId}/mbr/{mbrId}/mastr-validation")
    SuccessResponse<Boolean> validateCartShareMastr(@PathVariable("cartShareId") Long cartShareId,
            @PathVariable("mbrId") Long mbrId);
}
