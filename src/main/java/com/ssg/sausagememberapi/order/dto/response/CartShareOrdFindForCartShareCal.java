package com.ssg.sausagememberapi.order.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
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
public class CartShareOrdFindForCartShareCal {

    @Schema(description = "공유장바구니 ID")
    private Long cartShareId;

    @Schema(description = "공유장바구니 ID")
    private Long mastrMbrId;

    private Set<Long> mbrIdList;

    private int totalPaymtAmt;

    public static CartShareOrdFindForCartShareCal of(int totalPymtAmt) {
        return CartShareOrdFindForCartShareCal.builder()
                .totalPaymtAmt(totalPymtAmt)
                .build();
    }
}
