package com.watch.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="user")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    private String password;
    private String name;
    private String salt;

}
