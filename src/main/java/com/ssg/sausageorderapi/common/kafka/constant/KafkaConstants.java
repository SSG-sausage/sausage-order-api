package com.ssg.sausageorderapi.common.kafka.constant;

public class KafkaConstants {

    // cart-share
    public static final String KAFKA_CART_SHARE_ITEM_DELETE = "cart-share-service.cart-share-item-list.deleting";
    public static final String KAFKA_CART_SHARE_EDIT_UPDATE_UPDATE = "cart-share-service.cart-share-edit-psbl-yn.updating";
    public static final String KAFKA_CART_SHARE_NOTI_CREATE = "cart-share-service.cart-share-noti.creating";
    public static final String KAFKA_ITEM_INV_QTY_UPDATE = "item-service.item-inv-qty.updating";
    public static final String KAFKA_CART_SHARE_CAL_START = "cart-share-ord-service.cal-st-yn.starting";
    public static final String KAFKA_CART_SHARE_CAL_SAVE_RETRY = "cart-share-cal-service.save-cart-share-cal.retrying";
    public static final String KAFKA_CART_SHARE_ORD_CART_SHARE_CAL_ID_UPDATE = "order-service.cart-share-cal-id.updating";
    public static final String CONSUMER_GROUP_ID = "order-service";
}
