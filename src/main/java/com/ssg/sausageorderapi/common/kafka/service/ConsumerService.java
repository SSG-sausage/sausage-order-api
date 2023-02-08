package com.ssg.sausageorderapi.common.kafka.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssg.sausageorderapi.common.kafka.constant.KafkaConstants;
import com.ssg.sausageorderapi.common.kafka.dto.CartShareCalStartDto;
import com.ssg.sausageorderapi.common.kafka.dto.CartShareOrdCartShareCalIdUpdateDto;
import com.ssg.sausageorderapi.order.entity.CartShareOrd;
import com.ssg.sausageorderapi.order.service.CartShareOrdUtilService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ConsumerService {

    private final CartShareOrdUtilService cartShareOrdUtilService;

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = KafkaConstants.KAFKA_CART_SHARE_CAL_START, groupId = KafkaConstants.CONSUMER_GROUP_ID)
    public void updateCartShareOrdCalSt(String consumeMsg) throws JsonProcessingException {

        CartShareCalStartDto cartShareCalStartDto = objectMapper.readValue(consumeMsg,
                CartShareCalStartDto.class);

        CartShareOrd cartShareOrd = cartShareOrdUtilService.findListByCartShareCalId(
                cartShareCalStartDto.getCartShareCalId());

        cartShareOrd.changeCalStYn(Boolean.TRUE);
    }

    @KafkaListener(topics = KafkaConstants.KAFKA_CART_SHARE_ORD_CART_SHARE_CAL_ID_UPDATE, groupId = KafkaConstants.CONSUMER_GROUP_ID)
    public void updateCartShareOrdCartShareCalId(String consumeMsg) throws JsonProcessingException {

        CartShareOrdCartShareCalIdUpdateDto cartShareOrdCartShareCalIdUpdateDto = objectMapper.readValue(consumeMsg,
                CartShareOrdCartShareCalIdUpdateDto.class);


        cartShareOrdUtilService.changeCartShareOrdCartShareCalId(
                cartShareOrdCartShareCalIdUpdateDto.getCartShareOrdId(), cartShareOrdCartShareCalIdUpdateDto.getCartShareCalId());
    }

}
