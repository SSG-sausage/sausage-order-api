package com.ssg.sausageorderapi.common.kafka.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class CartShareOrdCartShareCalIdUpdateDto {

    @Schema(description = "공유장바구니 주문 ID")
    private Long cartShareOrdId;

    @Schema(description = "공유장바구니 정산 ID")
    private Long cartShareCalId;

}
