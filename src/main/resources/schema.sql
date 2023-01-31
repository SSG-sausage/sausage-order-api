DROP TABLE IF EXISTS `CART_SHARE_TMP_ORD_ITEM`;
DROP TABLE IF EXISTS `CART_SHARE_TMP_ORD`;
DROP TABLE IF EXISTS `CART_SHARE_ORD_ITEM`;
DROP TABLE IF EXISTS `CART_SHARE_ORD`;

CREATE TABLE `CART_SHARE_ORD`
(
    `CART_SHARE_ORD_ID`      bigint      NOT NULL AUTO_INCREMENT,
    `CART_SHARE_TMP_ORD_ID`  bigint      NOT NULL,
    `CART_SHARE_ID`          bigint      NOT NULL,
    `CART_SHARE_CAL_ID`      bigint NULL,
    `CAL_ST_YN`              boolean     NOT NULL,
    `CART_SHARE_ORD_RCP_DTS` datetime    NOT NULL,
    `ORD_STAT_CD`            varchar(30) NOT NULL,
    `TTL_PAYMT_AMT`          int NULL,
    `CART_SHARE_ORD_NO`      varchar(30) NOT NULL,
    `REG_DTS`                datetime    NOT NULL,
    `REGPE_ID`               bigint NULL,
    `MOD_DTS`                datetime    NOT NULL,
    `MODPE_ID`               bigint NULL,
    PRIMARY KEY (`CART_SHARE_ORD_ID`)
);

CREATE TABLE `CART_SHARE_TMP_ORD`
(
    `CART_SHARE_TMP_ORD_ID`      bigint      NOT NULL AUTO_INCREMENT,
    `CART_SHARE_ID`              bigint      NOT NULL,
    `CART_SHARE_TMP_ORD_RCP_DTS` datetime    NOT NULL,
    `TMP_ORD_STAT_CD`            varchar(30) NOT NULL,
    `REG_DTS`                    datetime    NOT NULL,
    `REGPE_ID`                   bigint NULL,
    `MOD_DTS`                    datetime    NOT NULL,
    `MODPE_ID`                   bigint NULL,
    PRIMARY KEY (`CART_SHARE_TMP_ORD_ID`)
);

CREATE TABLE `CART_SHARE_ORD_ITEM`
(
    `CART_SHARE_ORD_ITEM_ID`     bigint       NOT NULL AUTO_INCREMENT,
    `CART_SHARE_TMP_ORD_ITEM_ID` bigint       NOT NULL,
    `CART_SHARE_ORD_ID`          bigint       NOT NULL,
    `ITEM_ID`                    bigint       NOT NULL,
    `MBR_ID`                     bigint       NOT NULL,
    `MBR_NM`                     varchar(30)  NOT NULL,
    `ITEM_QTY`                   int          NOT NULL,
    `ITEM_NM`                    varchar(30)  NOT NULL,
    `COMM_YN`                    boolean      NOT NULL,
    `ITEM_AMT`                   int          NOT NULL,
    `PAYMT_AMT`                  int          NOT NULL,
    `ITEM_BRAND_NM`              varchar(30)  NOT NULL,
    `ITEM_IMG_URL`               varchar(300) NOT NULL,
    `SHPP_CD`                    varchar(30)  NOT NULL,
    `REG_DTS`                    datetime     NOT NULL,
    `REGPE_ID`                   bigint NULL,
    `MOD_DTS`                    datetime     NOT NULL,
    `MODPE_ID`                   bigint NULL,
    PRIMARY KEY (`CART_SHARE_ORD_ITEM_ID`)
);

CREATE TABLE `CART_SHARE_TMP_ORD_ITEM`
(
    `CART_SHARE_TMP_ORD_ITEM_ID` bigint       NOT NULL AUTO_INCREMENT,
    `CART_SHARE_TMP_ORD_ID`      bigint       NOT NULL,
    `ITEM_ID`                    bigint       NOT NULL,
    `MBR_ID`                     bigint       NOT NULL,
    `MBR_NM`                     varchar(30)  NOT NULL,
    `ITEM_QTY`                   int          NOT NULL,
    `COMM_YN`                    boolean      NOT NULL,
    `ITEM_AMT`                   int          NOT NULL,
    `ITEM_NM`                    varchar(30)  NOT NULL,
    `PAYMT_AMT`                  int          NOT NULL,
    `ITEM_BRAND_NM`              varchar(30)  NOT NULL,
    `ITEM_IMG_URL`               varchar(300) NOT NULL,
    `SHPP_CD`                    varchar(30)  NOT NULL,
    `REG_DTS`                    datetime     NOT NULL,
    `REGPE_ID`                   bigint NULL,
    `MOD_DTS`                    datetime     NOT NULL,
    `MODPE_ID`                   bigint NULL,
    PRIMARY KEY (`CART_SHARE_TMP_ORD_ITEM_ID`)
);