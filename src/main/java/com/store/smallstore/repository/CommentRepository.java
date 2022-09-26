package com.store.smallstore.repository;

import com.store.smallstore.model.Comment;
import com.store.smallstore.model.Product;
import com.store.smallstore.model.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByProductId(Long productId);

    Page<Comment> findAllByProductId(Long productId, PageRequest pageRequest);

    @Query(value = "select * from T_COMMENT c where c.productId=:productId order by ID DESC LIMIT 3", nativeQuery = true)
    List<Comment> getLatest3Item(long productId);

    Long countAllByProductId(Long productId);

}
