package com.ssg.sausagememberapi.common.client.internal;

import com.ssg.sausagememberapi.common.client.internal.dto.request.CartShareCalSaveRequest;
import com.ssg.sausagememberapi.common.client.internal.dto.response.CartShareCalSaveResponse;
import com.ssg.sausagememberapi.common.dto.SuccessResponse;
import com.ssg.sausagememberapi.common.success.SuccessCode;
import org.springframework.stereotype.Service;

@Service
public class CartShareCalClientMock implements CartShareCalClient {

    @Override
    public SuccessResponse<CartShareCalSaveResponse> saveCartShareCal(CartShareCalSaveRequest request) {
        return SuccessResponse.success(SuccessCode.OK_SUCCESS, new CartShareCalSaveResponse(1L)).getBody();
    }
}
