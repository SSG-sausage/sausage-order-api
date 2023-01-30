package com.ssg.sausageorderapi.order.dto.response;

import com.ssg.sausageorderapi.order.entity.CartShareOdrItem;
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
public class CartShareOrdItemInfo {

    @Schema(description = "공유장바구니주문상품 ID")
    private Long cartShareOrdItemId;

    @Schema(description = "공유장바구니주문 ID")
    private Long cartShareOrdId;

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

    @Schema(description = "상품 브랜드 이름")
    private String itemBrandNm;

    @Schema(description = "상품 이미지 URL")
    private String itemImgUrl;

    public static CartShareOrdItemInfo of(CartShareOdrItem cartShareOdrItem) {
        return CartShareOrdItemInfo.builder()
                .cartShareOrdItemId(cartShareOdrItem.getCartShareOrdItemId())
                .cartShareOrdId(cartShareOdrItem.getCartShareOrdId())
                .itemId(cartShareOdrItem.getItemId())
                .mbrId(cartShareOdrItem.getMbrId())
                .itemQty(cartShareOdrItem.getItemQty())
                .itemNm(cartShareOdrItem.getItemNm())
                .itemAmt(cartShareOdrItem.getItemAmt())
                .paymtAmt(cartShareOdrItem.getPaymtAmt())
                .shppCd(cartShareOdrItem.getShppCd().name())
                .commYn(cartShareOdrItem.getCommYn())
                .itemBrandNm(cartShareOdrItem.getItemBrandNm())
                .itemImgUrl(cartShareOdrItem.getItemImgUrl())
                .build();
    }
}
