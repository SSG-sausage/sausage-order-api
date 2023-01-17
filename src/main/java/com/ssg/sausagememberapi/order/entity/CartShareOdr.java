package com.ssg.sausagememberapi.order.entity;

import com.ssg.sausagememberapi.common.entity.BaseEntity;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
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

    @Column(name = "CART_SHARE_ID", nullable = false)
    private Long cartShareId;

    @Column(name = "CART_SHARE_ORD_RCP_DTS", nullable = false)
    private LocalDateTime cartShareOrdRcpDts;

    public static CartShareOdr newInstance(Long cartShareId) {
        return CartShareOdr.builder()
                .cartShareId(cartShareId)
                .cartShareOrdRcpDts(LocalDateTime.now())
                .build();
    }

}
