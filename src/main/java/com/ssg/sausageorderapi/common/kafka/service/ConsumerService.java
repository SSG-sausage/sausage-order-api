package com.ssg.sausageorderapi.common.kafka.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssg.sausageorderapi.common.kafka.constant.KafkaConstants;
import com.ssg.sausageorderapi.common.kafka.dto.CartShareCalStartDto;
import com.ssg.sausageorderapi.common.kafka.dto.CartShareOrdItemDeleteListDto;
import com.ssg.sausageorderapi.common.kafka.dto.CartShareOrdItemInfo;
import com.ssg.sausageorderapi.order.entity.CartShareOrd;
import com.ssg.sausageorderapi.order.entity.OrdStatCd;
import com.ssg.sausageorderapi.order.service.CartShareOrdUtilService;
import java.util.List;
import java.util.stream.Collectors;
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

    @KafkaListener(topics = KafkaConstants.KAFKA_CART_SHARE_ORD_ITEM_DELETE, groupId = KafkaConstants.CONSUMER_GROUP_ID)
    public void deleteCartShareOrdItemList(String consumeMsg) throws JsonProcessingException {

        CartShareOrdItemDeleteListDto cartShareOrdItemDeleteListDto = objectMapper.readValue(consumeMsg,
                CartShareOrdItemDeleteListDto.class);

        List<Long> cartShareOrdItemIdList = cartShareOrdItemDeleteListDto.getCartShareOrdItemInfoList().stream()
                .map(CartShareOrdItemInfo::getCartShareOrdItemId)
                .collect(Collectors.toList());

        cartShareOrdUtilService.deleteCartShareOrdItemList(cartShareOrdItemIdList);
        cartShareOrdUtilService.changeCartShareOrdStatCd(cartShareOrdItemDeleteListDto.getCartShareOrdId(),
                OrdStatCd.PARTIAL_SUCCESS);
    }
}
