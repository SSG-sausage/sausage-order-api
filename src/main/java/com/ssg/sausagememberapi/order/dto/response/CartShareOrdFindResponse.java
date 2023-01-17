package com.ssg.sausagememberapi.order.dto.response;

import com.ssg.sausagememberapi.order.entity.CartShareOdr;
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
public class CartShareOrdFindResponse {

    @Schema(description = "공유장바구니주문 ID")
    private Long cartShareOrdId;

    @Schema(description = "공유장바구니ID")
    private Long cartShareId;

    @Schema(description = "주문접수일시")
    private LocalDateTime cartShareOrdRcpDts;

    public static CartShareOrdFindResponse of(CartShareOdr cartShareOdr) {
        return CartShareOrdFindResponse.builder()
                .cartShareId(cartShareOdr.getCartShareId())
                .cartShareOrdId(cartShareOdr.getCartShareOrdId())
                .cartShareOrdRcpDts(cartShareOdr.getRegDts())
                .build();
    }

}
