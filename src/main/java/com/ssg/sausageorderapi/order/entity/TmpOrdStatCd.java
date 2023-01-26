package com.ssg.sausageorderapi.order.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TmpOrdStatCd {


    IN_PROGRESS("주문 진행중"),

    COMPLETED("주문 전환 완료"),

    CANCELED("주문 중도 이탈");

    private final String nm;

}
