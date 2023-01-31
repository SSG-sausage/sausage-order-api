package com.ssg.sausageorderapi.order.entity;

import com.ssg.sausageorderapi.common.entity.BaseEntity;
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
@Builder
public class CartShareOrdItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CART_SHARE_ORD_ITEM_ID", nullable = false)
    private Long cartShareOrdItemId;

    @Column(name = "CART_SHARE_TMP_ORD_ITEM_ID", nullable = false)
    private Long cartShareTmpOrdItemId;

    @Column(name = "CART_SHARE_ORD_ID", nullable = false)
    private Long cartShareOrdId;

    @Column(name = "ITEM_ID", nullable = false)
    private Long itemId;

    @Column(name = "MBR_ID", nullable = false)
    private Long mbrId;

    @Column(name = "MBR_NM", nullable = false)
    private String mbrNm;

    @Column(name = "ITEM_QTY", nullable = false)
    private Integer itemQty;

    @Column(name = "COMM_YN", nullable = false)
    private Boolean commYn;

    @Column(name = "ITEM_AMT", nullable = false)
    private Integer itemAmt;

    @Column(name = "ITEM_NM", nullable = false)
    private String itemNm;

    @Column(name = "PAYMT_AMT", nullable = false)
    private Integer paymtAmt;

    @Column(name = "ITEM_BRAND_NM")
    private String itemBrandNm;

    @Column(name = "ITEM_IMG_URL")
    private String itemImgUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "SHPP_CD")
    private ShppCd shppCd;

    public static CartShareOrdItem newInstance(Long cartShareOrdId, CartShareTmpOrdItem cartShareTmpOrdItem) {

        return CartShareOrdItem.builder()
                .cartShareOrdId(cartShareOrdId)
                .cartShareTmpOrdItemId(cartShareTmpOrdItem.getCartShareTmpOrdItemId())
                .itemId(cartShareTmpOrdItem.getItemId())
                .mbrId(cartShareTmpOrdItem.getMbrId())
                .itemQty(cartShareTmpOrdItem.getItemQty())
                .itemNm(cartShareTmpOrdItem.getItemNm())
                .commYn(cartShareTmpOrdItem.getCommYn())
                .itemAmt(cartShareTmpOrdItem.getItemAmt())
                .itemBrandNm(cartShareTmpOrdItem.getItemBrandNm())
                .itemImgUrl(cartShareTmpOrdItem.getItemImgUrl())
                .paymtAmt(cartShareTmpOrdItem.getItemAmt() * cartShareTmpOrdItem.getItemQty())
                .shppCd(cartShareTmpOrdItem.getShppCd())
                .mbrNm(cartShareTmpOrdItem.getMbrNm())
                .build();
    }

}
