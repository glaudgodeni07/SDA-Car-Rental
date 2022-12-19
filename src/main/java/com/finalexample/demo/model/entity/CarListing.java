package com.finalexample.demo.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "CAR_LISTINGS")
@Data
public class CarListing {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID")
    private Long id;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "PRICE")
    private BigDecimal price;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "IMAGE_NAME")
    private String imageName; //gets the path of image in a file


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CAR_ID",referencedColumnName = "ID")
    private Car car;
}

