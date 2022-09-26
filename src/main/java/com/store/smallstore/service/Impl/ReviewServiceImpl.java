package com.store.smallstore.service.Impl;

import com.store.smallstore.exception.BaseNotFoundException;
import com.store.smallstore.exception.NotAccessException;
import com.store.smallstore.model.Comment;
import com.store.smallstore.model.Product;
import com.store.smallstore.model.Vote;
import com.store.smallstore.model.enums.Status;
import com.store.smallstore.model.request.commentRequestDto;
import com.store.smallstore.model.request.voteRequestDto;
import com.store.smallstore.model.response.*;
import com.store.smallstore.repository.CommentRepository;
import com.store.smallstore.repository.ProductRepository;
import com.store.smallstore.repository.VoteRepository;
import com.store.smallstore.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final CommentRepository commentRepository;
    private final VoteRepository voteRepository;
    private final ProductRepository productRepository;

    @Override
    public commentResponseDto createComment(Long productId, commentRequestDto commentRequestDto) {
        CheckExistProduct(productId);
        Comment comment = new Comment();
        Product product = productRepository.findById(productId).get();
        if (product.isComment() && product.isCommon()) {
            comment.setComment(commentRequestDto.comment());
            if (commentRequestDto.status() != null) {
                comment.setStatus(commentRequestDto.status());
            } else {
                comment.setStatus(Status.REJECTED);
            }
            comment.setProduct(product);
        } else {
            throw new NotAccessException("you are not buyer or you have no any access to create comment", HttpStatus.CREATED);
        }

        return toCommentResponseDto(commentRepository.save(comment));
    }

    @Override
    public List<commentResponseDto> getComments(Long productId) {
        CheckExistProduct(productId);
        return commentRepository.findAllByProductId(productId).stream().map(this::toCommentResponseDto).toList();
    }

    @Override
    public PageDto<commentResponseDto> getCommentsByPage(Long productId, PageRequest pageRequest) {
        CheckExistProduct(productId);
        final var comments = commentRepository.findAllByProductId(productId, pageRequest);
        return buildDto(comments);
    }

    /**
     *
     * @param commentId
     * desc: this delete function used in test files
     */
    @Override
    public void deleteComment(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new BaseNotFoundException("Comment with id: " + commentId + "Not Found", HttpStatus.NOT_FOUND);
        }
        commentRepository.deleteById(commentId);
    }

    @Override
    public voteResponseDto createVote(Long productId, voteRequestDto voteRequestDto) {
        CheckExistProduct(productId);
        Vote vote = new Vote();
        Product product = productRepository.findById(productId).get();
        if (product.isVote() && product.isCommon()) {
            vote.setPoint(voteRequestDto.point());
            if (voteRequestDto.status() != null) {
                vote.setStatus(voteRequestDto.status());
            } else {
                vote.setStatus(Status.REJECTED);
            }
            vote.setProduct(product);
        } else {
            throw new NotAccessException("you are not buyer or you have no any access to create comment", HttpStatus.CREATED);
        }
        return toVoteResponseDto(voteRepository.save(vote));
    }


    @Override
    public reviewResponseDto getReviews(Long productId) {
        CheckExistProduct(productId);
        List<Comment> latestThreeComment = commentRepository.getLatest3Item(productId);
        Long avgVotes = voteRepository.getAvg(productId);
        long countComments = commentRepository.countAllByProductId(productId);
        reviewResponseDto reviewResponseDto = new reviewResponseDto(latestThreeComment, avgVotes, countComments);

        return reviewResponseDto;
    }

    private commentResponseDto toCommentResponseDto(Comment comment) {

        return new commentResponseDto(comment.getId(), comment.getComment(), comment.getCreatedAt(), comment.getUpdatedAt(), comment.getStatus(), comment.getProduct());
    }

    private voteResponseDto toVoteResponseDto(Vote vote) {

        return new voteResponseDto(vote.getId(), vote.getPoint(), vote.getCreatedAt(), vote.getUpdatedAt(), vote.getStatus(), vote.getProduct());
    }

    private PageDto<commentResponseDto> buildDto(final Page<Comment> page) {
        final var commentDtos = page.stream().map(this::toCommentResponseDto).toList();

        return new PageDto<commentResponseDto>(page.getNumber(), page.getSize(), commentDtos, page.getTotalElements(), page.getTotalPages());
    }

    private void CheckExistProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new BaseNotFoundException("Product with id: " + productId + " not found!", HttpStatus.NOT_FOUND);
        }
    }
}
