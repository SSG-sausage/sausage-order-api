package com.ssg.sausagememberapi.common.client.internal;

import com.ssg.sausagememberapi.common.client.internal.dto.request.CartShareUpdateEditPsblYnRequest;
import com.ssg.sausagememberapi.common.client.internal.dto.response.CartShareItemListResponse;
import com.ssg.sausagememberapi.common.client.internal.dto.response.CartShareMbrIdListResponse;
import com.ssg.sausagememberapi.common.dto.SuccessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "SAUSAGE-CART-SHARE-API")
public interface CartShareClient {

    @GetMapping("/cart-share/api/cart-share/{cartShareId}/cart-share-item")
    SuccessResponse<CartShareItemListResponse> findCartShareItemList(@PathVariable Long cartShareId);

    @GetMapping("/cart-share/api/cart-share/{cartShareId}/cart-share-mbr-id")
    SuccessResponse<CartShareMbrIdListResponse> findCartShareMbrIdList(@PathVariable Long cartShareId);

    @PostMapping("/cart-share/api/cart-share/{cartShareId}/edit-psbl-yn")
    SuccessResponse<String> updateEditPsblYn(@PathVariable Long cartShareId,
            @RequestBody CartShareUpdateEditPsblYnRequest request);

    @DeleteMapping("/cart-share/api/cart-share/{cartShareId}")
    SuccessResponse<String> deleteCartShareItemList(@PathVariable Long cartShareId);

    @GetMapping("/cart-share/api/cart-share/{cartShareId}/mbr/{mbrId}/validation")
    SuccessResponse<Boolean> validateCartShareAuth(@PathVariable Long cartShareId,
            @PathVariable Long mbrId);

    @GetMapping("/cart-share/api/cart-share/{cartShareId}/mbr/{mbrId}/master-validation")
    SuccessResponse<Boolean> validateCartShareMasterAuth(@PathVariable Long cartShareId,
            @PathVariable Long mbrId);


}
