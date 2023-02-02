package com.ssg.sausageorderapi.common.kafka.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssg.sausageorderapi.common.exception.ErrorCode;
import com.ssg.sausageorderapi.common.exception.InternalServerException;
import com.ssg.sausageorderapi.common.kafka.constant.KafkaConstants;
import com.ssg.sausageorderapi.common.kafka.dto.CartShareItemDeleteListDto;
import com.ssg.sausageorderapi.common.kafka.dto.CartShareNotiCreateDto;
import com.ssg.sausageorderapi.common.kafka.dto.CartShareOrdItemInfo;
import com.ssg.sausageorderapi.common.kafka.dto.CartShareUpdateEditPsblYnDto;
import com.ssg.sausageorderapi.common.kafka.dto.ItemInvQtyUpdateListDto;
import com.ssg.sausageorderapi.order.entity.CartShareOrdItem;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final ObjectMapper objectMapper;

    public void deleteCartShareItemList(Long cartShareId) {
        produceKafkaMsg(KafkaConstants.KAFKA_CART_SHARE_ITEM_DELETE, CartShareItemDeleteListDto.of(cartShareId));
    }

    public void updateEditPsblYn(Long cartShareId, boolean editPsblYn) {
        produceKafkaMsg(KafkaConstants.KAFKA_CART_SHARE_EDIT_UPDATE_UPDATE,
                CartShareUpdateEditPsblYnDto.of(cartShareId, editPsblYn));
    }

    public void createCartShareNoti(Long mbrId, String notiCd, String cartShareNotiCntt) {
        produceKafkaMsg(KafkaConstants.KAFKA_CART_SHARE_NOTI_CREATE,
                CartShareNotiCreateDto.of(mbrId, notiCd, cartShareNotiCntt));
    }

    public void updateItemInvQty(Long cartShareOrdId, List<CartShareOrdItem> cartShareOrdItemList) {

        List<CartShareOrdItemInfo> cartShareOrdItemInfoList = cartShareOrdItemList.stream()
                .map(cartShareOrdItem -> CartShareOrdItemInfo.of(cartShareOrdItem.getItemId(),
                        cartShareOrdItem.getCartShareOrdItemId(), cartShareOrdItem.getItemQty())
                ).collect(Collectors.toList());

        produceKafkaMsg(KafkaConstants.KAFKA_ITEM_INV_QTY_UPDATE,
                ItemInvQtyUpdateListDto.of(cartShareOrdId, cartShareOrdItemInfoList));
    }

    private void produceKafkaMsg(String topicNm, Object object) {
        try {
            kafkaTemplate.send(topicNm, objectMapper.writeValueAsString(object));
        } catch (JsonProcessingException e) {
            throw new InternalServerException("예상치 못한 서버 에러가 발생하였습니다.", ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
    }
}
