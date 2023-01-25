package com.ssg.sausagememberapi.order.controller;

import com.ssg.sausagememberapi.common.config.resolver.MbrId;
import com.ssg.sausagememberapi.common.dto.ErrorResponse;
import com.ssg.sausagememberapi.common.dto.SuccessResponse;
import com.ssg.sausagememberapi.common.success.SuccessCode;
import com.ssg.sausagememberapi.order.dto.response.CartShareOrdFindDetailForCartShareCal;
import com.ssg.sausagememberapi.order.dto.response.CartShareOrdFindForCartShareCal;
import com.ssg.sausagememberapi.order.dto.response.CartShareOrdFindListResponse;
import com.ssg.sausagememberapi.order.dto.response.CartShareOrdFindResponse;
import com.ssg.sausagememberapi.order.service.CartShareOrdForCartShareCalService;
import com.ssg.sausagememberapi.order.service.CartShareOrdService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Tag(name = "CartShareOrd", description = "주문")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CartShareOrdController {

    private final CartShareOrdService cartShareOrdService;

    private final CartShareOrdForCartShareCalService cartShareOrdForCartShareCalService;

    @Operation(summary = "공유장바구니 주문하기", responses = {
            @ApiResponse(responseCode = "200", description = "공유장바구니 주문하기 성공"),
            @ApiResponse(responseCode = "404", description = "유효한 임시 주문이 존재하지 않습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/cart-share/{cartShareId}/cart-share-ord")
    public ResponseEntity<SuccessResponse<String>> saveCartShareOrd(
            @Parameter(in = ParameterIn.HEADER) @MbrId Long mbrId,
            @PathVariable Long cartShareId) {

        cartShareOrdService.saveCartShareOrdFromTmpOrd(mbrId, cartShareId);

        return SuccessResponse.OK;
    }

    @Operation(summary = "공유장바구니주문 리스트 조회", responses = {
            @ApiResponse(responseCode = "200", description = "공유장비구니주문 리스트 조회 성공")})
    @GetMapping(value = "/cart-share/{cartShareId}/cart-share-ord")
    public ResponseEntity<SuccessResponse<CartShareOrdFindListResponse>> findCartShareOrdList(
            @Parameter(in = ParameterIn.HEADER) @MbrId Long mbrId,
            @PathVariable Long cartShareId) {

        return SuccessResponse.success(SuccessCode.FIND_CART_SHARE_ORD_SUCCESS,
                cartShareOrdService.findCartShareOrderList(mbrId, cartShareId));
    }

    @Operation(summary = "공유장바구니주문 단일 조회", responses = {
            @ApiResponse(responseCode = "200", description = "공유장바구니주문 단일 조회 성공"),
            @ApiResponse(responseCode = "404", description = "일치하는 공유장바구니주문 ID가 존재하지 않습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping(value = "/cart-share/{cartShareId}/cart-share-ord/{cartShareOrdId}")
    public ResponseEntity<SuccessResponse<CartShareOrdFindResponse>> findCartShareOrd(
            @Parameter(in = ParameterIn.HEADER) @MbrId Long mbrId,
            @PathVariable Long cartShareId,
            @PathVariable Long cartShareOrdId) {

        return SuccessResponse.success(SuccessCode.FIND_CART_SHARE_ORD_SUCCESS,
                cartShareOrdService.findCartShareOrder(mbrId, cartShareId, cartShareOrdId));
    }

    @Operation(summary = "공유장바구니주문 정산 조회", responses = {
            @ApiResponse(responseCode = "200", description = "공유장바구니주문 정산 조회 성공"),
    })
    @GetMapping(value = "/cart-share-ord/{cartShareOrdId}/cart-share-cal")
    public ResponseEntity<SuccessResponse<CartShareOrdFindForCartShareCal>> findCartShareOrdFindForCartShareCal(
            @PathVariable Long cartShareOrdId) {

        return SuccessResponse.success(SuccessCode.OK_SUCCESS,
                cartShareOrdForCartShareCalService.findCartShareOrd(cartShareOrdId));
    }

    @Operation(summary = "공유장바구니주문 정산 세부 조회", responses = {
            @ApiResponse(responseCode = "200", description = "공유장바구니주문 정산 세부 조회 성공"),
            @ApiResponse(responseCode = "404", description = "일치하는 공유장바구니주문 ID가 존재하지 않습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping(value = "/cart-share-ord/{cartShareOrdId}/cart-share-cal/detail")
    public ResponseEntity<SuccessResponse<CartShareOrdFindDetailForCartShareCal>> findCartShareOrdFindDetailForCartShareCal(
            @PathVariable Long cartShareOrdId) {

        return SuccessResponse.success(SuccessCode.OK_SUCCESS,
                cartShareOrdForCartShareCalService.findCartShareOrdDetail(cartShareOrdId));
    }


}
