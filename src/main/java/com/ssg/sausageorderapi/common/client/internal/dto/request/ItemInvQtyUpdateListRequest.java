package com.ssg.sausageorderapi.common.client.internal.dto.request;

import com.ssg.sausageorderapi.order.dto.response.ItemInvQtyUpdateInfo;
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

    private List<ItemInvQtyUpdateInfo> itemInvQtyUpdateInfoList = new ArrayList<>();

    private ItemInvQtyUpdateListRequestType updateType;

    public static ItemInvQtyUpdateListRequest of(List<ItemInvQtyUpdateInfo> cartShareOrdItemInvQtyUpdateInfoList,
            ItemInvQtyUpdateListRequestType itemInvQtyUpdateListRequestType) {
        return ItemInvQtyUpdateListRequest.builder()
                .itemInvQtyUpdateInfoList(cartShareOrdItemInvQtyUpdateInfoList)
                .updateType(itemInvQtyUpdateListRequestType)
                .build();
    }

    @RequiredArgsConstructor
    @Getter
    public enum ItemInvQtyUpdateListRequestType {

        INCREASE(1),
        DECREASE(-1);

        private final int value;
    }

}