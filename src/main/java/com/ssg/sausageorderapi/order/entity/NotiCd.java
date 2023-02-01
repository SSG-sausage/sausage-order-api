package com.ssg.sausageorderapi.order.entity;

import com.ssg.sausageorderapi.common.exception.ErrorCode;
import com.ssg.sausageorderapi.common.exception.InternalServerException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum NotiCd {

    CART_SHARE("함께 장보기"),
    CART_SHARE_ORD("주문 안내");

    private final String description;

    public static NotiCd of(String notiCd) {
        if (notiCd.equals("CART_SHARE")) {
            return CART_SHARE;
        }
        if (notiCd.equals("CART_SHARE_ORD")) {
            return CART_SHARE_ORD;
        }
        throw new InternalServerException("예상치 못한 서버 에러가 발생하였습니다.", ErrorCode.INTERNAL_SERVER_EXCEPTION);
    }
}
