package com.ssg.sausagememberapi.order.service;


import com.ssg.sausagememberapi.common.client.internal.CartShareClientMock;
import com.ssg.sausagememberapi.order.dto.response.CartShareOrdFindListResponse;
import com.ssg.sausagememberapi.order.dto.response.CartShareOrdFindResponse;
import com.ssg.sausagememberapi.order.entity.CartShareOdr;
import com.ssg.sausagememberapi.order.entity.CartShareOdrItem;
import com.ssg.sausagememberapi.order.entity.CartShareTmpOdr;
import com.ssg.sausagememberapi.order.entity.CartShareTmpOdrItem;
import com.ssg.sausagememberapi.order.repository.CartShareOrdItemRepository;
import com.ssg.sausagememberapi.order.repository.CartShareOrdRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CartShareOrdService {

    private final CartShareOrdRepository cartShareOrdRepository;

    private final CartShareOrdItemRepository cartShareOrdItemRepository;

    private final CartShareOrdUtilService cartShareOrdUtilService;

    private final CartShareTmpOrdUtilService cartShareTmpOrdUtilService;

    private final CartShareClientMock cartShareClient;

    public void saveCartShareOrdFromTmpOrd(Long mbrId, Long cartShareId) {

        CartShareTmpOdr cartShareTmpOdr = cartShareTmpOrdUtilService.findCartShareTmpOrdInProgress(cartShareId);

        // Ord created from TmpOrd
        CartShareOdr cartShareOdr = CartShareOdr.newInstance(cartShareTmpOdr);
        cartShareOrdRepository.save(cartShareOdr);

        // find CartShareTmpOdrItem from TmpOrdId
        List<CartShareTmpOdrItem> cartShareTmpOdrItemList = cartShareTmpOrdUtilService.findCartShareTmpOrdItemByTmpOrdId(
                cartShareOdr.getCartShareTmpOrdId());

        // covert CartShareTmpOdrItem to CartShareOdrItem
        List<CartShareOdrItem> cartShareOdrItems = cartShareTmpOdrItemList.stream()
                .map(cartShareTmpOdrItem -> CartShareOdrItem.newInstance(cartShareOdr.getCartShareOrdId(),
                        cartShareTmpOdrItem))
                .collect(Collectors.toList());

        cartShareOrdItemRepository.saveAll(cartShareOdrItems);

        // change tmpOrdStatCd to Completed
        cartShareTmpOrdUtilService.changeTmpOrdStatCdToCompleted(cartShareTmpOdr);
    }


    public CartShareOrdFindListResponse findCartShareOrderList(Long mbrId, Long cartShareId) {

        // validate 'isFound' and 'isAccessibleCartShareByMbr' (internal api)
        cartShareClient.validateCartShareAuth(mbrId, cartShareId);

        List<CartShareOdr> cartShareOdrList = cartShareOrdRepository.findAllByCartShareId(cartShareId);

        return CartShareOrdFindListResponse.of(cartShareOdrList);
    }

    public CartShareOrdFindResponse findCartShareOrder(Long mbrId, Long cartShareId, Long cartShareOrdId) {

        // validate 'isFound' and 'isAccessibleCartShareByMbr' (internal api)
        cartShareClient.validateCartShareAuth(mbrId, cartShareId);

        CartShareOdr cartShareOdr = cartShareOrdUtilService.findById(cartShareOrdId);

        List<CartShareOdrItem> cartShareOdrItemList = cartShareOrdItemRepository.findAllByCartShareOrdId(
                cartShareOdr.getCartShareId());

        return CartShareOrdFindResponse.of(cartShareOdr, cartShareOdrItemList);
    }
}
