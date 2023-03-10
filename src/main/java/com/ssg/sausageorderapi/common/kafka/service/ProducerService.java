package com.ssg.sausageorderapi.common.kafka.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssg.sausageorderapi.common.client.internal.dto.request.CartShareCalSaveRequest;
import com.ssg.sausageorderapi.common.exception.ErrorCode;
import com.ssg.sausageorderapi.common.exception.InternalServerException;
import com.ssg.sausageorderapi.common.kafka.constant.KafkaConstants;
import com.ssg.sausageorderapi.common.kafka.dto.CartShareCalRetryDto;
import com.ssg.sausageorderapi.common.kafka.dto.CartShareItemDeleteListDto;
import com.ssg.sausageorderapi.common.kafka.dto.CartShareNotiCreateDto;
import com.ssg.sausageorderapi.common.kafka.dto.CartShareUpdateEditPsblYnDto;
import com.ssg.sausageorderapi.common.kafka.dto.ItemInvQtyUpdateListDto;
import com.ssg.sausageorderapi.common.kafka.dto.ItemInvQtyUpdateListDto.ItemInvQtyUpdateListDtoType;
import com.ssg.sausageorderapi.order.dto.response.ItemInvQtyUpdateInfo;
import java.util.List;
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

    public void retryCartShareCal(CartShareCalSaveRequest request) {
        produceKafkaMsg(KafkaConstants.KAFKA_CART_SHARE_CAL_SAVE_RETRY, CartShareCalRetryDto.of(request));
    }

    public void updateItemInvQty(List<ItemInvQtyUpdateInfo> itemInvQtyUpdateInfoList) {

        produceKafkaMsg(KafkaConstants.KAFKA_ITEM_INV_QTY_UPDATE,
                ItemInvQtyUpdateListDto.of(itemInvQtyUpdateInfoList, ItemInvQtyUpdateListDtoType.INCREASE));
    }

    private void produceKafkaMsg(String topicNm, Object object) {
        try {
            kafkaTemplate.send(topicNm, objectMapper.writeValueAsString(object));
        } catch (JsonProcessingException e) {
            throw new InternalServerException("????????? ?????? ?????? ????????? ?????????????????????.", ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
    }
}
