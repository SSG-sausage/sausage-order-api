package com.ssg.sausageorderapi.order.dto.response;

import com.ssg.sausageorderapi.order.entity.CartShareOrd;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class CartShareOrdFindListResponse {

    @Schema(description = "공유장바구니주문 리스트")
    public List<CartShareOrdInfo> cartShareOdrList;

    public static CartShareOrdFindListResponse of(List<CartShareOrd> cartShareOrdList) {

        List<CartShareOrdInfo> cartShareOrdInfoList = cartShareOrdList.stream()
                .map(CartShareOrdInfo::of)
                .sorted(Comparator.comparing(CartShareOrdInfo::getCartShareOrdRcpDts).reversed())
                .collect(Collectors.toList());

        return CartShareOrdFindListResponse.builder()
                .cartShareOdrList(cartShareOrdInfoList)
                .build();
    }

}
