package com.ssg.sausagememberapi.order.entity;

import com.ssg.sausagememberapi.common.entity.BaseEntity;
import java.time.LocalDateTime;
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
public class CartShareOdr extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CART_SHARE_ORD_ID", nullable = false)
    private Long cartShareOrdId;

    @Column(name = "CART_SHARE_TMP_ORD_ID", nullable = false)
    private Long cartShareTmpOrdId;

    @Column(name = "CART_SHARE_ID", nullable = false)
    private Long cartShareId;

    @Column(name = "CART_SHARE_ORD_RCP_DTS", nullable = false)
    private LocalDateTime cartShareOrdRcpDts;

    @Enumerated(EnumType.STRING)
    @Column(name = "ORD_STAT_CD", nullable = false)
    private OrdStatCd ordStatCd;

    public static CartShareOdr newInstance(CartShareTmpOdr cartShareTmpOdr) {
        return CartShareOdr.builder()
                .cartShareId(cartShareTmpOdr.getCartShareId())
                .cartShareTmpOrdId(cartShareTmpOdr.getCartShareTmpOrdId())
                .cartShareOrdRcpDts(LocalDateTime.now())
                .ordStatCd(OrdStatCd.SUCCESS)
                .build();
    }

}
