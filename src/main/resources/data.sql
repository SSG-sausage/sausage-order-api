INSERT INTO `order`.CART_SHARE_TMP_ORD (CART_SHARE_TMP_ORD_ID, CART_SHARE_ID,
                                        CART_SHARE_TMP_ORD_RCP_DTS, TMP_ORD_STAT_CD, REG_DTS,
                                        REGPE_ID, MOD_DTS, MODPE_ID)
VALUES (1, 1, '2023-01-30 13:41:19', 'CANCELED', '2023-01-30 13:41:19', 1, '2023-01-30 13:41:31',
        1);
INSERT INTO `order`.CART_SHARE_TMP_ORD (CART_SHARE_TMP_ORD_ID, CART_SHARE_ID,
                                        CART_SHARE_TMP_ORD_RCP_DTS, TMP_ORD_STAT_CD, REG_DTS,
                                        REGPE_ID, MOD_DTS, MODPE_ID)
VALUES (2, 1, '2023-01-30 13:44:16', 'CANCELED', '2023-01-30 13:44:16', 1, '2023-01-30 13:44:20',
        1);

INSERT INTO `order`.CART_SHARE_TMP_ORD_ITEM (CART_SHARE_TMP_ORD_ITEM_ID, CART_SHARE_TMP_ORD_ID,
                                             ITEM_ID, MBR_ID, ITEM_QTY, COMM_YN, ITEM_AMT, ITEM_NM,
                                             PAYMT_AMT, ITEM_BRAND_NM, ITEM_IMG_URL, SHPP_CD,
                                             REG_DTS, REGPE_ID, MOD_DTS, MODPE_ID)
VALUES (1, 1, 1, 1, 1, 0, 19500, '표백제 1.5L*2', 19500, '유한젠',
        'https://sitem.ssgcdn.com/76/82/32/item/1000518328276_i1_1100.jpg', 'SSG_TRADERS_SHPP',
        '2023-01-30 13:41:19', 1, '2023-01-30 13:41:19', 1);
INSERT INTO `order`.CART_SHARE_TMP_ORD_ITEM (CART_SHARE_TMP_ORD_ITEM_ID, CART_SHARE_TMP_ORD_ID,
                                             ITEM_ID, MBR_ID, ITEM_QTY, COMM_YN, ITEM_AMT, ITEM_NM,
                                             PAYMT_AMT, ITEM_BRAND_NM, ITEM_IMG_URL, SHPP_CD,
                                             REG_DTS, REGPE_ID, MOD_DTS, MODPE_ID)
VALUES (2, 1, 2, 1, 2, 0, 9980, '정선생 곤드레나물밥 (5입)', 19960, '피코크',
        'https://sitem.ssgcdn.com/24/74/27/item/1000174277424_i1_550.jpg', 'SSG_TRADERS_SHPP',
        '2023-01-30 13:41:19', 1, '2023-01-30 13:41:19', 1);
INSERT INTO `order`.CART_SHARE_TMP_ORD_ITEM (CART_SHARE_TMP_ORD_ITEM_ID, CART_SHARE_TMP_ORD_ID,
                                             ITEM_ID, MBR_ID, ITEM_QTY, COMM_YN, ITEM_AMT, ITEM_NM,
                                             PAYMT_AMT, ITEM_BRAND_NM, ITEM_IMG_URL, SHPP_CD,
                                             REG_DTS, REGPE_ID, MOD_DTS, MODPE_ID)
VALUES (3, 1, 3, 2, 5, 0, 7980, '슈퍼크런치 치킨텐더', 39900, '올반',
        'https://sitem.ssgcdn.com/64/93/56/item/1000033569364_i1_1100.jpg', 'SSG_SHPP',
        '2023-01-30 13:41:19', 1, '2023-01-30 13:41:19', 1);
INSERT INTO `order`.CART_SHARE_TMP_ORD_ITEM (CART_SHARE_TMP_ORD_ITEM_ID, CART_SHARE_TMP_ORD_ID,
                                             ITEM_ID, MBR_ID, ITEM_QTY, COMM_YN, ITEM_AMT, ITEM_NM,
                                             PAYMT_AMT, ITEM_BRAND_NM, ITEM_IMG_URL, SHPP_CD,
                                             REG_DTS, REGPE_ID, MOD_DTS, MODPE_ID)
