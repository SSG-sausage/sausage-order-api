package com.ssg.sausagememberapi.order.dto.response;

import com.ssg.sausagememberapi.order.entity.ShppCd;
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
@Builder(access = AccessLevel.PRIVATE)
public class CartShareOrdForDutchPayResponse {

    private int totalPymtAmt;

    private int commAmt;

    private List<CartShareOrdShppInfo> shppInfoList = Collections.emptyList();

    private List<CartShareOrdAmtInfo> ordInfoList = Collections.emptyList();

    public static CartShareOrdForDutchPayResponse of(int totalPrice, int commAmt,
            List<CartShareOrdShppInfo> shppInfoList, List<CartShareOrdAmtInfo> ordInfoList) {

        return CartShareOrdForDutchPayResponse.builder()
                .totalPymtAmt(totalPrice)
                .commAmt(commAmt)
                .shppInfoList(shppInfoList)
                .ordInfoList(ordInfoList)
                .build();
    }

    @ToString
    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder(access = AccessLevel.PRIVATE)
    public static class CartShareOrdAmtInfo {

        private Long mbrId;

        // 배송비 제외 주문 금액
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

        private int shppCst;

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
