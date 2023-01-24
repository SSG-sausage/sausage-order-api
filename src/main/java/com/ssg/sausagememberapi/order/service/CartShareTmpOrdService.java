package com.ssg.sausagememberapi.order.service;

import com.ssg.sausagememberapi.common.client.internal.CartShareClient;
import com.ssg.sausagememberapi.common.client.internal.dto.response.CartShareItemListResponse.CartShareItemInfo;
import com.ssg.sausagememberapi.order.entity.CartShareTmpOdr;
import com.ssg.sausagememberapi.order.entity.CartShareTmpOdrItem;
import com.ssg.sausagememberapi.order.repository.CartShareTmpOrdItemRepository;
import com.ssg.sausagememberapi.order.repository.CartShareTmpOrdRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartShareTmpOrdService {


    private final CartShareTmpOrdRepository cartShareTmpOrdRepository;

    private final CartShareTmpOrdItemRepository cartShareTmpOrdItemRepository;

    private final CartShareClient cartShareClient;

    private final CartShareTmpOrdUtilService cartShareTmpOrdUtilService;


    @Transactional
    public void findCartShareTmpOrdInProgress(Long mbrId, Long cartShareId) {

        // validate 'isFound' and 'isCartShareMaster' (internal api)
        cartShareClient.validateCartShareMasterAuth(mbrId, cartShareId);

        CartShareTmpOdr cartShareTmpOrdInProgress = cartShareTmpOrdUtilService.findCartShareTmpOrdInProgress(
                cartShareId);


    }

    @Transactional
    public void saveCartShareTmpOrd(Long mbrId, Long cartShareId) {

        // validate 'isFound' and 'isCartShareMaster' (internal api)
        cartShareClient.validateCartShareMasterAuth(mbrId, cartShareId);

        CartShareTmpOdr cartShareTmpOdr = cartShareTmpOrdRepository.save(CartShareTmpOdr.newInstance(cartShareId));

        // find cartShareOrderItem-list by cart share id (internal api)
        List<CartShareItemInfo> cartShareItemList = cartShareClient.getCartShareItemList(cartShareId)
                .getData().getCartShareItemList();

        List<CartShareTmpOdrItem> cartShareTmpOdrItems = cartShareItemList.stream()
                .map(cartShareItemInfo -> CartShareTmpOdrItem.newInstance(cartShareItemInfo,
                        cartShareTmpOdr.getCartShareTmpOrdId()))
                .collect(Collectors.toList());

        // (kafka producing) delete cartShareItem ==> to be added

        // save all items
        cartShareTmpOrdItemRepository.saveAll(cartShareTmpOdrItems);
    }


}
