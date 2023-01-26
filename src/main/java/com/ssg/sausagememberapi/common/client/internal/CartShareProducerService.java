package com.ssg.sausagememberapi.common.client.internal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssg.sausagememberapi.common.client.internal.dto.request.CartShareItemDeleteListRequest;
import com.ssg.sausagememberapi.common.client.internal.dto.request.CartShareUpdateEditPsblYnRequest;
import com.ssg.sausagememberapi.common.exception.ErrorCode;
import com.ssg.sausagememberapi.common.exception.InternalServerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartShareProducerService {

    @Value("${kafka.topic.cart-share-service.cart-share-item-list.deleting}")
    private String cartShareItemListDeleteTopic;

    @Value("${kafka.topic.cart-share-service.cart-share-edit-psbl-yn.updating}")
    private String cartShareEditPsblYnUpdateTopic;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final ObjectMapper objectMapper;

    public void deleteCartShareItemList(Long cartShareId) {
        produceKafkaMsg(cartShareItemListDeleteTopic, CartShareItemDeleteListRequest.of(cartShareId));
    }

    public void updateEditPsblYn(Long cartShareId, boolean editPsblYn) {
        produceKafkaMsg(cartShareEditPsblYnUpdateTopic, CartShareUpdateEditPsblYnRequest.of(cartShareId, editPsblYn));
    }

    public void produceKafkaMsg(String topicNm, Object object) {
        try {
            kafkaTemplate.send(topicNm, objectMapper.writeValueAsString(object));
        } catch (JsonProcessingException e) {
            throw new InternalServerException("예상치 못한 서버 에러가 발생하였습니다.", ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
    }

}
