package com.ssg.sausagememberapi.order.dto.response;

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
public class CartShareOrdTotalPymtAmtForDutchPayResponse {

    private int totalPymtAmt;

    public static CartShareOrdTotalPymtAmtForDutchPayResponse of(int totalPymtAmt) {
        return CartShareOrdTotalPymtAmtForDutchPayResponse.builder()
                .totalPymtAmt(totalPymtAmt)
                .build();
    }
}
