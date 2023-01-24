INSERT INTO `order`.CART_SHARE_TMP_ORD (CART_SHARE_TMP_ORD_ID, CART_SHARE_ID,
                                        CART_SHARE_TMP_ORD_RCP_DTS, TMP_ORD_STAT_CD, REG_DTS,
                                        REGPE_ID, MOD_DTS, MODPE_ID)
VALUES (1, 1, '2023-01-24 23:17:01', 'IN_PROGRESS', '2023-01-24 23:17:02', 1, '2023-01-24 23:17:02',
        1);

INSERT INTO `order`.CART_SHARE_TMP_ORD_ITEM (CART_SHARE_TMP_ORD_ITEM_ID, CART_SHARE_TMP_ORD_ID,
                                             ITEM_ID, MBR_ID, ITEM_QTY, COM_YN, ITEM_AMT, PAYMT_AMT,
                                             SHPP_CD, REG_DTS, REGPE_ID, MOD_DTS, MODPE_ID)
VALUES (1, 1, 1, 1, 1, 1, 1000, 1000, 'SSG_SHPP', '2023-01-24 23:17:02', 1, '2023-01-24 23:17:02',
        1);

INSERT INTO `order`.CART_SHARE_TMP_ORD_ITEM (CART_SHARE_TMP_ORD_ITEM_ID, CART_SHARE_TMP_ORD_ID,
                                             ITEM_ID, MBR_ID, ITEM_QTY, COM_YN, ITEM_AMT, PAYMT_AMT,
                                             SHPP_CD, REG_DTS, REGPE_ID, MOD_DTS, MODPE_ID)
VALUES (2, 1, 2, 2, 2, 0, 2000, 4000, 'SSG_SHPP', '2023-01-24 23:17:02', 1, '2023-01-24 23:17:02',
        1);

INSERT INTO `order`.CART_SHARE_TMP_ORD_ITEM (CART_SHARE_TMP_ORD_ITEM_ID, CART_SHARE_TMP_ORD_ID,
                                             ITEM_ID, MBR_ID, ITEM_QTY, COM_YN, ITEM_AMT, PAYMT_AMT,
                                             SHPP_CD, REG_DTS, REGPE_ID, MOD_DTS, MODPE_ID)
VALUES (3, 1, 3, 1, 2, 1, 4000, 8000, 'EMART_TRADERS_SHPP', '2023-01-24 23:17:02', 1,
        '2023-01-24 23:17:02', 1);

INSERT INTO `order`.CART_SHARE_ORD (CART_SHARE_ORD_ID, CART_SHARE_TMP_ORD_ID, CART_SHARE_ID,
                                    CART_SHARE_ORD_RCP_DTS, ORD_STAT_CD, REG_DTS, REGPE_ID, MOD_DTS,
                                    MODPE_ID)
VALUES (1, 1, 1, '2023-01-24 23:18:38', 'SUCCESS', '2023-01-24 23:18:38', 1, '2023-01-24 23:18:38',
        1);

INSERT INTO `order`.CART_SHARE_ORD_ITEM (CART_SHARE_ORD_ITEM_ID, CART_SHARE_TMP_ORD_ITEM_ID,
                                         CART_SHARE_ORD_ID, ITEM_ID, MBR_ID, ITEM_QTY, COM_YN,
                                         ITEM_AMT, PAYMT_AMT, SHPP_CD, REG_DTS, REGPE_ID, MOD_DTS,
                                         MODPE_ID)
VALUES (1, 1, 1, 1, 1, 1, 1, 1000, 1000, 'SSG_SHPP', '2023-01-24 23:18:38', 1,
        '2023-01-24 23:18:38', 1);
INSERT INTO `order`.CART_SHARE_ORD_ITEM (CART_SHARE_ORD_ITEM_ID, CART_SHARE_TMP_ORD_ITEM_ID,
                                         CART_SHARE_ORD_ID, ITEM_ID, MBR_ID, ITEM_QTY, COM_YN,
                                         ITEM_AMT, PAYMT_AMT, SHPP_CD, REG_DTS, REGPE_ID, MOD_DTS,
                                         MODPE_ID)
VALUES (2, 2, 1, 2, 2, 2, 0, 2000, 4000, 'SSG_SHPP', '2023-01-24 23:18:38', 1,
        '2023-01-24 23:18:38', 1);
INSERT INTO `order`.CART_SHARE_ORD_ITEM (CART_SHARE_ORD_ITEM_ID, CART_SHARE_TMP_ORD_ITEM_ID,
                                         CART_SHARE_ORD_ID, ITEM_ID, MBR_ID, ITEM_QTY, COM_YN,
                                         ITEM_AMT, PAYMT_AMT, SHPP_CD, REG_DTS, REGPE_ID, MOD_DTS,
                                         MODPE_ID)
VALUES (3, 3, 1, 3, 1, 2, 1, 4000, 8000, 'EMART_TRADERS_SHPP', '2023-01-24 23:18:38', 1,
        '2023-01-24 23:18:38', 1);

