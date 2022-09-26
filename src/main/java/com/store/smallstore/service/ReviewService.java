package com.store.smallstore.service;

import com.store.smallstore.model.request.commentRequestDto;
import com.store.smallstore.model.request.voteRequestDto;
import com.store.smallstore.model.response.PageDto;
import com.store.smallstore.model.response.commentResponseDto;
import com.store.smallstore.model.response.reviewResponseDto;
import com.store.smallstore.model.response.voteResponseDto;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ReviewService {

    commentResponseDto createComment(Long productId, commentRequestDto commentRequestDto);
    List<commentResponseDto> getComments(Long productId);
    PageDto<commentResponseDto> getCommentsByPage(Long productId,PageRequest pageRequest);

    void deleteComment(Long commentId);
    voteResponseDto createVote(Long productId, voteRequestDto voteRequestDto);

    reviewResponseDto getReviews(Long productId);

}
