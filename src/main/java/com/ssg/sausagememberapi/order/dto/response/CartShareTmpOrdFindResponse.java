package com.ssg.sausagememberapi.order.dto.response;

import com.ssg.sausagememberapi.order.entity.CartShareTmpOdr;
import com.ssg.sausagememberapi.order.entity.CartShareTmpOdrItem;
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

    public static CartShareTmpOrdFindResponse of(CartShareTmpOdr cartShareTmpOdr,
            List<CartShareTmpOdrItem> cartShareTmpOdrItemList) {

        List<CartShareTmpOrdItemInfo> cartShareTmpOrdItemInfoList = cartShareTmpOdrItemList.stream()
                .map(CartShareTmpOrdItemInfo::of)
                .collect(Collectors.toList());

        return CartShareTmpOrdFindResponse.builder()
                .cartShareId(cartShareTmpOdr.getCartShareId())
                .cartShareTmpOrdId(cartShareTmpOdr.getCartShareTmpOrdId())
                .cartShareTmpOrdRcpDts(cartShareTmpOdr.getRegDts())
                .cartShareTmpOrdItemInfoList(cartShareTmpOrdItemInfoList)
                .build();
    }
}
