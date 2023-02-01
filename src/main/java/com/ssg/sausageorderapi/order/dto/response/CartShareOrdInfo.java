package com.ssg.sausageorderapi.order.dto.response;

import com.ssg.sausageorderapi.order.entity.CartShareOrd;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class CartShareOrdInfo {

    @Schema(description = "공유장바구니주문 ID")
    private Long cartShareOrdId;

    @Schema(description = "공유장바구니 ID")
    private Long cartShareId;

    @Schema(description = "공유장바구니정산 ID")
    private Long cartShareCalId;

    @Schema(description = "주문접수일시")
    private LocalDateTime cartShareOrdRcpDts;

    @Schema(description = "주문상태코드")
    private String ordStatCd;

    @Schema(description = "정산시작여부")
    private Boolean calStYn;

    @Schema(description = "총결제 금액")
    private Integer ttlPaymtAmt;

    public static CartShareOrdInfo of(CartShareOrd cartShareOrd) {
        return CartShareOrdInfo.builder()
                .cartShareOrdId(cartShareOrd.getCartShareOrdId())
                .cartShareId(cartShareOrd.getCartShareId())
                .cartShareCalId(cartShareOrd.getCartShareCalId())
                .cartShareOrdRcpDts(cartShareOrd.getCartShareOrdRcpDts())
                .ordStatCd(cartShareOrd.getOrdStatCd().name())
                .calStYn(cartShareOrd.getCalStYn())
                .ttlPaymtAmt(cartShareOrd.getTtlPaymtAmt())
                .build();
    }
}
