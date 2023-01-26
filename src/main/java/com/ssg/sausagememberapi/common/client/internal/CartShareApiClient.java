package com.ssg.sausagememberapi.common.client.internal;

import com.ssg.sausagememberapi.common.client.internal.dto.response.CartShareItemListResponse;
import com.ssg.sausagememberapi.common.client.internal.dto.response.CartShareMbrIdListResponse;
import com.ssg.sausagememberapi.common.dto.SuccessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "SAUSAGE-CART-SHARE-API")
public interface CartShareApiClient {

    @GetMapping("/cart-share/api/cart-share/{cartShareId}/cart-share-item")
    SuccessResponse<CartShareItemListResponse> findCartShareItemList(@PathVariable Long cartShareId);

    @GetMapping("/cart-share/api/cart-share/{cartShareId}/cart-share-mbr-id")
    SuccessResponse<CartShareMbrIdListResponse> findCartShareMbrIdList(@PathVariable Long cartShareId);

    @GetMapping("/cart-share/api/cart-share/{cartShareId}/mbr/{mbrId}/validation")
    SuccessResponse<Boolean> validateCartShareMbr(@PathVariable Long cartShareId, @PathVariable Long mbrId);

    @GetMapping("/cart-share/api/cart-share/{cartShareId}/mbr/{mbrId}/mastr-validation")
    SuccessResponse<Boolean> validateCartShareMastr(@PathVariable Long cartShareId, @PathVariable Long mbrId);
}