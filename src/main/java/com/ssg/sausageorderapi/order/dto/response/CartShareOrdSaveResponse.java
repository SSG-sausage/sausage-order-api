package com.ssg.sausageorderapi.order.dto.response;

import com.ssg.sausageorderapi.order.entity.CartShareOrd;
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
public class CartShareOrdSaveResponse {

    @Schema(description = "공유장바구니정산 ID")
    private Long cartShareCalId;

    @Schema(description = "공유장바구니주문 ID")
    private Long cartShareOrdId;

    @Schema(description = "총 결제 금액")
    private Integer ttlPaymtAmt;


    public static CartShareOrdSaveResponse of(CartShareOrd cartShareOrd) {

        return CartShareOrdSaveResponse.builder()
                .cartShareOrdId(cartShareOrd.getCartShareOrdId())
                .cartShareCalId(cartShareOrd.getCartShareCalId())
                .ttlPaymtAmt(cartShareOrd.getTtlPaymtAmt())
                .build();
    }
}
