package com.swp.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "bmi_standards")
public class BmiStandard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer sex;// 1: bé trai, 2: bé gái
    private Integer ageInMonths;

    // Weight
    private Double weightNeg3Sd;
    private Double weightNeg2Sd;
    private Double weightNeg1Sd;
    private Double weightMedian;
    private Double weightPos1Sd;
    private Double weightPos2Sd;
    private Double weightPos3Sd;

    // Height
    private Double heightNeg3Sd;
    private Double heightNeg2Sd;
    private Double heightNeg1Sd;
    private Double heightMedian;
    private Double heightPos1Sd;
    private Double heightPos2Sd;
    private Double heightPos3Sd;

    // BMI
    private Double bmiNeg3Sd;
    private Double bmiNeg2Sd;
    private Double bmiNeg1Sd;
    private Double bmiMedian;
    private Double bmiPos1Sd;
    private Double bmiPos2Sd;
    private Double bmiPos3Sd;
}
