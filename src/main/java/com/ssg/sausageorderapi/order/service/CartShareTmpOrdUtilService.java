package com.ssg.sausageorderapi.order.service;

import com.ssg.sausageorderapi.common.exception.ErrorCode;
import com.ssg.sausageorderapi.common.exception.NotFoundException;
import com.ssg.sausageorderapi.order.entity.CartShareTmpOdr;
import com.ssg.sausageorderapi.order.entity.CartShareTmpOdrItem;
import com.ssg.sausageorderapi.order.entity.TmpOrdStatCd;
import com.ssg.sausageorderapi.order.repository.CartShareTmpOrdItemRepository;
import com.ssg.sausageorderapi.order.repository.CartShareTmpOrdRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CartShareTmpOrdUtilService {

    private final CartShareTmpOrdRepository cartShareTmpOrdRepository;

    private final CartShareTmpOrdItemRepository cartShareTmpOrdItemRepository;

    public CartShareTmpOdr findCartShareTmpOrdInProgress(Long cartShareId) {

        // 임시 주문 상태가 IN_PROGRESS인 주문만 find
        return cartShareTmpOrdRepository.findFirstByCartShareIdAndTmpOrdStatCdOrderByCartShareTmpOrdRcpDts(cartShareId,
                        TmpOrdStatCd.IN_PROGRESS)
                .orElseThrow(() -> {
                    throw new NotFoundException(String.format("유효한 임시 주문이 존재하지 않습니다."),
                            ErrorCode.NOT_FOUND_CART_SHARE_TMP_ORD_EXCEPTION);
                });
    }

    public List<CartShareTmpOdrItem> findCartShareTmpOrdItemByTmpOrdId(Long cartShareTmpOrdId) {
        return cartShareTmpOrdItemRepository.findAllByCartShareTmpOrdId(cartShareTmpOrdId);
    }

    @Transactional
    public void changeTmpOrdStatCd(CartShareTmpOdr cartShareTmpOdr, TmpOrdStatCd tmpOrdStatCd) {
        cartShareTmpOdr.changeTmpOrdStat(tmpOrdStatCd);
    }
}
