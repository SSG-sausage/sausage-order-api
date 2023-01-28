package com.ssg.sausageorderapi.order.dto.response;

import com.ssg.sausageorderapi.order.entity.CartShareOdr;
import com.ssg.sausageorderapi.order.entity.CartShareOdrItem;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
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
public class CartShareOrdFindResponse {

    @Schema(description = "공유장바구니주문 ID")
    private Long cartShareOrdId;

    @Schema(description = "공유장바구니 ID")
    private Long cartShareId;

    @Schema(description = "주문접수일시")
    private LocalDateTime cartShareOrdRcpDts;

    @Schema(description = "공유장바구니주문상품 리스트")
    private List<CartShareOrdItemInfo> cartShareOrdItemList;

    public static CartShareOrdFindResponse of(CartShareOdr cartShareOdr, List<CartShareOdrItem> cartShareOdrItemList) {

        List<CartShareOrdItemInfo> cartShareOrdItemInfoList = cartShareOdrItemList.stream()
                .map(CartShareOrdItemInfo::of)
                .collect(Collectors.toList());

        return CartShareOrdFindResponse.builder()
                .cartShareId(cartShareOdr.getCartShareId())
                .cartShareOrdId(cartShareOdr.getCartShareOrdId())
                .cartShareOrdRcpDts(cartShareOdr.getRegDts())
                .cartShareOrdItemList(cartShareOrdItemInfoList)
                .build();
    }

}