VALUES (4, 1, 4, 1, 10, 1, 13410, '소고기 밀푀유나베 (2인분)', 134100, '이마트',
        'https://sitem.ssgcdn.com/97/99/84/item/2097000849997_i1_1100.jpg', 'SSG_TRADERS_SHPP',
        '2023-01-30 13:41:19', 1, '2023-01-30 13:41:19', 1);
INSERT INTO `order`.CART_SHARE_TMP_ORD_ITEM (CART_SHARE_TMP_ORD_ITEM_ID, CART_SHARE_TMP_ORD_ID,
                                             ITEM_ID, MBR_ID, ITEM_QTY, COMM_YN, ITEM_AMT, ITEM_NM,
                                             PAYMT_AMT, ITEM_BRAND_NM, ITEM_IMG_URL, SHPP_CD,
                                             REG_DTS, REGPE_ID, MOD_DTS, MODPE_ID)
VALUES (5, 2, 1, 1, 1, 0, 19500, '표백제 1.5L*2', 19500, '유한젠',
        'https://sitem.ssgcdn.com/76/82/32/item/1000518328276_i1_1100.jpg', 'SSG_TRADERS_SHPP',
        '2023-01-30 13:44:17', 1, '2023-01-30 13:44:17', 1);
INSERT INTO `order`.CART_SHARE_TMP_ORD_ITEM (CART_SHARE_TMP_ORD_ITEM_ID, CART_SHARE_TMP_ORD_ID,
                                             ITEM_ID, MBR_ID, ITEM_QTY, COMM_YN, ITEM_AMT, ITEM_NM,
                                             PAYMT_AMT, ITEM_BRAND_NM, ITEM_IMG_URL, SHPP_CD,
                                             REG_DTS, REGPE_ID, MOD_DTS, MODPE_ID)
VALUES (6, 2, 2, 1, 2, 0, 9980, '정선생 곤드레나물밥 (5입)', 19960, '피코크',
        'https://sitem.ssgcdn.com/24/74/27/item/1000174277424_i1_550.jpg', 'SSG_TRADERS_SHPP',
        '2023-01-30 13:44:17', 1, '2023-01-30 13:44:17', 1);
INSERT INTO `order`.CART_SHARE_TMP_ORD_ITEM (CART_SHARE_TMP_ORD_ITEM_ID, CART_SHARE_TMP_ORD_ID,
                                             ITEM_ID, MBR_ID, ITEM_QTY, COMM_YN, ITEM_AMT, ITEM_NM,
                                             PAYMT_AMT, ITEM_BRAND_NM, ITEM_IMG_URL, SHPP_CD,
                                             REG_DTS, REGPE_ID, MOD_DTS, MODPE_ID)
VALUES (7, 2, 3, 2, 5, 0, 7980, '슈퍼크런치 치킨텐더', 39900, '올반',
        'https://sitem.ssgcdn.com/64/93/56/item/1000033569364_i1_1100.jpg', 'SSG_SHPP',
        '2023-01-30 13:44:17', 1, '2023-01-30 13:44:17', 1);
INSERT INTO `order`.CART_SHARE_TMP_ORD_ITEM (CART_SHARE_TMP_ORD_ITEM_ID, CART_SHARE_TMP_ORD_ID,
                                             ITEM_ID, MBR_ID, ITEM_QTY, COMM_YN, ITEM_AMT, ITEM_NM,
                                             PAYMT_AMT, ITEM_BRAND_NM, ITEM_IMG_URL, SHPP_CD,
                                             REG_DTS, REGPE_ID, MOD_DTS, MODPE_ID)
VALUES (8, 2, 4, 1, 10, 1, 13410, '소고기 밀푀유나베 (2인분)', 134100, '이마트',
        'https://sitem.ssgcdn.com/97/99/84/item/2097000849997_i1_1100.jpg', 'SSG_TRADERS_SHPP',
        '2023-01-30 13:44:17', 1, '2023-01-30 13:44:17', 1);

-- ord


INSERT INTO `order`.CART_SHARE_ORD (CART_SHARE_ORD_ID, CART_SHARE_TMP_ORD_ID, CART_SHARE_ID,
                                    CART_SHARE_CAL_ID, CAL_ST_YN, CART_SHARE_ORD_RCP_DTS,
                                    ORD_STAT_CD, TTL_PAYMT_AMT, CART_SHARE_ORD_NO, REG_DTS,
                                    REGPE_ID, MOD_DTS, MODPE_ID)
