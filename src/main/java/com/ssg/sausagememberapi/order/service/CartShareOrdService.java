package com.ssg.sausagememberapi.order.service;


import com.ssg.sausagememberapi.common.client.internal.CartShareClient;
import com.ssg.sausagememberapi.common.client.internal.dto.response.CartShareItemListResponse.CartShareItemInfo;
import com.ssg.sausagememberapi.order.dto.response.CartShareOrdFindListResponse;
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

    private final CartShareClient cartShareClient;

    private final CartShareOrdItemRepository cartShareOrdItemRepository;

    @Transactional
    public void saveCartShareOrd(Long cartShareId) {

        cartShareOrdRepository.save(CartShareOdr.newInstance(cartShareId));

        // find cartShareOrderItem-list by cart share id (internal api)
        List<CartShareItemInfo> cartShareItemList = cartShareClient.getCartShareItemList(cartShareId)
                .getData().getCartShareItemList();

        List<CartShareOdrItem> cartShareOdrItems = cartShareItemList
                .stream()
                .map(cartShareItemInfo -> CartShareOdrItem.newInstance(cartShareItemInfo, cartShareId))
                .collect(Collectors.toList());

        // (kafka producing) delete cartShareItem

        cartShareOrdItemRepository.saveAll(cartShareOdrItems);
    }

    public CartShareOrdFindListResponse findCartShareOrderList(Long cartShareId) {

        List<CartShareOdr> cartShareOdrList = cartShareOrdRepository.findAllByCartShareId(cartShareId);

        return CartShareOrdFindListResponse.of(cartShareOdrList);
    }


}
