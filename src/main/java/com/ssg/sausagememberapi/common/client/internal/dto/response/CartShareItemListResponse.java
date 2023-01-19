package com.ssg.sausagememberapi.common.client.internal.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartShareItemListResponse {

    private List<CartShareItemInfo> cartShareItemList;

    @ToString
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CartShareItemInfo {

        private Long cartShareItemId;

        private Long itemId;

        private Long mbrId;

        private String itemNm;

        private int itemAmt;

        private String itemImgUrl;

        private String shppCd;

        private int itemQty;

        private boolean comYn;
    }
}
