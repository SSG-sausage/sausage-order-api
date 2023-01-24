package com.ssg.sausagememberapi.order.service;

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

        // 임시 주문 상태가 IN_PROGRESS인 임시 주문 find
        return cartShareTmpOrdRepository.findFirstByCartShareIdAndTmpOrdStatCd(cartShareId,
                TmpOrdStatCd.IN_PROGRESS);
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
