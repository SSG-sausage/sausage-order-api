package com.ssg.sausageorderapi.common.kafka.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssg.sausageorderapi.common.exception.ErrorCode;
import com.ssg.sausageorderapi.common.exception.InternalServerException;
import com.ssg.sausageorderapi.common.kafka.constant.KafkaConstants;
import com.ssg.sausageorderapi.common.kafka.dto.CartShareOrdUpdateCalStYnDto;
import com.ssg.sausageorderapi.order.entity.CartShareOdr;
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
public class CartShareConsumerService {

    private final CartShareOrdUtilService cartShareOrdUtilService;

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = KafkaConstants.KAFKA_CART_SHARE_ORD_CAL_ST_UPDATE, groupId = KafkaConstants.CONSUMER_GROUP_ID)
    public void updateCartShareOrdCalSt(String consumeMsg) {

        try {
            CartShareOrdUpdateCalStYnDto cartShareOrdUpdateCalStYnDto = objectMapper.readValue(consumeMsg,
                    CartShareOrdUpdateCalStYnDto.class);

            CartShareOdr cartShareOdr = cartShareOrdUtilService.findById(cartShareOrdUpdateCalStYnDto.getCartShareId());

            cartShareOdr.changeCalStYn(Boolean.TRUE);
        } catch (JsonProcessingException e) {
            throw new InternalServerException("예상치 못한 서버 에러가 발생하였습니다.", ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
    }
}
