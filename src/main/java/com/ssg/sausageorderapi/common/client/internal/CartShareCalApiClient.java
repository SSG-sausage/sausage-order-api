package com.ssg.sausageorderapi.common.client.internal;

import com.ssg.sausageorderapi.common.client.internal.dto.request.CartShareCalSaveRequest;
import com.ssg.sausageorderapi.common.client.internal.dto.response.CartShareCalSaveResponse;
import com.ssg.sausageorderapi.common.dto.SuccessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "SAUSAGE-CART-SHARE-CALCULATION-API")
public interface CartShareCalApiClient {

    @PostMapping("/api/cart-share-cal")
    SuccessResponse<CartShareCalSaveResponse> saveCartShareCal(@RequestBody CartShareCalSaveRequest request);
}
