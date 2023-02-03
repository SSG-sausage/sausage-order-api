package com.ssg.sausageorderapi.common.client.internal;

import com.ssg.sausageorderapi.common.client.internal.dto.request.ItemInvQtyValidateRequest;
import com.ssg.sausageorderapi.common.dto.SuccessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "SAUSAGE-ITEM-API")
public interface ItemApiClient {

    @PostMapping("/api/item/inv-qty-validation")
    SuccessResponse<Boolean> validateItemInvQty(@RequestBody ItemInvQtyValidateRequest request);
}
