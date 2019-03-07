package com.watch.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="brand")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
@Data
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    private String name;
    private String ename;

    @Transient
    List<Product> products;

    @Transient
    Culture culture;
}

