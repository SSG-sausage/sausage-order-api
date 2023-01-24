package com.ssg.sausagememberapi.order.entity;

import com.ssg.sausagememberapi.common.client.internal.dto.response.CartShareItemListResponse.CartShareItemInfo;
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

@Table(name = "CART_SHARE_TMP_ORD_ITEM")
@Getter
@Entity
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class CartShareTmpOdrItem extends CartShareOdrItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CART_SHARE_TMP_ORD_ITEM_ID", nullable = false)
    private Long cartShareTmpOrdItemId;

    @Column(name = "CART_SHARE_TMP_ORD_ID", nullable = false)
    private Long cartShareTmpOrdId;

    @Column(name = "ITEM_ID", nullable = false)
    protected Long itemId;

    @Column(name = "MBR_ID", nullable = false)
    protected Long mbrId;

    @Column(name = "ITEM_QTY", nullable = false)
    protected Integer itemQty;

    @Column(name = "COM_YN", nullable = false)
    protected Boolean comYn;

    @Column(name = "ITEM_AMT", nullable = false)
    protected Integer itemAmt;

    @Column(name = "PAYMT_AMT", nullable = false)
    protected Integer paymtAmt;

    @Enumerated(EnumType.STRING)
    @Column(name = "SHPP_CD")
    protected ShppCd shppCd;

    public static CartShareTmpOdrItem newInstance(CartShareItemInfo cartShareItemInfo, Long cartShareTmpOrdId) {

        return CartShareTmpOdrItem.builder()
                .cartShareTmpOrdId(cartShareTmpOrdId)
                .itemId(cartShareItemInfo.getItemId())
                .mbrId(cartShareItemInfo.getMbrId())
                .itemQty(cartShareItemInfo.getItemQty())
                .comYn(cartShareItemInfo.isComYn())
                .itemAmt(cartShareItemInfo.getItemAmt())
                .paymtAmt(cartShareItemInfo.getItemAmt() * cartShareItemInfo.getItemQty())
                .shppCd(ShppCd.valueOf(cartShareItemInfo.getShppCd()))
                .build();
    }

}
