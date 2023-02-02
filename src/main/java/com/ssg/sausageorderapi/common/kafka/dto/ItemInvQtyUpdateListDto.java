package com.ssg.sausageorderapi.common.kafka.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class ItemInvQtyUpdateListDto {

    private Long cartShareOrdId;

    private List<CartShareOrdItemInfo> cartShareOrdItemInfoList = new ArrayList<>();

    public static ItemInvQtyUpdateListDto of(Long cartShareOrdId, List<CartShareOrdItemInfo> cartShareOrdItemInfoList) {
        return ItemInvQtyUpdateListDto.builder()
                .cartShareOrdId(cartShareOrdId)
                .cartShareOrdItemInfoList(cartShareOrdItemInfoList)
                .build();
    }

}