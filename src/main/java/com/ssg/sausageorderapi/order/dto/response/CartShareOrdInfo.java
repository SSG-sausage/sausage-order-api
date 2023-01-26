package com.ssg.sausageorderapi.order.dto.response;

import com.ssg.sausageorderapi.order.entity.CartShareOdr;
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

    @Schema(description = "공유장바구니ID")
    private Long cartShareId;

    @Schema(description = "주문접수일시")
    private LocalDateTime cartShareOrdRcpDts;

    @Schema(description = "주문상태코드")
    private String ordStatCd;

    public static CartShareOrdInfo of(CartShareOdr cartShareOdr) {
        return CartShareOrdInfo.builder()
                .cartShareOrdId(cartShareOdr.getCartShareOrdId())
                .cartShareId(cartShareOdr.getCartShareId())
                .cartShareOrdRcpDts(cartShareOdr.getCartShareOrdRcpDts())
                .ordStatCd(cartShareOdr.getOrdStatCd().name())
                .build();
    }
}
