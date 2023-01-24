package com.ssg.sausagememberapi.order.entity;

import com.ssg.sausagememberapi.common.entity.BaseEntity;
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

@Table(name = "CART_SHARE_ORD_ITEM")
@Getter
@Entity
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PUBLIC)
public class CartShareOdrItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CART_SHARE_ORD_ITEM_ID", nullable = false)
    private Long cartShareOrdItemId;

    @Column(name = "CART_SHARE_ORD_ID", nullable = false)
    private Long cartShareOrdId;

    @Column(name = "CART_SHARE_TMP_ORD_ITEM_ID", nullable = false)
    private Long cartShareTmpOrdItemId;

    @Column(name = "ITEM_ID", nullable = false)
    private Long itemId;

    @Column(name = "MBR_ID", nullable = false)
    private Long mbrId;

    @Column(name = "ITEM_QTY", nullable = false)
    private Integer itemQty;

    @Column(name = "COM_YN", nullable = false)
    private Boolean comYn;

    @Column(name = "ITEM_AMT", nullable = false)
    private Integer itemAmt;

    @Column(name = "PAYMT_AMT", nullable = false)
    private Integer paymtAmt;

    @Enumerated(EnumType.STRING)
    @Column(name = "SHPP_CD")
    private ShppCd shppCd;

    public static CartShareOdrItem newInstance(Long cartShareOrdId, CartShareTmpOdrItem cartShareTmpOdrItem) {

        return CartShareOdrItem.builder()
                .cartShareOrdId(cartShareOrdId)
                .cartShareTmpOrdItemId(cartShareTmpOdrItem.getCartShareTmpOrdItemId())
                .itemId(cartShareTmpOdrItem.getItemId())
                .mbrId(cartShareTmpOdrItem.getMbrId())
                .itemQty(cartShareTmpOdrItem.getItemQty())
                .comYn(cartShareTmpOdrItem.getComYn())
                .itemAmt(cartShareTmpOdrItem.getItemAmt())
                .paymtAmt(cartShareTmpOdrItem.getItemAmt() * cartShareTmpOdrItem.getItemQty())
                .shppCd(cartShareTmpOdrItem.getShppCd())
                .build();
    }

}
