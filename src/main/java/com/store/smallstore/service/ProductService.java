package com.store.smallstore.service;

import com.store.smallstore.model.Product;
import com.store.smallstore.model.request.productRequestDto;
import com.store.smallstore.model.response.PageDto;
import com.store.smallstore.model.response.productResponseDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProductService {

    productResponseDto saveProduct(productRequestDto productRequestDto);

    List<productResponseDto> getProducts();

    PageDto<productResponseDto> getProductsByPage(PageRequest pageRequest);

    PageDto<productResponseDto> getProductsByPageIfShowIsTrue(PageRequest pageRequest);

    productResponseDto getProductById(Long id);

    productResponseDto updateProduct(Long id, productRequestDto productRequestDto);

    void deleteProductById(Long id);
}
