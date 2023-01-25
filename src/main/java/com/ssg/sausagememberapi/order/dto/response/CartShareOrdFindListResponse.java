package com.ssg.sausagememberapi.order.dto.response;

import com.ssg.sausagememberapi.order.entity.CartShareOdr;
import io.swagger.v3.oas.annotations.media.Schema;
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

    public static CartShareOrdFindListResponse of(List<CartShareOdr> cartShareOdrList) {

        List<CartShareOrdInfo> cartShareOrdInfoList = cartShareOdrList.stream()
                .map(CartShareOrdInfo::of)
                .collect(Collectors.toList());

        return CartShareOrdFindListResponse.builder()
                .cartShareOdrList(cartShareOrdInfoList)
                .build();
    }

}
