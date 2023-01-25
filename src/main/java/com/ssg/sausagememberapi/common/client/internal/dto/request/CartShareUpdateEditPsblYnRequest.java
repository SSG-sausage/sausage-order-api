package com.ssg.sausagememberapi.common.client.internal.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PUBLIC)
public class CartShareUpdateEditPsblYnRequest {

    @Schema(description = "수정가능여부")
    private Boolean editPsblYn;

    public static CartShareUpdateEditPsblYnRequest of(boolean editPsblYn) {
        return CartShareUpdateEditPsblYnRequest.builder()
                .editPsblYn(editPsblYn)
                .build();
    }

}