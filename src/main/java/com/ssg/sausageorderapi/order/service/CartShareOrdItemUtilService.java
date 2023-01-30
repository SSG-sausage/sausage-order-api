package com.ssg.sausageorderapi.order.service;

import com.ssg.sausageorderapi.order.entity.CartShareOdrItem;
import com.ssg.sausageorderapi.order.repository.CartShareOrdItemRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CartShareOrdItemUtilService {

    private final CartShareOrdItemRepository cartShareOrdItemRepository;

    public List<CartShareOdrItem> findListByCartShareOrdId(Long cartShareOrdId) {
        return cartShareOrdItemRepository.findAllByCartShareOrdId(cartShareOrdId);
    }
}
