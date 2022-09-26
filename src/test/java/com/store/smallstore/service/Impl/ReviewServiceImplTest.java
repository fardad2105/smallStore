package com.store.smallstore.service.Impl;

import com.store.smallstore.model.enums.Status;
import com.store.smallstore.model.request.commentRequestDto;
import com.store.smallstore.model.request.productRequestDto;
import com.store.smallstore.model.request.voteRequestDto;
import com.store.smallstore.model.response.PageDto;
import com.store.smallstore.model.response.commentResponseDto;
import com.store.smallstore.model.response.productResponseDto;
import com.store.smallstore.model.response.reviewResponseDto;
import com.store.smallstore.service.ProductService;
import com.store.smallstore.service.ReviewService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReviewServiceImplTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ProductService productService;

    private productRequestDto productRequestDto;

    private productResponseDto productResponseDto;
    private commentRequestDto commentRequestDto;
    private commentResponseDto commentResponseDto;
    private voteRequestDto voteRequestDto;
    private reviewResponseDto reviewResponseDto;


    @BeforeEach
    void setUp() {
        commentRequestDto = new commentRequestDto("new comment", Status.REJECTED);
        voteRequestDto = new voteRequestDto(20,Status.REJECTED);
        productRequestDto = new productRequestDto("iphone14", "2000$", "this is best phone",
                "iphone14.png", "exist", true, true, true, true);
    }

    @AfterEach
    void tearDown() {
        if (productResponseDto !=null) {
            productService.deleteProductById(productResponseDto.id());
        }
        if (commentResponseDto!=null) {
            reviewService.deleteComment(commentResponseDto.id());
        }
    }

    @Test
    void createComment() {
        productResponseDto = productService.saveProduct(productRequestDto);
        commentResponseDto = reviewService.createComment(productResponseDto.id(),commentRequestDto);
        assertEquals(commentResponseDto.comment(),commentRequestDto.comment());
    }

    @Test
    void getComments() {
        productResponseDto = productService.saveProduct(productRequestDto);
        commentResponseDto = reviewService.createComment(productResponseDto.id(),commentRequestDto);
        List<commentResponseDto> commentResponseDtos = reviewService.getComments(productResponseDto.id());
        assertTrue(commentResponseDtos.size()!=0);
    }

    @Test
    void getCommentsByPage() {
        productResponseDto = productService.saveProduct(productRequestDto);
        commentResponseDto = reviewService.createComment(productResponseDto.id(),commentRequestDto);
        PageDto<commentResponseDto> pageDto = reviewService.getCommentsByPage(productResponseDto.id(), PageRequest.of(0,10));
        List<commentResponseDto> commentDtoList = pageDto.body().stream().toList();
        assertTrue(commentDtoList.size()!=0);
    }


    @Test
    void getReviews() {
        productResponseDto = productService.saveProduct(productRequestDto);
        commentResponseDto = reviewService.createComment(productResponseDto.id(),commentRequestDto);
        reviewService.createVote(productResponseDto.id(),voteRequestDto);
        reviewResponseDto = reviewService.getReviews(productResponseDto.id());
        assertEquals(commentResponseDto.comment(),reviewResponseDto.comments().get(0).getComment());
    }
}