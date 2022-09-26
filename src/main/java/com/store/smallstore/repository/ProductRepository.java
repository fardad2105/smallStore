package com.store.smallstore.repository;

import com.store.smallstore.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("select p from Product p where p.isShow =:isShow")
    Page<Product> getAllProductsIfShowIsTrue(boolean isShow,PageRequest pageRequest);


}