VALUES (1, 1, 1, null, 0, '2023-01-30 13:41:31', 'SUCCESS', 216460, '2023-01-30-000001',
        '2023-01-30 13:41:31', 1, '2023-01-30 13:41:31', 1);
INSERT INTO `order`.CART_SHARE_ORD (CART_SHARE_ORD_ID, CART_SHARE_TMP_ORD_ID, CART_SHARE_ID,
                                    CART_SHARE_CAL_ID, CAL_ST_YN, CART_SHARE_ORD_RCP_DTS,
                                    ORD_STAT_CD, TTL_PAYMT_AMT, CART_SHARE_ORD_NO, REG_DTS,
                                    REGPE_ID, MOD_DTS, MODPE_ID)
VALUES (2, 2, 1, null, 0, '2023-01-30 13:44:20', 'SUCCESS', 216460, '2023-01-30-000002',
        '2023-01-30 13:44:20', 1, '2023-01-30 13:44:20', 1);

INSERT INTO `order`.CART_SHARE_ORD_ITEM (CART_SHARE_ORD_ITEM_ID, CART_SHARE_TMP_ORD_ITEM_ID,
                                         CART_SHARE_ORD_ID, ITEM_ID, MBR_ID, ITEM_QTY, ITEM_NM,
                                         COMM_YN, ITEM_AMT, PAYMT_AMT, ITEM_BRAND_NM, ITEM_IMG_URL,
                                         SHPP_CD, REG_DTS, REGPE_ID, MOD_DTS, MODPE_ID)
VALUES (1, 1, 1, 1, 1, 1, '표백제 1.5L*2', 0, 19500, 19500, '유한젠',
        'https://sitem.ssgcdn.com/76/82/32/item/1000518328276_i1_1100.jpg', 'SSG_TRADERS_SHPP',
        '2023-01-30 13:41:31', 1, '2023-01-30 13:41:31', 1);
INSERT INTO `order`.CART_SHARE_ORD_ITEM (CART_SHARE_ORD_ITEM_ID, CART_SHARE_TMP_ORD_ITEM_ID,
                                         CART_SHARE_ORD_ID, ITEM_ID, MBR_ID, ITEM_QTY, ITEM_NM,
                                         COMM_YN, ITEM_AMT, PAYMT_AMT, ITEM_BRAND_NM, ITEM_IMG_URL,
                                         SHPP_CD, REG_DTS, REGPE_ID, MOD_DTS, MODPE_ID)
VALUES (2, 2, 1, 2, 1, 2, '정선생 곤드레나물밥 (5입)', 0, 9980, 19960, '피코크',
        'https://sitem.ssgcdn.com/24/74/27/item/1000174277424_i1_550.jpg', 'SSG_TRADERS_SHPP',
        '2023-01-30 13:41:31', 1, '2023-01-30 13:41:31', 1);
INSERT INTO `order`.CART_SHARE_ORD_ITEM (CART_SHARE_ORD_ITEM_ID, CART_SHARE_TMP_ORD_ITEM_ID,
                                         CART_SHARE_ORD_ID, ITEM_ID, MBR_ID, ITEM_QTY, ITEM_NM,
                                         COMM_YN, ITEM_AMT, PAYMT_AMT, ITEM_BRAND_NM, ITEM_IMG_URL,
                                         SHPP_CD, REG_DTS, REGPE_ID, MOD_DTS, MODPE_ID)
VALUES (3, 3, 1, 3, 2, 5, '슈퍼크런치 치킨텐더', 0, 7980, 39900, '올반',
        'https://sitem.ssgcdn.com/64/93/56/item/1000033569364_i1_1100.jpg', 'SSG_SHPP',
        '2023-01-30 13:41:31', 1, '2023-01-30 13:41:31', 1);
INSERT INTO `order`.CART_SHARE_ORD_ITEM (CART_SHARE_ORD_ITEM_ID, CART_SHARE_TMP_ORD_ITEM_ID,
                                         CART_SHARE_ORD_ID, ITEM_ID, MBR_ID, ITEM_QTY, ITEM_NM,
                                         COMM_YN, ITEM_AMT, PAYMT_AMT, ITEM_BRAND_NM, ITEM_IMG_URL,
                                         SHPP_CD, REG_DTS, REGPE_ID, MOD_DTS, MODPE_ID)
