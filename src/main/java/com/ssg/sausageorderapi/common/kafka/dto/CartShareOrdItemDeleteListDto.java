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
public class CartShareOrdItemDeleteListDto {

    private Long cartShareOrdId;

    private List<CartShareOrdItemInfo> cartShareOrdItemInfoList = new ArrayList<>();

    public static CartShareOrdItemDeleteListDto of(List<CartShareOrdItemInfo> cartShareOrdItemInfoList) {
        return CartShareOrdItemDeleteListDto.builder()
                .cartShareOrdItemInfoList(cartShareOrdItemInfoList)
                .build();
    }
}