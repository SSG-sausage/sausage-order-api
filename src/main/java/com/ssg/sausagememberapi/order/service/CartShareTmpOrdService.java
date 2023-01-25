package com.ssg.sausagememberapi.order.service;

import com.ssg.sausagememberapi.common.client.internal.CartShareClientMock;
import com.ssg.sausagememberapi.common.client.internal.dto.request.CartShareUpdateEditPsblYnRequest;
import com.ssg.sausagememberapi.common.client.internal.dto.response.CartShareItemListResponse.CartShareItemInfo;
import com.ssg.sausagememberapi.order.dto.response.CartShareTmpOrdFindResponse;
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

    private final CartShareClientMock cartShareClient;

    private final CartShareTmpOrdUtilService cartShareTmpOrdUtilService;


    @Transactional
    public CartShareTmpOrdFindResponse findCartShareTmpOrdInProgress(Long mbrId, Long cartShareId) {

        // validate 'isFound' and 'isCartShareMember'
        cartShareClient.validateCartShareAuth(mbrId, cartShareId);

        CartShareTmpOdr cartShareTmpOdr = cartShareTmpOrdUtilService.findCartShareTmpOrdInProgress(cartShareId);

        List<CartShareTmpOdrItem> cartShareTmpOdrItemList = cartShareTmpOrdItemRepository.findAllByCartShareTmpOrdId(
                cartShareTmpOdr.getCartShareTmpOrdId());

        return CartShareTmpOrdFindResponse.of(cartShareTmpOdr, cartShareTmpOdrItemList);
    }

    @Transactional
    public void saveCartShareTmpOrd(Long mbrId, Long cartShareId) {

        // validate 'isFound' and 'isCartShareMaster'
        cartShareClient.validateCartShareMasterAuth(mbrId, cartShareId);

        CartShareTmpOdr cartShareTmpOdr = cartShareTmpOrdRepository.save(CartShareTmpOdr.newInstance(cartShareId));

        // find cartShareOrderItem-list by cart share id
        List<CartShareItemInfo> cartShareItemList = cartShareClient.findCartShareItemList(cartShareId).getData()
                .getCartShareItemList();

        List<CartShareTmpOdrItem> cartShareTmpOdrItems = cartShareItemList.stream()
                .map(cartShareItemInfo -> CartShareTmpOdrItem.newInstance(cartShareItemInfo,
                        cartShareTmpOdr.getCartShareTmpOrdId()))
                .collect(Collectors.toList());

        // save all items
        cartShareTmpOrdItemRepository.saveAll(cartShareTmpOdrItems);

        // invoke update cartshare editPsblYn to False api
        cartShareClient.updateEditPsblYn(cartShareId, CartShareUpdateEditPsblYnRequest.of(Boolean.FALSE));
    }


}
