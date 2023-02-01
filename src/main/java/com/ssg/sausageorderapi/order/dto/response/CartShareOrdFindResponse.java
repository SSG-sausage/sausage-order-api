package com.ssg.sausageorderapi.order.dto.response;

import com.ssg.sausageorderapi.order.entity.CartShareOrd;
import com.ssg.sausageorderapi.order.entity.CartShareOrdItem;
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

    public static CartShareOrdFindResponse of(CartShareOrd cartShareOrd, List<CartShareOrdItem> cartShareOrdItemList) {

        List<CartShareOrdItemInfo> cartShareOrdItemInfoList = cartShareOrdItemList.stream()
                .map(CartShareOrdItemInfo::of)
                .collect(Collectors.toList());

        return CartShareOrdFindResponse.builder()
                .cartShareId(cartShareOrd.getCartShareId())
                .cartShareOrdId(cartShareOrd.getCartShareOrdId())
                .cartShareOrdRcpDts(cartShareOrd.getRegDts())
                .cartShareOrdItemList(cartShareOrdItemInfoList)
                .build();
    }

}
