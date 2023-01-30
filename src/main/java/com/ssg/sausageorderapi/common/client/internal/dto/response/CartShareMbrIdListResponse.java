package com.ssg.sausageorderapi.common.client.internal.dto.response;

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

    @Schema(description = "마스터 ID")
    private Long mastrMbrId;

    @Schema(description = "멤버 ID 리스트")
    private List<Long> mbrIdList;

    @Schema(description = "공유장바구니 이름")
    private Long cartShareNm;
}
