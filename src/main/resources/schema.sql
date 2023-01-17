DROP TABLE IF EXISTS `CART_SHARE_ORD`;
DROP TABLE IF EXISTS `CART_SHARE_ORD_ITEM`;

CREATE TABLE `CART_SHARE_ORD`
(
    `CART_SHARE_ORD_ID`      bigint   NOT NULL AUTO_INCREMENT,
    `CART_SHARE_ID`          bigint   NOT NULL,
    `CART_SHARE_ORD_RCP_DTS` datetime NOT NULL,
    `REG_DTS`                datetime NOT NULL,
    `REGPE_ID`               bigint NULL,
    `MOD_DTS`                datetime NOT NULL,
    `MODPE_ID`               bigint NULL,
    PRIMARY KEY (`CART_SHARE_ORD_ID`)
);

CREATE TABLE `CART_SHARE_ORD_ITEM`
(
    `CART_SHARE_ORD_ITEM_ID` bigint   NOT NULL AUTO_INCREMENT,
    `ITEM_ID`                bigint   NOT NULL,
    `CART_SHARE_ORD_ID`      bigint   NOT NULL,
    `MBR_ID`                 bigint   NOT NULL,
    `ITEM_QTY`               int      NOT NULL,
    `COM_YN`                 boolean  NOT NULL,
    `ITEM_AMT`               int      NOT NULL,
    `PAYMT_AMT`              int      NOT NULL,
    `REG_DTS`                datetime NOT NULL,
    `REGPE_ID`               bigint NULL,
    `MOD_DTS`                datetime NOT NULL,
    `MODPE_ID`               bigint NULL,
    PRIMARY KEY (`CART_SHARE_ORD_ITEM_ID`)
);