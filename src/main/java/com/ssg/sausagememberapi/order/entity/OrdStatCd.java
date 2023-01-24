package com.ssg.sausagememberapi.order.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OrdStatCd {


    IN_PROGRESS("주문 진행중"),

    SUCCESS("주문 성공"),

    CANCELED("주문 취소");

    private final String nm;

}
