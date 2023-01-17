package com.ssg.sausagememberapi.order.entity;

import com.ssg.sausagememberapi.common.client.internal.dto.response.CartShareItemListResponse.CartShareItemInfo;
import com.ssg.sausagememberapi.common.entity.BaseEntity;
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

    @Column(name = "ITEM_ID", nullable = false)
    private Long itemId;

    @Column(name = "CART_SHARE_ORD_ID", nullable = false)
    private Long cartShareOrdId;

    @Column(name = "MBR_ID", nullable = false)
    private Long mbrId;

    @Column(name = "ITEM_QTY", nullable = false)
    private Integer itemQty;

    @Column(name = "COM_YN", nullable = false)
    private Boolean comYn;

    @Column(name = "ITEM_AMT", nullable = false)
    private Long itemAmt;

    @Column(name = "PAYMT_AMT", nullable = false)
    private Long paymtAmt;

    public static CartShareOdrItem newInstance(CartShareItemInfo cartShareItemInfo, Long cartShareOrdId) {
        return CartShareOdrItem.builder()
                .cartShareOrdId(cartShareOrdId)
                .itemId(cartShareItemInfo.getItemId())
                .mbrId(cartShareItemInfo.getMbrId())
                .itemQty(cartShareItemInfo.getItemQty())
                .comYn(cartShareItemInfo.getComYn())
                .itemAmt(cartShareItemInfo.getItemAmt())
                .paymtAmt(cartShareItemInfo.getItemAmt() * cartShareItemInfo.getItemQty())
                .build();
    }

}
