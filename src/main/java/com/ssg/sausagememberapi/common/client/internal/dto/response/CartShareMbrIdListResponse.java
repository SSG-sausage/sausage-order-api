package com.ssg.sausagememberapi.common.client.internal.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartShareMbrIdListResponse {

    @Schema(description = "공유장바구니마스터 ID 리스트")
    private Long cartShareMastrMbrId;

    @Schema(description = "공유장바구니멤버 ID 리스트")
    private List<Long> cartShareMbrIdList;


}
