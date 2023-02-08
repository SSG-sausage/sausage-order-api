package com.ssg.sausageorderapi.order.dto.response;

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
public class ItemInvQtyUpdateInfo {

    @Schema(description = "상품 ID")
    private Long itemId;

    @Schema(description = "상품 수량")
    private Integer itemInvQty;

    public static ItemInvQtyUpdateInfo of(Long itemId, int itemInvQty) {
        return ItemInvQtyUpdateInfo.builder()
                .itemId(itemId)
                .itemInvQty(itemInvQty)
                .build();
    }
}
