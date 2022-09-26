package com.store.smallstore.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "T_PRODUCT")
@RequiredArgsConstructor
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String price;
    private String description;
    private String image;
    private String condition;
    //and etc .....

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<Comment> comments;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<Vote> votes;

    private boolean isShow; // product can show for user or not
    private boolean isComment; // user can write comment for product or not
    private boolean isVote; // user can select vote for product or not
    private boolean isCommon; // product is common for all user to write comment or just buyer users can write comment


}
