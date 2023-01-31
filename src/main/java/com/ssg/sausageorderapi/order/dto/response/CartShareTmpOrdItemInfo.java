package com.ssg.sausageorderapi.order.dto.response;

import com.ssg.sausageorderapi.order.entity.CartShareTmpOdrItem;
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

    public static CartShareTmpOrdItemInfo of(CartShareTmpOdrItem cartShareTmpOdrItem) {
        return CartShareTmpOrdItemInfo.builder()
                .cartShareTmpOrdItemId(cartShareTmpOdrItem.getCartShareTmpOrdItemId())
                .cartShareTmpOrdId(cartShareTmpOdrItem.getCartShareTmpOrdId())
                .itemId(cartShareTmpOdrItem.getItemId())
                .mbrId(cartShareTmpOdrItem.getMbrId())
                .itemQty(cartShareTmpOdrItem.getItemQty())
                .itemAmt(cartShareTmpOdrItem.getItemAmt())
                .itemNm(cartShareTmpOdrItem.getItemNm())
                .paymtAmt(cartShareTmpOdrItem.getPaymtAmt())
                .shppCd(cartShareTmpOdrItem.getShppCd().name())
                .commYn(cartShareTmpOdrItem.getCommYn())
                .build();
    }
}
