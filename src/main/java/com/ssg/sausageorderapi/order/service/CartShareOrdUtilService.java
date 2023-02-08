package com.ssg.sausageorderapi.order.service;

import com.ssg.sausageorderapi.common.exception.ErrorCode;
import com.ssg.sausageorderapi.common.exception.NotFoundException;
import com.ssg.sausageorderapi.order.entity.CartShareOrd;
import com.ssg.sausageorderapi.order.entity.OrdStatCd;
import com.ssg.sausageorderapi.order.repository.CartShareOrdItemRepository;
import com.ssg.sausageorderapi.order.repository.CartShareOrdRepository;
import java.util.List;
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

    private final CartShareOrdItemRepository cartShareOrdItemRepository;

    public CartShareOrd findById(Long cartShareOrdId) {
        return cartShareOrdRepository.findById(cartShareOrdId)
                .orElseThrow(() -> {
                    throw new NotFoundException(String.format("존재하지 않는 주문 ID (%s) 입니다", cartShareOrdId),
                            ErrorCode.NOT_FOUND_CART_SHARE_ORD_EXCEPTION);
                });
    }

    public CartShareOrd findListByCartShareCalId(Long cartShareCalId) {
        return cartShareOrdRepository.findByCartShareCalId(cartShareCalId)
                .orElseThrow(() -> {
                    throw new NotFoundException(String.format("존재하지 않는 주문 정산 ID (%s) 입니다", cartShareCalId),
                            ErrorCode.NOT_FOUND_CART_SHARE_ORD_CAL_EXCEPTION);
                });
    }

    public List<CartShareOrd> findListByCartShareId(Long cartShareId) {
        return cartShareOrdRepository.findAllByCartShareId(cartShareId);
    }

    public void changeCartShareOrdStatCd(Long cartShareOrdId, OrdStatCd ordStatCd) {
        CartShareOrd cartShareOrd = findById(cartShareOrdId);
        cartShareOrd.changeOrdStatCd(ordStatCd);
    }

    public void changeCartShareOrdCartShareCalId(Long cartShareOrdId, Long cartShareCalId){
        CartShareOrd cartShareOrd = findById(cartShareOrdId);
        cartShareOrd.changeCartShareCalId(cartShareCalId);
    }

    public void deleteCartShareOrdItemList(List<Long> cartShareOrdItemList) {
        cartShareOrdItemRepository.deleteAllById(cartShareOrdItemList);
    }
}
