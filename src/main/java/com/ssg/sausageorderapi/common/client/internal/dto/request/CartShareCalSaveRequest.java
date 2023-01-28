package com.ssg.sausageorderapi.common.client.internal.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CartShareCalSaveRequest {

    @Schema(description = "공유장바구니주문 ID")
    private Long cartShareOrdId;

    public static CartShareCalSaveRequest of(Long cartShareOrdId) {
        return CartShareCalSaveRequest.builder()
                .cartShareOrdId(cartShareOrdId)
                .build();
    }

}