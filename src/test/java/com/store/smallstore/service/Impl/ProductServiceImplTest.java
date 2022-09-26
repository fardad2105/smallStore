package com.store.smallstore.service.Impl;

import com.store.smallstore.exception.BaseNotFoundException;
import com.store.smallstore.model.request.productRequestDto;
import com.store.smallstore.model.response.PageDto;
import com.store.smallstore.model.response.productResponseDto;
import com.store.smallstore.service.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProductServiceImplTest {

    @Autowired
    private ProductService productService;

    private productRequestDto productRequestDto;

    private productResponseDto productResponseDto;

    @BeforeEach
    void setUp() {
        productRequestDto = new productRequestDto("iphone14", "2000$", "this is best phone",
                "iphone14.png", "exist", true, true, true, true);
    }

    @AfterEach
    void tearDown() {
        if (productResponseDto != null) {
            productService.deleteProductById(productResponseDto.id());
        }
    }

    @Test
    void saveProduct() {
        productResponseDto = productService.saveProduct(productRequestDto);
        assertEquals(productRequestDto.description(),productResponseDto.description());
    }

    @Test
    void getProducts() {
        productResponseDto = productService.saveProduct(productRequestDto);
        List<productResponseDto> productResponseDtos = productService.getProducts();
        assertTrue(productResponseDtos.size()!=0);
    }

    @Test
    void getProductsByPageIfShowIsTrue() {
        productResponseDto = productService.saveProduct(productRequestDto);
        PageDto<productResponseDto> pages = productService.getProductsByPageIfShowIsTrue(PageRequest.of(0,10));
        List<productResponseDto> productDtoList = pages.body().stream().toList();
        assertTrue(productDtoList.size() != 0);
    }

    @Test
    void getProductsByPage() {
        productResponseDto = productService.saveProduct(productRequestDto);
        PageDto<productResponseDto> pages = productService.getProductsByPage(PageRequest.of(0,10));
        List<productResponseDto> productDtoList = pages.body().stream().toList();
        assertTrue(productDtoList.size() != 0);
    }

    @Test
    void getProductById() {
        productResponseDto = productService.saveProduct(productRequestDto);
        productResponseDto = productService.getProductById(1L);
        assertEquals(productRequestDto.description(),productResponseDto.description());
    }

    @Test
    void updateProduct() {
        productResponseDto = productService.saveProduct(productRequestDto);
        productRequestDto updated = new productRequestDto("Huawei", "2000$", "this is best phone",
                "iphone14.png", "exist", true, true, true, true);
        productResponseDto = productService.updateProduct(productResponseDto.id(),updated);
        assertEquals(updated.name(), productResponseDto.name());

    }

    @Test
    void deleteProductById() {
        Exception exception = assertThrows(BaseNotFoundException.class,
                ()->{
                    productService.deleteProductById(100L);
                });
        String expectedMessage = "Product with id: "+100L+" not found!";
        String actualMessage = exception.getMessage();
        //after deleted

        assertTrue(actualMessage.contains(expectedMessage));
    }
}