VALUES (4, 4, 1, 4, 1, 10, '소고기 밀푀유나베 (2인분)', 1, 13410, 134100, '이마트',
        'https://sitem.ssgcdn.com/97/99/84/item/2097000849997_i1_1100.jpg', 'SSG_TRADERS_SHPP',
        '2023-01-30 13:41:31', 1, '2023-01-30 13:41:31', 1);
INSERT INTO `order`.CART_SHARE_ORD_ITEM (CART_SHARE_ORD_ITEM_ID, CART_SHARE_TMP_ORD_ITEM_ID,
                                         CART_SHARE_ORD_ID, ITEM_ID, MBR_ID, ITEM_QTY, ITEM_NM,
                                         COMM_YN, ITEM_AMT, PAYMT_AMT, ITEM_BRAND_NM, ITEM_IMG_URL,
                                         SHPP_CD, REG_DTS, REGPE_ID, MOD_DTS, MODPE_ID)
VALUES (5, 5, 2, 1, 1, 1, '표백제 1.5L*2', 0, 19500, 19500, '유한젠',
        'https://sitem.ssgcdn.com/76/82/32/item/1000518328276_i1_1100.jpg', 'SSG_TRADERS_SHPP',
        '2023-01-30 13:44:20', 1, '2023-01-30 13:44:20', 1);
INSERT INTO `order`.CART_SHARE_ORD_ITEM (CART_SHARE_ORD_ITEM_ID, CART_SHARE_TMP_ORD_ITEM_ID,
                                         CART_SHARE_ORD_ID, ITEM_ID, MBR_ID, ITEM_QTY, ITEM_NM,
                                         COMM_YN, ITEM_AMT, PAYMT_AMT, ITEM_BRAND_NM, ITEM_IMG_URL,
                                         SHPP_CD, REG_DTS, REGPE_ID, MOD_DTS, MODPE_ID)
VALUES (6, 6, 2, 2, 1, 2, '정선생 곤드레나물밥 (5입)', 0, 9980, 19960, '피코크',
        'https://sitem.ssgcdn.com/24/74/27/item/1000174277424_i1_550.jpg', 'SSG_TRADERS_SHPP',
        '2023-01-30 13:44:20', 1, '2023-01-30 13:44:20', 1);
INSERT INTO `order`.CART_SHARE_ORD_ITEM (CART_SHARE_ORD_ITEM_ID, CART_SHARE_TMP_ORD_ITEM_ID,
                                         CART_SHARE_ORD_ID, ITEM_ID, MBR_ID, ITEM_QTY, ITEM_NM,
                                         COMM_YN, ITEM_AMT, PAYMT_AMT, ITEM_BRAND_NM, ITEM_IMG_URL,
                                         SHPP_CD, REG_DTS, REGPE_ID, MOD_DTS, MODPE_ID)
VALUES (7, 7, 2, 3, 2, 5, '슈퍼크런치 치킨텐더', 0, 7980, 39900, '올반',
        'https://sitem.ssgcdn.com/64/93/56/item/1000033569364_i1_1100.jpg', 'SSG_SHPP',
        '2023-01-30 13:44:20', 1, '2023-01-30 13:44:20', 1);
INSERT INTO `order`.CART_SHARE_ORD_ITEM (CART_SHARE_ORD_ITEM_ID, CART_SHARE_TMP_ORD_ITEM_ID,
                                         CART_SHARE_ORD_ID, ITEM_ID, MBR_ID, ITEM_QTY, ITEM_NM,
                                         COMM_YN, ITEM_AMT, PAYMT_AMT, ITEM_BRAND_NM, ITEM_IMG_URL,
                                         SHPP_CD, REG_DTS, REGPE_ID, MOD_DTS, MODPE_ID)
VALUES (8, 8, 2, 4, 1, 10, '소고기 밀푀유나베 (2인분)', 1, 13410, 134100, '이마트',
        'https://sitem.ssgcdn.com/97/99/84/item/2097000849997_i1_1100.jpg', 'SSG_TRADERS_SHPP',
        '2023-01-30 13:44:20', 1, '2023-01-30 13:44:20', 1);

