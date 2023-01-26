package com.ssg.sausageorderapi.order.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OrdStatCd {

    SUCCESS("주문 성공"),

    CANCELED("주문 취소");

    private final String nm;

}
