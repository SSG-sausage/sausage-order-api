package com.ssg.sausageorderapi.order.dto.response;

import com.ssg.sausageorderapi.order.entity.ShppCd;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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
public class CartShareOrdFindDetailForCartShareCalResponse {

    @Schema(description = "공통 금액")
    private int commAmt;

    @Schema(description = "공유장바구니 배송 정보 리스트")
    private List<CartShareOrdShppInfo> shppInfoList = Collections.emptyList();

    @Schema(description = "공유장바구니주문 금액 정보 리스트")
    private List<CartShareOrdAmtInfo> ordInfoList = Collections.emptyList();

    @ToString
    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder(access = AccessLevel.PRIVATE)
    public static class CartShareOrdAmtInfo {

        @Schema(description = "멤버 ID")
        private Long mbrId;

        // 배송비 제외 주문 금액
        @Schema(description = "주문 금액")
        private int ordAmt;

        public void addOrdAmt(int ordAmt) {
            this.ordAmt += ordAmt;
        }

        public static CartShareOrdAmtInfo of(Long mbrId, int ordAmt) {
            return CartShareOrdAmtInfo.builder()
                    .mbrId(mbrId)
                    .ordAmt(ordAmt)
                    .build();
        }
    }

    @ToString
    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder(access = AccessLevel.PRIVATE)
    public static class CartShareOrdShppInfo {

        @Schema(description = "배송비")
        private int shppCst;

        @Schema(description = "멤버 ID 리스트")
        private Set<Long> mbrIdList = new HashSet<>();

        public void addMbrId(Long mbrId) {
            this.mbrIdList.add(mbrId);
        }

        public static CartShareOrdShppInfo of(ShppCd shppCd) {

            return CartShareOrdShppInfo.builder()
                    .shppCst(shppCd.getShppCst())
                    .mbrIdList(new HashSet<>())
                    .build();
        }
    }
}
