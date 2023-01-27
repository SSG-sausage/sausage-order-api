package com.ssg.sausageorderapi.order.dto.response;

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
@Builder(access = AccessLevel.PUBLIC)
public class CartShareOrdFindForCartShareCalResponse {

    @Schema(description = "공유장바구니 ID")
    private Long cartShareId;

    @Schema(description = "공유장바구니마스터멤버 ID")
    private Long mastrMbrId;

    @Schema(description = "공유장바구니멤버 ID")
    private Set<Long> cartShareMbrIdList;

    @Schema(description = "전체결제금액")
    private int ttlPaymtAmt;
}
