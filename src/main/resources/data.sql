INSERT INTO `order`.CART_SHARE_TMP_ORD (CART_SHARE_TMP_ORD_ID, CART_SHARE_ID,
                                        CART_SHARE_TMP_ORD_RCP_DTS, TMP_ORD_STAT_CD, REG_DTS,
                                        REGPE_ID, MOD_DTS, MODPE_ID)
VALUES (1, 1, '2023-01-29 23:18:02', 'CANCELED', '2023-01-29 23:18:02', 1, '2023-01-29 23:18:05',
        1);
INSERT INTO `order`.CART_SHARE_ORD_ITEM (CART_SHARE_ORD_ITEM_ID, CART_SHARE_TMP_ORD_ITEM_ID,
                                         CART_SHARE_ORD_ID, ITEM_ID, MBR_ID, ITEM_QTY, ITEM_NM,
                                         COMM_YN, ITEM_AMT, PAYMT_AMT, SHPP_CD, REG_DTS, REGPE_ID,
                                         MOD_DTS, MODPE_ID)
VALUES (1, 1, 1, 1, 1, 1, '표백제 1.5L*2', 0, 19500, 19500, 'EMART_TRADERS_SHPP',
        '2023-01-29 23:18:05', 1, '2023-01-29 23:18:05', 1);
INSERT INTO `order`.CART_SHARE_ORD_ITEM (CART_SHARE_ORD_ITEM_ID, CART_SHARE_TMP_ORD_ITEM_ID,
                                         CART_SHARE_ORD_ID, ITEM_ID, MBR_ID, ITEM_QTY, ITEM_NM,
                                         COMM_YN, ITEM_AMT, PAYMT_AMT, SHPP_CD, REG_DTS, REGPE_ID,
                                         MOD_DTS, MODPE_ID)
VALUES (2, 2, 1, 2, 1, 2, '정선생 곤드레나물밥 (5입)', 0, 9980, 19960, 'EMART_TRADERS_SHPP',
        '2023-01-29 23:18:05', 1, '2023-01-29 23:18:05', 1);
INSERT INTO `order`.CART_SHARE_ORD_ITEM (CART_SHARE_ORD_ITEM_ID, CART_SHARE_TMP_ORD_ITEM_ID,
                                         CART_SHARE_ORD_ID, ITEM_ID, MBR_ID, ITEM_QTY, ITEM_NM,
                                         COMM_YN, ITEM_AMT, PAYMT_AMT, SHPP_CD, REG_DTS, REGPE_ID,
                                         MOD_DTS, MODPE_ID)
VALUES (3, 3, 1, 3, 2, 5, '슈퍼크런치 치킨텐더', 0, 7980, 39900, 'SSG_SHPP', '2023-01-29 23:18:05', 1,
        '2023-01-29 23:18:05', 1);
INSERT INTO `order`.CART_SHARE_ORD_ITEM (CART_SHARE_ORD_ITEM_ID, CART_SHARE_TMP_ORD_ITEM_ID,
                                         CART_SHARE_ORD_ID, ITEM_ID, MBR_ID, ITEM_QTY, ITEM_NM,
                                         COMM_YN, ITEM_AMT, PAYMT_AMT, SHPP_CD, REG_DTS, REGPE_ID,
                                         MOD_DTS, MODPE_ID)
VALUES (4, 4, 1, 4, 1, 10, '소고기 밀푀유나베 (2인분)', 1, 13410, 134100, 'EMART_TRADERS_SHPP',
        '2023-01-29 23:18:05', 1, '2023-01-29 23:18:05', 1);
INSERT INTO `order`.CART_SHARE_TMP_ORD (CART_SHARE_TMP_ORD_ID, CART_SHARE_ID,
                                        CART_SHARE_TMP_ORD_RCP_DTS, TMP_ORD_STAT_CD, REG_DTS,
                                        REGPE_ID, MOD_DTS, MODPE_ID)
VALUES (1, 1, '2023-01-29 23:18:02', 'CANCELED', '2023-01-29 23:18:02', 1, '2023-01-29 23:18:05',
        1);

INSERT INTO `order`.CART_SHARE_TMP_ORD_ITEM (CART_SHARE_TMP_ORD_ITEM_ID, CART_SHARE_TMP_ORD_ID,
                                             ITEM_ID, MBR_ID, ITEM_QTY, COMM_YN, ITEM_AMT, ITEM_NM,
                                             PAYMT_AMT, SHPP_CD, REG_DTS, REGPE_ID, MOD_DTS,
                                             MODPE_ID)
VALUES (1, 1, 1, 1, 1, 0, 19500, '표백제 1.5L*2', 19500, 'EMART_TRADERS_SHPP', '2023-01-29 23:18:03',
        1, '2023-01-29 23:18:03', 1);
INSERT INTO `order`.CART_SHARE_TMP_ORD_ITEM (CART_SHARE_TMP_ORD_ITEM_ID, CART_SHARE_TMP_ORD_ID,
                                             ITEM_ID, MBR_ID, ITEM_QTY, COMM_YN, ITEM_AMT, ITEM_NM,
                                             PAYMT_AMT, SHPP_CD, REG_DTS, REGPE_ID, MOD_DTS,
                                             MODPE_ID)
VALUES (2, 1, 2, 1, 2, 0, 9980, '정선생 곤드레나물밥 (5입)', 19960, 'EMART_TRADERS_SHPP',
        '2023-01-29 23:18:03', 1, '2023-01-29 23:18:03', 1);
INSERT INTO `order`.CART_SHARE_TMP_ORD_ITEM (CART_SHARE_TMP_ORD_ITEM_ID, CART_SHARE_TMP_ORD_ID,
                                             ITEM_ID, MBR_ID, ITEM_QTY, COMM_YN, ITEM_AMT, ITEM_NM,
                                             PAYMT_AMT, SHPP_CD, REG_DTS, REGPE_ID, MOD_DTS,
                                             MODPE_ID)
VALUES (3, 1, 3, 2, 5, 0, 7980, '슈퍼크런치 치킨텐더', 39900, 'SSG_SHPP', '2023-01-29 23:18:03', 1,
        '2023-01-29 23:18:03', 1);
INSERT INTO `order`.CART_SHARE_TMP_ORD_ITEM (CART_SHARE_TMP_ORD_ITEM_ID, CART_SHARE_TMP_ORD_ID,
                                             ITEM_ID, MBR_ID, ITEM_QTY, COMM_YN, ITEM_AMT, ITEM_NM,
                                             PAYMT_AMT, SHPP_CD, REG_DTS, REGPE_ID, MOD_DTS,
                                             MODPE_ID)
VALUES (4, 1, 4, 1, 10, 1, 13410, '소고기 밀푀유나베 (2인분)', 134100, 'EMART_TRADERS_SHPP',
        '2023-01-29 23:18:03', 1, '2023-01-29 23:18:03', 1);

