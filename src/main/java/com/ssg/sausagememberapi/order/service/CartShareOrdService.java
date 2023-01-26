package com.ssg.sausagememberapi.order.service;


import com.ssg.sausagememberapi.common.client.internal.CartShareApiClientMock;
import com.ssg.sausagememberapi.common.client.internal.CartShareCalApiClientMock;
import com.ssg.sausagememberapi.common.client.internal.CartShareProducerService;
import com.ssg.sausagememberapi.common.client.internal.dto.request.CartShareCalSaveRequest;
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

    private final CartShareApiClientMock cartShareClient;

    private final CartShareCalApiClientMock cartShareCalApiClientMock;

    private final CartShareProducerService cartShareProducerService;


    public CartShareCalSaveRequest saveCartShareOrdFromTmpOrd(Long mbrId, Long cartShareId) {

        // validate 'isFound' and 'isCartShareMaster' (internal api)
        cartShareClient.validateCartShareMastr(mbrId, cartShareId);

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

        // produce delete cartshareItemList
        cartShareProducerService.deleteCartShareItemList(cartShareId);

        // produce update cartshare editPsblYn to True api
        cartShareProducerService.updateEditPsblYn(cartShareId, true);

        // *to be added, produce 주문 완료 알림 생성 이벤트

        // invoke save cartShareCal api
        Long cartShareCalId = cartShareCalApiClientMock.saveCartShareCal(
                CartShareCalSaveRequest.of(cartShareOdr.getCartShareOrdId())).getData().getCartShareCalId();

        cartShareOdr.changeCartShareCalId(cartShareCalId);

        return CartShareCalSaveRequest.of(cartShareCalId);
    }


    public CartShareOrdFindListResponse findCartShareOrderList(Long mbrId, Long cartShareId) {

        // validate 'isFound' and 'isAccessibleCartShareByMbr'
        cartShareClient.validateCartShareMbr(mbrId, cartShareId);

        List<CartShareOdr> cartShareOdrList = cartShareOrdRepository.findAllByCartShareId(cartShareId);

        // * to be added, get DUTCH_PAY_ST_YN

        return CartShareOrdFindListResponse.of(cartShareOdrList);
    }

    public CartShareOrdFindResponse findCartShareOrder(Long mbrId, Long cartShareId, Long cartShareOrdId) {

        // validate 'isFound' and 'isAccessibleCartShareByMbr'
        cartShareClient.validateCartShareMbr(mbrId, cartShareId);

        CartShareOdr cartShareOdr = cartShareOrdUtilService.findById(cartShareOrdId);

        List<CartShareOdrItem> cartShareOdrItemList = cartShareOrdItemRepository.findAllByCartShareOrdId(
                cartShareOdr.getCartShareId());

        return CartShareOrdFindResponse.of(cartShareOdr, cartShareOdrItemList);
    }

    public void changeCalStYnToTrue(Long cartShareOrdId) {

        CartShareOdr cartShareOdr = cartShareOrdUtilService.findById(cartShareOrdId);

        cartShareOdr.changeCalStYn(true);
    }
}