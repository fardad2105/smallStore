package com.store.smallstore.service.Impl;

import com.store.smallstore.exception.BaseNotFoundException;
import com.store.smallstore.model.Product;
import com.store.smallstore.model.request.productRequestDto;
import com.store.smallstore.model.response.PageDto;
import com.store.smallstore.model.response.productResponseDto;
import com.store.smallstore.repository.ProductRepository;
import com.store.smallstore.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public productResponseDto saveProduct(productRequestDto productRequestDto) {
        /**
         * desc: in this here we could use MapStruct for avoid more line code
         */
        Product product = new Product();
        return getProductResponseDto(productRequestDto, product);
    }

    @Override
    public List<productResponseDto> getProducts() {
        return productRepository.findAll().stream()
                .map(this::toProductResponseDto)
                .toList();
    }

    @Override
    public PageDto<productResponseDto> getProductsByPageIfShowIsTrue(PageRequest pageRequest) {
        final var comments = productRepository.getAllProductsIfShowIsTrue(true, pageRequest);
        return buildDto(comments);
    }

    @Override
    public PageDto<productResponseDto> getProductsByPage(PageRequest pageRequest) {
        final var comments = productRepository.getAllProductsIfShowIsTrue(true, pageRequest);
        return buildDto(comments);
    }

    @Override
    public productResponseDto getProductById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new BaseNotFoundException("Product with id: " + id + " not found!", HttpStatus.NOT_FOUND);
        }
        return toProductResponseDto(productRepository.findById(id).get());
    }

    @Override
    public productResponseDto updateProduct(Long id, productRequestDto productRequestDto) {
        if (!productRepository.existsById(id)) {
            throw new BaseNotFoundException("Product with id: " + id + " not found!", HttpStatus.NOT_FOUND);
        }
        Product updatedProduct = productRepository.findById(id).get();
        return getProductResponseDto(productRequestDto, updatedProduct);
    }

    @Override
    public void deleteProductById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new BaseNotFoundException("Product with id: " + id + " not found!", HttpStatus.NOT_FOUND);
        }
        productRepository.deleteById(id);
    }

    private productResponseDto getProductResponseDto(productRequestDto productRequestDto, Product product) {
        product.setName(productRequestDto.name());
        product.setPrice(productRequestDto.price());
        product.setDescription(productRequestDto.description());
        product.setImage(productRequestDto.image());
        product.setCondition(productRequestDto.condition());
        product.setShow(productRequestDto.isShow());
        product.setComment(productRequestDto.isComment());
        product.setVote(productRequestDto.isVote());
        product.setCommon(productRequestDto.isCommon());


        return toProductResponseDto(productRepository.save(product));
    }


    private productResponseDto toProductResponseDto(Product product) {

        return new productResponseDto(product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getImage(),
                product.getCondition(),
                product.isShow(),
                product.isComment(),
                product.isVote(),
                product.isCommon()
        );
    }

    private PageDto<productResponseDto> buildDto(final Page<Product> page) {
        final var productDtos = page.stream()
                .map(this::toProductResponseDto)
                .toList();

        return new PageDto<productResponseDto>(page.getNumber(),
                page.getSize(),
                productDtos,
                page.getTotalElements(),
                page.getTotalPages());
    }
}
