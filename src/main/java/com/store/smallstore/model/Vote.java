package com.store.smallstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.store.smallstore.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "T_VOTE")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * desc: in this project I try to consider simple state for vote,
     * for this reason I commented startPoint and endPoint
     */
//    private String startPoint;
//    private String endPoint;
    private long point;
    @Column(name = "status", columnDefinition = "varchar(32) default 'REJECTED'")
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "create_at")
    @CreationTimestamp
    private Timestamp createdAt;
    @Column(name = "update_at")
    @UpdateTimestamp
    private Timestamp updatedAt;
    @ManyToOne
    @JoinColumn(name = "productId")
    @JsonIgnore
    private Product product;
}
