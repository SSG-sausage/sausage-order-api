package com.ssg.sausageorderapi.order.service;

import com.ssg.sausageorderapi.common.exception.ErrorCode;
import com.ssg.sausageorderapi.common.exception.NotFoundException;
import com.ssg.sausageorderapi.order.entity.CartShareOdr;
import com.ssg.sausageorderapi.order.repository.CartShareOrdRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CartShareOrdUtilService {

    private final CartShareOrdRepository cartShareOrdRepository;

    public CartShareOdr findById(Long cartShareOrdId) {
        return cartShareOrdRepository.findById(cartShareOrdId)
                .orElseThrow(() -> {
                    throw new NotFoundException(String.format("존재하지 않는 주문 ID (%s) 입니다", cartShareOrdId),
                            ErrorCode.NOT_FOUND_CART_SHARE_ORD_EXCEPTION);
                });
    }

}
