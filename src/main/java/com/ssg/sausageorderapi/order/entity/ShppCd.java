package com.ssg.sausageorderapi.order.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ShppCd {

    SSG_SHPP("쓱배송", 3000),
    EMART_TRADERS_SHPP("이마트 트레이더스 배송", 4000);

    private final String nm;
    private final int shppCst;

    public static int calculateShppCst(ShppCd shppCd, int paymtAmt) {

        switch (shppCd) {

            case SSG_SHPP:
                return findShppCst(SSG_SHPP, paymtAmt, 40000);

            case EMART_TRADERS_SHPP:
                return findShppCst(EMART_TRADERS_SHPP, paymtAmt, 120000);

            default:
                return 0;
        }
    }

    private static int findShppCst(ShppCd shppCd, int paymtAmt, int freeShppCst) {
        if (paymtAmt >= freeShppCst) {
            return 0;
        }
        return shppCd.shppCst;
    }

}
