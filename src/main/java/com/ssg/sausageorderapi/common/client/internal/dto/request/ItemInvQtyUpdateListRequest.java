package com.ssg.sausageorderapi.common.client.internal.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class ItemInvQtyUpdateListRequest {

    private List<ItemInfo> cartShareOrdItemInfoList = new ArrayList<>();

    private ItemInvQtyUpdateListUpdateType updateType;

    public static ItemInvQtyUpdateListRequest of(List<ItemInfo> cartShareOrdItemInfoList,
            ItemInvQtyUpdateListRequest.ItemInvQtyUpdateListUpdateType itemInvQtyUpdateListUpdateType) {
        return ItemInvQtyUpdateListRequest.builder()
                .cartShareOrdItemInfoList(cartShareOrdItemInfoList)
                .updateType(itemInvQtyUpdateListUpdateType)
                .build();
    }


    @RequiredArgsConstructor
    @Getter
    public enum ItemInvQtyUpdateListUpdateType {

        INCREASE(1),
        DECREASE(-1);

        private final int value;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder(access = AccessLevel.PRIVATE)
    public static class ItemInfo {

        @Schema(description = "상품 ID")
        private Long itemId;

        @Schema(description = "상품 수량")
        private Integer itemInvQty;

        public static ItemInfo of(Long itemId, int itemInvQty) {
            return ItemInfo.builder()
                    .itemId(itemId)
                    .itemInvQty(itemInvQty)
                    .build();
        }
    }

}