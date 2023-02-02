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
@Builder(access = AccessLevel.PRIVATE)
public class CartShareOrdItemInfo {

    @Schema(description = "상품 ID")
    private Long itemId;

    @Schema(description = "공유장바구니주문상품 ID")
    private Long cartShareOrdItemId;

    @Schema(description = "상품 수량")
    private Integer itemInvQty;

    public static CartShareOrdItemInfo of(Long itemId, Long cartShareOrdItemId, int itemInvQty) {
        return CartShareOrdItemInfo.builder()
                .itemId(itemId)
                .cartShareOrdItemId(cartShareOrdItemId)
                .itemInvQty(itemInvQty)
                .build();
    }


}