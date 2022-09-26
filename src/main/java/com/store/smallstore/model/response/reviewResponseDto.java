package com.store.smallstore.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.store.smallstore.model.Comment;

import java.util.List;

public record reviewResponseDto(@JsonProperty("comments") List<Comment> comments,
                                @JsonProperty("averageOfAllVotes") double votes,
                                @JsonProperty("numberOfComments") Long numberOfComments) {
}
