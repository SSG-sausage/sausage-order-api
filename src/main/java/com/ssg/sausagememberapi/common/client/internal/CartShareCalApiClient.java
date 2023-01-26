package com.ssg.sausagememberapi.common.client.internal;

import com.ssg.sausagememberapi.common.client.internal.dto.request.CartShareCalSaveRequest;
import com.ssg.sausagememberapi.common.client.internal.dto.response.CartShareCalSaveResponse;
import com.ssg.sausagememberapi.common.dto.SuccessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "SAUSAGE-CART-CAL-SHARE-API")
public interface CartShareCalApiClient {

    @PostMapping("/cart-share-cal/api/cart-share-cal")
    SuccessResponse<CartShareCalSaveResponse> saveCartShareCal(@RequestBody CartShareCalSaveRequest request);
}
