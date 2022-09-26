package com.store.smallstore.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.store.smallstore.model.enums.Status;

public record voteRequestDto(@JsonProperty("point") long point,
                             @JsonProperty("status") Status status
) {
}
