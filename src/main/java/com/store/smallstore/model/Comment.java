package com.store.smallstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.store.smallstore.model.enums.Status;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "T_COMMENT")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;
    @Column(name = "create_at")
    @CreationTimestamp
    @CreatedDate
    private Timestamp createdAt;
    @Column(name = "update_at")
    @UpdateTimestamp
    @LastModifiedDate
    private Timestamp updatedAt;
    @Column(name = "status", columnDefinition = "varchar(32) default 'REJECTED'")
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "productId")
    @JsonIgnore
    private Product product;
}
