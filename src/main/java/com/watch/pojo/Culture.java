package com.watch.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="culture")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
@Data
public class Culture{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    private String introduction;
    private String story;
    private String characterstic;
}