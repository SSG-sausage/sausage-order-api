package com.ssg.sausagememberapi.order.service;

import com.ssg.sausagememberapi.common.exception.ErrorCode;
import com.ssg.sausagememberapi.common.exception.NotFoundException;
import com.ssg.sausagememberapi.order.entity.CartShareTmpOdr;
import com.ssg.sausagememberapi.order.entity.CartShareTmpOdrItem;
import com.ssg.sausagememberapi.order.entity.TmpOrdStatCd;
import com.ssg.sausagememberapi.order.repository.CartShareTmpOrdItemRepository;
import com.ssg.sausagememberapi.order.repository.CartShareTmpOrdRepository;
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

    @Transactional
    public CartShareTmpOdr findCartShareTmpOrdInProgress(Long cartShareId) {

        // find tmpOrd be IN_PROGRESS
        return cartShareTmpOrdRepository.findFirstByCartShareIdAndTmpOrdStatCd(cartShareId, TmpOrdStatCd.IN_PROGRESS)
                .orElseThrow(() -> {
                    throw new NotFoundException(String.format("유효한 임시 주문이 존재하지 않습니다."),
                            ErrorCode.NOT_FOUND_CART_SHARE_TMP_ORDER_EXCEPTION);
                });
    }

    @Transactional
    public List<CartShareTmpOdrItem> findCartShareTmpOrdItemByTmpOrdId(Long cartShareTmpOrdId) {
        return cartShareTmpOrdItemRepository.findAllByCartShareTmpOrdId(cartShareTmpOrdId);
    }

    @Transactional
    public void changeTmpOrdStatCdToCompleted(CartShareTmpOdr cartShareTmpOdr) {
        cartShareTmpOdr.changeTmpOrdStat(TmpOrdStatCd.COMPLETED);
    }
}
