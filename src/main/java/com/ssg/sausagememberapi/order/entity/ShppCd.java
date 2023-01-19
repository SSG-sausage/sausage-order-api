package com.ssg.sausagememberapi.order.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ShppCd {

    SSG_SHPP("쓱배송", 3000),
    EMART_TRADERS_SHPP("이마트 트레이더스 배송", 4000);

    private final String nm;
    private final int shppCst;

}
