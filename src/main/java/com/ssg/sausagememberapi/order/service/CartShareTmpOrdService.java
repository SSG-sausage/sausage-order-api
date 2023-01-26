package com.ssg.sausagememberapi.order.service;

import com.ssg.sausagememberapi.common.client.internal.CartShareApiClient;
import com.ssg.sausagememberapi.common.client.internal.CartShareProducerService;
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

    private final CartShareApiClient cartShareClient;

    private final CartShareTmpOrdUtilService cartShareTmpOrdUtilService;

    private final CartShareProducerService cartShareProducerService;


    @Transactional
    public CartShareTmpOrdFindResponse findCartShareTmpOrdInProgress(Long mbrId, Long cartShareId) {

        // validate 'isFound' and 'isCartShareMember'
        cartShareClient.validateCartShareMbr(mbrId, cartShareId);

        CartShareTmpOdr cartShareTmpOdr = cartShareTmpOrdUtilService.findCartShareTmpOrdInProgress(cartShareId);

        List<CartShareTmpOdrItem> cartShareTmpOdrItemList = cartShareTmpOrdItemRepository.findAllByCartShareTmpOrdId(
                cartShareTmpOdr.getCartShareTmpOrdId());

        return CartShareTmpOrdFindResponse.of(cartShareTmpOdr, cartShareTmpOdrItemList);
    }

    @Transactional
    public void saveCartShareTmpOrd(Long mbrId, Long cartShareId) {

        // validate 'isFound' and 'isCartShareMaster'
        cartShareClient.validateCartShareMastr(mbrId, cartShareId);

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

        // produce update cartshare editPsblYn to False api
        cartShareProducerService.updateEditPsblYn(cartShareId, false);
    }


}
