package com.ssg.sausageorderapi.order.service;

import com.ssg.sausageorderapi.common.client.internal.CartShareApiClient;
import com.ssg.sausageorderapi.common.client.internal.dto.response.CartShareMbrIdListResponse;
import com.ssg.sausageorderapi.common.kafka.service.CartShareProducerService;
import com.ssg.sausageorderapi.order.entity.CartShareOrd;
import com.ssg.sausageorderapi.order.entity.CartShareOrdItem;
import com.ssg.sausageorderapi.order.entity.NotiCd;
import com.ssg.sausageorderapi.order.entity.ShppCd;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CartShareOrdCreateNotiService {

    private final CartShareProducerService cartShareProducerService;

    private final CartShareApiClient cartShareApiClient;

    public void createOrdSaveNoti(CartShareOrd cartShareOrd, Long cartShareId, Long masterId,
            List<CartShareOrdItem> cartShareOrdItems) {

        CartShareMbrIdListResponse mbrIdList = cartShareApiClient.findCartShareMbrIdList(cartShareId)
                .getData();

        for (Long mbrId : mbrIdList.getMbrIdList()) {

            // 마스터 인경우 알림 발송
            if (masterId.equals(mbrId)) {
                cartShareProducerService.createCartShareNoti(mbrId, NotiCd.CART_SHARE_ORD.name(),
                        createNotiCntt(cartShareOrd, cartShareOrdItems, true, mbrIdList.getMastrMbrNm()));
                continue;
            }

            cartShareProducerService.createCartShareNoti(mbrId, NotiCd.CART_SHARE.name(),
                    createNotiCntt(cartShareOrd, cartShareOrdItems, false, mbrIdList.getMastrMbrNm()));
        }

    }

    private String createNotiCntt(CartShareOrd cartShareOrd, List<CartShareOrdItem> cartShareOrdItems,
            boolean isMaster, String masterMbrNm) {
        DecimalFormat decFormat = new DecimalFormat("###,###");

        StringBuilder cnttBuilder = new StringBuilder();

        String cartShareNm = "소시지";

        if (isMaster) {
            cnttBuilder.append(String.format("'%s' 장바구니의 마스터가 상품 주문을 완료했습니다.\n\n", cartShareNm));
        } else {
            cnttBuilder.append(String.format("'%s' 고객님의 주문내역입니다.\n\n", masterMbrNm));
        }

        for (ShppCd shppCd : ShppCd.values()) {

            List<String> ordItemList = findOrdItemListByShppCd(cartShareOrdItems, shppCd.name());

            switch (ordItemList.size()) {
                case 0: {
                    break;
                }
                case 1: {
                    cnttBuilder.append(String.format("%s: %s\n", shppCd.getNm(), ordItemList.get(0)));
                    break;
                }
                case 2: {
                    cnttBuilder.append(String.format("%s: %s, %s\n", shppCd.getNm(), ordItemList.get(0),
                            ordItemList.get(1)));
                    break;
                }
                default: {
                    cnttBuilder.append(String.format("%s: %s, %s ...\n", shppCd.getNm(), ordItemList.get(0),
                            ordItemList.get(1)));
                    break;
                }
            }
        }

        cnttBuilder.append(String.format("\n결제 금액 : %s원\n", decFormat.format(cartShareOrd.getTtlPaymtAmt())));
        cnttBuilder.append(String.format("\n주문 번호 : %s\n", cartShareOrd.getCartShareOrdNo()));

        return cnttBuilder.toString();
    }

    private static List<String> findOrdItemListByShppCd(List<CartShareOrdItem> cartShareOrdItems, String shppCd) {
        return cartShareOrdItems.stream()
                .filter(cartShareOrdItem -> cartShareOrdItem.getShppCd().name().equals(shppCd))
                .map(CartShareOrdItem::getItemNm)
                .distinct()
                .collect(Collectors.toList());
    }


}
