package com.store.smallstore.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;

public record PageDto<T>(@JsonProperty("page") int page,
                         @JsonProperty("pageSize") int pageSize,

                         @JsonProperty("body") Collection<T> body,
                         @JsonProperty("count") long count,
                         @JsonProperty("totalPages") long totalPages) {
}
