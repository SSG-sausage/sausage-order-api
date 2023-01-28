package com.ssg.sausageorderapi.order.controller;


import com.ssg.sausageorderapi.common.config.resolver.MbrId;
import com.ssg.sausageorderapi.common.dto.ErrorResponse;
import com.ssg.sausageorderapi.common.dto.SuccessResponse;
import com.ssg.sausageorderapi.common.success.SuccessCode;
import com.ssg.sausageorderapi.order.dto.response.CartShareTmpOrdFindResponse;
import com.ssg.sausageorderapi.order.service.CartShareTmpOrdService;
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
@Tag(name = "CartShareTmpOrd", description = "임시주문")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CartShareTmpOrdController {

    private final CartShareTmpOrdService cartShareTmpOrdService;

    @Operation(summary = "[external] 공유장바구니 임시주문하기", responses = {
            @ApiResponse(responseCode = "200", description = "공유장바구니 임시주문하기 성공입니다.")
    })
    @PostMapping("/cart-share/{cartShareId}/cart-share-tmp-ord")
    public ResponseEntity<SuccessResponse<String>> saveCartShareTmpOrd(
            @Parameter(in = ParameterIn.HEADER) @MbrId Long mbrId,
            @PathVariable Long cartShareId) {

        cartShareTmpOrdService.saveCartShareTmpOrd(mbrId, cartShareId);

        return SuccessResponse.OK;
    }


    @Operation(summary = "[external] 공유장바구니임시주문 단일 조회", responses = {
            @ApiResponse(responseCode = "200", description = "공유장비구니임시주문 단일조회 성공입니다."),
            @ApiResponse(responseCode = "404", description = "일치하는 공유장바구니임시주문 ID가 존재하지 않습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping(value = "/cart-share/{cartShareId}/cart-share-tmp-ord")
    public ResponseEntity<SuccessResponse<CartShareTmpOrdFindResponse>> findCartShareTmpOrd(
            @Parameter(in = ParameterIn.HEADER) @MbrId Long mbrId,
            @PathVariable Long cartShareId) {

        return SuccessResponse.success(SuccessCode.FIND_CART_SHARE_TMP_ORD_SUCCESS,
                cartShareTmpOrdService.findCartShareTmpOrdInProgress(mbrId, cartShareId));
    }

    @Operation(summary = "[external] 공유장바구니임시주문 취소", responses = {
            @ApiResponse(responseCode = "200", description = "공유장비구니임시주문이 취소됐습니다."),
            @ApiResponse(responseCode = "404", description = "일치하는 공유장바구니임시주문 ID가 존재하지 않습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping(value = "/cart-share/{cartShareId}/cart-share-tmp-ord/cancel")
    public ResponseEntity<SuccessResponse<String>> cancelCartShareTmpOrd(
            @Parameter(in = ParameterIn.HEADER) @MbrId Long mbrId,
            @PathVariable Long cartShareId) {

        cartShareTmpOrdService.cancelCartShareTmpOrd(mbrId, cartShareId);

        return SuccessResponse.OK;
    }


}
