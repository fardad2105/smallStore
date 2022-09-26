package com.store.smallstore.controller;

import com.store.smallstore.model.request.productRequestDto;
import com.store.smallstore.model.response.PageDto;
import com.store.smallstore.model.response.productResponseDto;
import com.store.smallstore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class productController {

    private final ProductService productService;

    /**
     * @param productDto
     * @return product added by admin
     */
    @PostMapping
    public ResponseEntity<productResponseDto> createProduct(@RequestBody productRequestDto productDto) {
        return new ResponseEntity<productResponseDto>(productService.saveProduct(productDto), HttpStatus.CREATED);
    }

    /**
     * @param request
     * @param page
     * @param perPage
     * @return all products that have permission to show
     */
    @GetMapping("/getAll")
    public PageDto<productResponseDto> getProducts(final HttpServletRequest request,
                                                   @RequestParam(value = "page", defaultValue = "0") final int page,
                                                   @RequestParam(value = "perPage", defaultValue = "10") final int perPage) {
        return productService.getProductsByPage(PageRequest.of(page, perPage));
    }


    @GetMapping("/{id}")
    public ResponseEntity<productResponseDto> getProductById(@PathVariable long id) {
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<productResponseDto> updateProductById(@PathVariable long id, @RequestBody productRequestDto productDto) {
        return new ResponseEntity<productResponseDto>(productService.updateProduct(id, productDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable long id) {
        productService.deleteProductById(id);
        return new ResponseEntity<>("product with id: " + id + " deleted successful", HttpStatus.OK);
    }
}
