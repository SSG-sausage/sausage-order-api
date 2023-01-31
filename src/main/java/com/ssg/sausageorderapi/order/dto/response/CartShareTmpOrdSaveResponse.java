package com.ssg.sausageorderapi.order.dto.response;

import com.ssg.sausageorderapi.order.entity.CartShareTmpOrd;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Builder(access = AccessLevel.PRIVATE)
public class CartShareTmpOrdSaveResponse {

    @Schema(description = "공유장바구니주문 ID")
    private Long cartShareTmpOrdId;

    @Schema(description = "총 결제 금액")
    private Integer ttlPaymtAmt;

    public static CartShareTmpOrdSaveResponse of(CartShareTmpOrd cartShareTmpOrd) {

        return CartShareTmpOrdSaveResponse.builder()
                .cartShareTmpOrdId(cartShareTmpOrd.getCartShareTmpOrdId())
                .ttlPaymtAmt(cartShareTmpOrd.getTtlPaymtAmt())
                .build();
    }
}
