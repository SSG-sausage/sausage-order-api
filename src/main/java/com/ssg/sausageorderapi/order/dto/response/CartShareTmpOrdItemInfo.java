package com.ssg.sausageorderapi.order.dto.response;

import com.ssg.sausageorderapi.order.entity.CartShareTmpOrdItem;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class CartShareTmpOrdItemInfo {

    @Schema(description = "공유장바구니임시주문상품 ID")
    private Long cartShareTmpOrdItemId;

    @Schema(description = "공유장바구니임시주문 ID")
    private Long cartShareTmpOrdId;

    @Schema(description = "상품 ID")
    private Long itemId;

    @Schema(description = "멤버 ID")
    private Long mbrId;

    @Schema(description = "상품수량")
    private Integer itemQty;

    @Schema(description = "공통상품여부")
    private Boolean commYn;

    @Schema(description = "상품가격")
    private Integer itemAmt;

    @Schema(description = "상품이름")
    private String itemNm;

    @Schema(description = "결제금액")
    private Integer paymtAmt;

    @Schema(description = "배송타입코드")
    private String shppCd;

    public static CartShareTmpOrdItemInfo of(CartShareTmpOrdItem cartShareTmpOrdItem) {
        return CartShareTmpOrdItemInfo.builder()
                .cartShareTmpOrdItemId(cartShareTmpOrdItem.getCartShareTmpOrdItemId())
                .cartShareTmpOrdId(cartShareTmpOrdItem.getCartShareTmpOrdId())
                .itemId(cartShareTmpOrdItem.getItemId())
                .mbrId(cartShareTmpOrdItem.getMbrId())
                .itemQty(cartShareTmpOrdItem.getItemQty())
                .itemAmt(cartShareTmpOrdItem.getItemAmt())
                .itemNm(cartShareTmpOrdItem.getItemNm())
                .paymtAmt(cartShareTmpOrdItem.getPaymtAmt())
                .shppCd(cartShareTmpOrdItem.getShppCd().name())
                .commYn(cartShareTmpOrdItem.getCommYn())
                .build();
    }
}
