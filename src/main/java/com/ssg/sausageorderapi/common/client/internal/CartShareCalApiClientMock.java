package com.ssg.sausageorderapi.common.client.internal;

import com.ssg.sausageorderapi.common.client.internal.dto.request.CartShareCalSaveRequest;
import com.ssg.sausageorderapi.common.client.internal.dto.response.CartShareCalSaveResponse;
import com.ssg.sausageorderapi.common.dto.SuccessResponse;
import com.ssg.sausageorderapi.common.success.SuccessCode;
import org.springframework.stereotype.Service;

@Service
public class CartShareCalApiClientMock implements CartShareCalApiClient {

    @Override
    public SuccessResponse<CartShareCalSaveResponse> saveCartShareCal(CartShareCalSaveRequest request) {
        return SuccessResponse.success(SuccessCode.OK_SUCCESS, new CartShareCalSaveResponse(1L)).getBody();
    }
}
