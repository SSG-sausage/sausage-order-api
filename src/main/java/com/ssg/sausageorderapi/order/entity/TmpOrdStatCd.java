package com.ssg.sausageorderapi.order.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TmpOrdStatCd {


    IN_PROGRESS("임시 주문 진행중"),

    COMPLETED("임시 주문 전환 완료"),

    CANCELED("임시 주문 취소");

    private final String nm;

}
