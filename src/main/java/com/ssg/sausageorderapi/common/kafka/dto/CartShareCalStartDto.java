package com.ssg.sausageorderapi.common.kafka.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PUBLIC)
public class CartShareCalStartDto {

    @Schema(description = "공유장바구니 정산 ID")
    private Long cartShareCalId;

    public static CartShareCalStartDto of(Long cartShareCalId) {
        return CartShareCalStartDto.builder()
                .cartShareCalId(cartShareCalId)
                .build();
    }
}