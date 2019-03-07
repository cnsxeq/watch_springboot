package com.watch.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
@Entity
@Table(name = "store")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
@Data
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "bid")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "pid")
    private Place place;

}
