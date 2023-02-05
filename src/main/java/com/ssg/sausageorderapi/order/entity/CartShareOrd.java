package com.ssg.sausageorderapi.order.entity;

import com.ssg.sausageorderapi.common.entity.BaseEntity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Table(name = "CART_SHARE_ORD")
@Getter
@Entity
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PUBLIC)
public class CartShareOrd extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CART_SHARE_ORD_ID", nullable = false)
    private Long cartShareOrdId;

    @Column(name = "CART_SHARE_TMP_ORD_ID", nullable = false)
    private Long cartShareTmpOrdId;

    @Column(name = "CART_SHARE_ID", nullable = false)
    private Long cartShareId;

    @Column(name = "CART_SHARE_ORD_NO", nullable = false)
    private String cartShareOrdNo;

    @Column(name = "CART_SHARE_CAL_ID")
    private Long cartShareCalId;

    @Column(name = "CAL_ST_YN", nullable = false)
    private Boolean calStYn;

    @Column(name = "TTL_PAYMT_AMT", nullable = true)
    private Integer ttlPaymtAmt;

    @Column(name = "CART_SHARE_ORD_RCP_DTS", nullable = false)
    private LocalDateTime cartShareOrdRcpDts;

    @Enumerated(EnumType.STRING)
    @Column(name = "ORD_STAT_CD", nullable = false)
    private OrdStatCd ordStatCd;

    public void changeCartShareCalId(Long cartShareCalId) {
        this.cartShareCalId = cartShareCalId;
    }

    public void changeCalStYn(Boolean calStYn) {
        this.calStYn = calStYn;
    }

    public void changeTtlPaymtAmt(int ttlPaymtAmt) {
        this.ttlPaymtAmt = ttlPaymtAmt;
    }

    public void changeOrdStatCd(OrdStatCd ordStatCd) {
        this.ordStatCd = ordStatCd;
    }

    public static CartShareOrd newInstance(CartShareTmpOrd cartShareTmpOrd) {

        return CartShareOrd.builder()
                .cartShareId(cartShareTmpOrd.getCartShareId())
                .cartShareTmpOrdId(cartShareTmpOrd.getCartShareTmpOrdId())
                .cartShareOrdRcpDts(LocalDateTime.now())
                .ordStatCd(OrdStatCd.SUCCESS)
                .cartShareOrdNo(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                        + "-" + String.format("%06d", cartShareTmpOrd.getCartShareTmpOrdId()))
                .calStYn(false)
                .build();
    }

}
