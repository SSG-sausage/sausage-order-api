package com.ssg.sausageorderapi.order.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
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
public class CartShareOrdFindListForCartShareCalResponse {

    @Schema(description = "공유장바구니주문 리스트")
    public List<CartShareOrdInfo> cartShareOdrList;

    public static CartShareOrdFindListForCartShareCalResponse of(List<CartShareOrdInfo> cartShareOdrList) {

        List<CartShareOrdInfo> cartShareOrdInfoList = cartShareOdrList.stream()
                .sorted(Comparator.comparing(CartShareOrdInfo::getCartShareOrdRcpDts).reversed())
                .collect(Collectors.toList());

        return CartShareOrdFindListForCartShareCalResponse.builder()
                .cartShareOdrList(cartShareOrdInfoList)
                .build();
    }

    @ToString
    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder(access = AccessLevel.PUBLIC)
    public static class CartShareOrdInfo {

        @Schema(description = "주문접수일시")
        private LocalDateTime cartShareOrdRcpDts;

        @Schema(description = "공유장바구니주문 이름")
        private String cartShareNm;

        @Schema(description = "공유장바구니 멤버 수")
        private Integer cartShareMbrQty;

        @Schema(description = "공유장바구니주문 번호")
        private String cartShareOrdNo;

        @Schema(description = "대표상품 이미지 URL")
        private String repItemImgUrl;

        @Schema(description = "대표상품 이름")
        private String repItemNm;

        @Schema(description = "전체 주문 상품수")
        private Integer ttlOrdItemQty;

        @Schema(description = "총결제 금액")
        private Integer ttlPaymtAmt;

        @Schema(description = "정산 ID")
        private Long cartShareCalId;

        @Schema(description = "정산 시작 여부")
        private Boolean calStYn;
    }
}
