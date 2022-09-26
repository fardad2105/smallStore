package com.store.smallstore.controller;

import com.store.smallstore.model.request.commentRequestDto;
import com.store.smallstore.model.request.voteRequestDto;
import com.store.smallstore.model.response.PageDto;
import com.store.smallstore.model.response.commentResponseDto;
import com.store.smallstore.model.response.reviewResponseDto;
import com.store.smallstore.model.response.voteResponseDto;
import com.store.smallstore.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class reviewController {

    private final ReviewService reviewService;


    /**
     * @param productId
     * @return desc: client can create comment if she/he has permission
     */
    @PostMapping("/comment/{productId}")
    public ResponseEntity<commentResponseDto> createComment(@PathVariable long productId,
                                                            @RequestBody commentRequestDto commentDto) {

        return new ResponseEntity<commentResponseDto>(reviewService.createComment(productId, commentDto), HttpStatus.CREATED);
    }

    /**
     * @param productId
     * @param page
     * @param perPage
     * @return all comments that have Approved status
     */
    @GetMapping("/comment/{productId}")
    public PageDto<commentResponseDto> getComments(@PathVariable long productId,
                                                   @RequestParam(value = "page", defaultValue = "0") final int page,
                                                   @RequestParam(value = "perPage", defaultValue = "10") final int perPage) {
        return reviewService.getCommentsByPage(productId, PageRequest.of(page, perPage));
    }

    /**
     * @param productId
     * @return desc: client can create vote if she/he has permission
     */
    @PostMapping("/vote/{productId}")
    public ResponseEntity<voteResponseDto> createVote(@PathVariable long productId,
                                                      @RequestBody voteRequestDto voteDto) {
        return new ResponseEntity<>(reviewService.createVote(productId, voteDto), HttpStatus.CREATED);
    }

    /**
     * @param productId
     * @return latest 3 comment and average all votes and count of all comments
     */
    @GetMapping("/review/{productId}")
    public ResponseEntity<reviewResponseDto> getReviews(@PathVariable long productId) {
        return new ResponseEntity<>(reviewService.getReviews(productId), HttpStatus.OK);
    }


}
