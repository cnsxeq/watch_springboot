package com.watch.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="product")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name="bid")
    private Brand brand;

    private String name;
    private String price;
    private String sex;
    private String core;
    private String shell;
    private int dial_diameter;

    @Transient
    private ProductImage firstProductImage;
    @Transient
    private List<ProductImage> productPluralImages;
    @Transient
    private int saleCount;

}
