package com.watch.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="property")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
@Data
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name="pid")
    private Product product;

    private String number_;
    private String series;
    private String core_model;
    private String core_diameter;
    private String core_thickness;
    private String balance_wheel;
    private String vibration_frequency;
    private String gemstone;
    private String sqares;
    private String reserve;
    private String shell_thickness;
    private String dial_color;
    private String dial_shape;
    private String watchband_color;
    private String button_type;
    private String beitou;
    private String weight;
    private String waterproof_depth;
    private String ear_spacing;
    private String material_shell;
    private String material_case;
    private String material_mirror;
    private String material_crown;
    private String material_band;
    private String material_button;
    private String function;

}
