package com.ssg.sausagememberapi.order.service;


import com.ssg.sausagememberapi.common.client.internal.CartShareClient;
import com.ssg.sausagememberapi.common.client.internal.dto.response.CartShareItemListResponse.CartShareItemInfo;
import com.ssg.sausagememberapi.order.dto.response.CartShareOrdFindListResponse;
import com.ssg.sausagememberapi.order.dto.response.CartShareOrdFindResponse;
import com.ssg.sausagememberapi.order.entity.CartShareOdr;
import com.ssg.sausagememberapi.order.entity.CartShareOdrItem;
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
@Transactional(readOnly = true)
@Slf4j
public class CartShareOrdService {

    private final CartShareOrdRepository cartShareOrdRepository;

    private final CartShareOrdItemRepository cartShareOrdItemRepository;

    private final CartShareOrdUtilService cartShareOrdUtilService;

    private final CartShareClient cartShareClient;


    @Transactional
    public void saveCartShareOrd(Long mbrId, Long cartShareId) {

        // validate 'isFound' and 'isCartShareMaster' (internal api)
        cartShareClient.validateCartShareMasterAuth(mbrId, cartShareId);

        cartShareOrdRepository.save(CartShareOdr.newInstance(cartShareId));

        // find cartShareOrderItem-list by cart share id (internal api)
        List<CartShareItemInfo> cartShareItemList = cartShareClient.getCartShareItemList(cartShareId)
                .getData().getCartShareItemList();

        List<CartShareOdrItem> cartShareOdrItems = cartShareItemList.stream()
                .map(cartShareItemInfo -> CartShareOdrItem.newInstance(cartShareItemInfo, cartShareId))
                .collect(Collectors.toList());

        // (kafka producing) delete cartShareItem ==> to be added

        cartShareOrdItemRepository.saveAll(cartShareOdrItems);
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

        return CartShareOrdFindResponse.of(cartShareOdr);
    }


}
