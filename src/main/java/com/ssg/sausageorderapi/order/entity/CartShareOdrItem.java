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
public class CartShareOdrItem extends BaseEntity {

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

    public static CartShareOdrItem newInstance(Long cartShareOrdId, CartShareTmpOdrItem cartShareTmpOdrItem) {

        return CartShareOdrItem.builder()
                .cartShareOrdId(cartShareOrdId)
                .cartShareTmpOrdItemId(cartShareTmpOdrItem.getCartShareTmpOrdItemId())
                .itemId(cartShareTmpOdrItem.getItemId())
                .mbrId(cartShareTmpOdrItem.getMbrId())
                .itemQty(cartShareTmpOdrItem.getItemQty())
                .itemNm(cartShareTmpOdrItem.getItemNm())
                .commYn(cartShareTmpOdrItem.getCommYn())
                .itemAmt(cartShareTmpOdrItem.getItemAmt())
                .itemBrandNm(cartShareTmpOdrItem.getItemBrandNm())
                .itemImgUrl(cartShareTmpOdrItem.getItemImgUrl())
                .paymtAmt(cartShareTmpOdrItem.getItemAmt() * cartShareTmpOdrItem.getItemQty())
                .shppCd(cartShareTmpOdrItem.getShppCd())
                .build();
    }

}
