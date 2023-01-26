package com.ssg.sausagememberapi.common.client.internal.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PUBLIC)
@ToString
public class CartShareItemDeleteListRequest {

    @Schema(description = "공유장바구니 ID")
    private Long cartShareId;

    public static CartShareItemDeleteListRequest of(Long cartShareId) {
        return CartShareItemDeleteListRequest.builder()
                .cartShareId(cartShareId)
                .build();
    }
}