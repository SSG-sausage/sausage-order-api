package com.ssg.sausageorderapi.order.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Builder(access = AccessLevel.PRIVATE)
public class CartShareOrdSaveResponse {

    @Schema(description = "공유장바구니정산 id")
    private Long cartShareCalId;

    public static CartShareOrdSaveResponse of(Long cartShareCalId) {

        return CartShareOrdSaveResponse.builder()
                .cartShareCalId(cartShareCalId)
                .build();
    }
}
