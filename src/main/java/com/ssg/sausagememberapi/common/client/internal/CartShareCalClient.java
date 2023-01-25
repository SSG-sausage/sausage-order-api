package com.ssg.sausagememberapi.common.client.internal;

import com.ssg.sausagememberapi.common.client.internal.dto.response.CartShareItemListResponse;
import com.ssg.sausagememberapi.common.dto.SuccessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "SAUSAGE-CART-CAL-SHARE-API")
public interface CartShareCalClient {


    @PostMapping("/cart-share-cal/api/cart-share-cal")
    SuccessResponse<CartShareItemListResponse> getCartShareItemList(@PathVariable Long cartShareId);

}
