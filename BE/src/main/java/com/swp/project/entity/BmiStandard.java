package com.swp.project.entity;

import com.swp.project.enums.PeriodType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "bmi_standards")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class BmiStandard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String gender;
    private Integer period;
    @Enumerated(EnumType.STRING)
    private PeriodType periodType;// DAY: duoi 5 tuoi, MONTH: tu 5-19 tuoi
    // Weight
    private Double weightNeg4Sd;
    private Double weightNeg3Sd;
    private Double weightNeg2Sd;
    private Double weightNeg1Sd;
    private Double weightMedian;
    private Double weightPos1Sd;
    private Double weightPos2Sd;
    private Double weightPos3Sd;
    private Double weightPos4Sd;

    // Height
    private Double heightNeg4Sd;
    private Double heightNeg3Sd;
    private Double heightNeg2Sd;
    private Double heightNeg1Sd;
    private Double heightMedian;
    private Double heightPos1Sd;
    private Double heightPos2Sd;
    private Double heightPos3Sd;
    private Double heightPos4Sd;

    // BMI
    private Double bmiNeg4Sd;
    private Double bmiNeg3Sd;
    private Double bmiNeg2Sd;
    private Double bmiNeg1Sd;
    private Double bmiMedian;
    private Double bmiPos1Sd;
    private Double bmiPos2Sd;
    private Double bmiPos3Sd;
    private Double bmiPos4Sd;

    // Head circumference
    private Double headCircumferenceNeg4Sd;
    private Double headCircumferenceNeg3Sd;
    private Double headCircumferenceNeg2Sd;
    private Double headCircumferenceNeg1Sd;
    private Double headCircumferenceMedian;
    private Double headCircumferencePos1Sd;
    private Double headCircumferencePos2Sd;
    private Double headCircumferencePos3Sd;
    private Double headCircumferencePos4Sd;

}
