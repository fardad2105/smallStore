package com.store.smallstore.repository;

import com.store.smallstore.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    @Query("select avg (v.point) from Vote v where v.product.id =:productId")
    Long getAvg(Long productId);
}
