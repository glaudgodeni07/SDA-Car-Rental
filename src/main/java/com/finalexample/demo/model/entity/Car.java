package com.finalexample.demo.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="MY_CARS")
@Data //Getter and Setter
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID")
    private Long id;

    @Column(name="BRAND")
    private String brand;

    @Column(name="MODEL")
    private String model;

    @Column(name="YEAR")
    private int year;

    @Column(name="FUEL_TYPE")
    private String fuelType;

    @Column(name="POWER")
    private double power;
}
