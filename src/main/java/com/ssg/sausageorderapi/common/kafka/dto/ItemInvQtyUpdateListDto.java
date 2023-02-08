package com.ssg.sausageorderapi.common.kafka.dto;

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
public class ItemInvQtyUpdateListDto {

    private List<ItemInvQtyUpdateInfo> itemInvQtyUpdateInfoList = new ArrayList<>();

    private ItemInvQtyUpdateListDtoType itemInvQtyUpdateListDtoType;

    public static ItemInvQtyUpdateListDto of(List<ItemInvQtyUpdateInfo> itemInvQtyUpdateInfoList,
            ItemInvQtyUpdateListDtoType itemInvQtyUpdateListDtoType) {

        return ItemInvQtyUpdateListDto.builder()
                .itemInvQtyUpdateInfoList(itemInvQtyUpdateInfoList)
                .itemInvQtyUpdateListDtoType(itemInvQtyUpdateListDtoType)
                .build();
    }

    @RequiredArgsConstructor
    @Getter
    public enum ItemInvQtyUpdateListDtoType {

        INCREASE(1),
        DECREASE(-1);

        private final int value;
    }
}