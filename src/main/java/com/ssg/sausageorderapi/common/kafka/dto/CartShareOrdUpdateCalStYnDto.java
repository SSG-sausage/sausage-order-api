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
public class CartShareOrdUpdateCalStYnDto {

    @Schema(description = "공유장바구니 ID")
    private Long cartShareId;

    public static CartShareOrdUpdateCalStYnDto of(Long cartShareId) {
        return CartShareOrdUpdateCalStYnDto.builder()
                .cartShareId(cartShareId)
                .build();
    }
}