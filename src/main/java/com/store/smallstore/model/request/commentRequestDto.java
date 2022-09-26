package com.store.smallstore.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.store.smallstore.model.Product;
import com.store.smallstore.model.enums.Status;

public record commentRequestDto(@JsonProperty("comment") String comment,
                                @JsonProperty("status") Status status
) {
}
