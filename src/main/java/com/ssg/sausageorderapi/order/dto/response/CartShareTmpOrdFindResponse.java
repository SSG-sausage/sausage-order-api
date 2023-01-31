package com.ssg.sausageorderapi.order.dto.response;

import com.ssg.sausageorderapi.order.entity.CartShareTmpOrd;
import com.ssg.sausageorderapi.order.entity.CartShareTmpOrdItem;
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
public class CartShareTmpOrdFindResponse {

    @Schema(description = "공유장바구니임시주문 ID")
    private Long cartShareTmpOrdId;

    @Schema(description = "공유장바구니ID")
    private Long cartShareId;

    @Schema(description = "임시주문접수일시")
    private LocalDateTime cartShareTmpOrdRcpDts;

    @Schema(description = "공유장바구니임시주문 리스트")
    private List<CartShareTmpOrdItemInfo> cartShareTmpOrdItemInfoList;

    public static CartShareTmpOrdFindResponse of(CartShareTmpOrd cartShareTmpOrd,
            List<CartShareTmpOrdItem> cartShareTmpOrdItemList) {

        List<CartShareTmpOrdItemInfo> cartShareTmpOrdItemInfoList = cartShareTmpOrdItemList.stream()
                .map(CartShareTmpOrdItemInfo::of)
                .collect(Collectors.toList());

        return CartShareTmpOrdFindResponse.builder()
                .cartShareId(cartShareTmpOrd.getCartShareId())
                .cartShareTmpOrdId(cartShareTmpOrd.getCartShareTmpOrdId())
                .cartShareTmpOrdRcpDts(cartShareTmpOrd.getRegDts())
                .cartShareTmpOrdItemInfoList(cartShareTmpOrdItemInfoList)
                .build();
    }
}
