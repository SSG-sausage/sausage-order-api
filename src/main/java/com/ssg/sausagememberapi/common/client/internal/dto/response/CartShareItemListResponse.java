package com.ssg.sausagememberapi.common.client.internal.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartShareItemListResponse {

    @Schema(description = "공유장바구니상품 ID 리스트")
    private List<CartShareItemInfo> cartShareItemList;

    @ToString
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Builder
    public static class CartShareItemInfo {

        @Schema(description = "공유장바구니상품 ID")
        private Long cartShareItemId;

        @Schema(description = "상품 ID")
        private Long itemId;

        @Schema(description = "멤버 ID")
        private Long mbrId;

        @Schema(description = "상품이름")
        private String itemNm;

        @Schema(description = "상품가격")
        private int itemAmt;
        @Schema(description = "배송타입코드")
        private String shppCd;

        @Schema(description = "상품수량")
        private int itemQty;

        @Schema(description = "공통상품여부")
        private boolean comYn;
    }
}
