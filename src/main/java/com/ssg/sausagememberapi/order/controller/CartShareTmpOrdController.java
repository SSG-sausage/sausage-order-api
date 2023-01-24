package com.ssg.sausagememberapi.order.controller;


import com.ssg.sausagememberapi.common.config.resolver.MbrId;
import com.ssg.sausagememberapi.common.dto.SuccessResponse;
import com.ssg.sausagememberapi.common.success.SuccessCode;
import com.ssg.sausagememberapi.order.dto.response.CartShareTmpOrdFindResponse;
import com.ssg.sausagememberapi.order.service.CartShareTmpOrdService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "CartShareTmpOrd", description = "임시주문")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CartShareTmpOrdController {

    private final CartShareTmpOrdService cartShareTmpOrdService;

    @Operation(summary = "공유장바구니 임시주문하기", responses = {
            @ApiResponse(responseCode = "200", description = "공유장바구니 임시주문하기 성공")
    })
    @PostMapping("/cart-share/{cartShareId}/tmp-cart-share-ord")
    public ResponseEntity<SuccessResponse<String>> saveCartShareTmpOrd(
            @Parameter(in = ParameterIn.HEADER) @MbrId Long mbrId,
            @PathVariable Long cartShareId) {

        cartShareTmpOrdService.saveCartShareTmpOrd(mbrId, cartShareId);

        return SuccessResponse.OK;
    }


    @Operation(summary = "공유장바구니임시주문 단일 조회", responses = {
            @ApiResponse(responseCode = "200", description = "공유장비구니임시주문 단일 조회 성공")})
    @GetMapping(value = "/cart-share/{cartShareId}/tmp-cart-share-ord")
    public ResponseEntity<SuccessResponse<CartShareTmpOrdFindResponse>> findCartShareTmpOrd(
            @Parameter(in = ParameterIn.HEADER) @MbrId Long mbrId,
            @PathVariable Long cartShareId) {

        return SuccessResponse.success(SuccessCode.FIND_CART_SHARE_ORD_SUCCESS,
                cartShareTmpOrdService.findCartShareTmpOrdInProgress(mbrId, cartShareId));
    }


}
