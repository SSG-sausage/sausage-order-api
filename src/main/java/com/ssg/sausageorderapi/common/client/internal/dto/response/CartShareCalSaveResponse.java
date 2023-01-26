package com.ssg.sausageorderapi.common.client.internal.dto.response;

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
public class CartShareCalSaveResponse {

    @Schema(description = "공유장바구니정산 ID")
    private Long cartShareCalId;

}
