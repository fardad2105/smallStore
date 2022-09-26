package com.store.smallstore.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record productRequestDto(@JsonProperty("name") String name,
                                @JsonProperty("price") String price,
                                @JsonProperty("description") String description,
                                @JsonProperty("image") String image,
                                @JsonProperty("condition") String condition,

                                @JsonProperty("isShow") boolean isShow,
                                @JsonProperty("isComment") boolean isComment,
                                @JsonProperty("isVote") boolean isVote,
                                @JsonProperty("isCommon") boolean isCommon) {
}
