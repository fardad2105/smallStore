package com.store.smallstore.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.store.smallstore.model.Product;
import com.store.smallstore.model.enums.Status;

import java.sql.Timestamp;

public record voteResponseDto(@JsonProperty("id") Integer id,
                              @JsonProperty("point") long point,
                              @JsonProperty("createdAt") Timestamp createAt,
                              @JsonProperty("updatedAt") Timestamp updateAt,
                              @JsonProperty("status") Status status,
                              @JsonProperty("product") Product product) {
}
