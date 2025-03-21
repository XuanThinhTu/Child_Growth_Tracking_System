package com.swp.project.dto.response;

import com.swp.project.enums.PeriodType;

public record StandardIndexResponse(
        Long id,

        String gender,
        Integer period,
        PeriodType periodType,

        Double weightNeg4Sd,
        Double weightNeg3Sd,
        Double weightNeg2Sd,
        Double weightNeg1Sd,
        Double weightMedian,
        Double weightPos1Sd,
        Double weightPos2Sd,
        Double weightPos3Sd,
        Double weightPos4Sd,

        // Height
        Double heightNeg4Sd,
        Double heightNeg3Sd,
        Double heightNeg2Sd,
        Double heightNeg1Sd,
        Double heightMedian,
        Double heightPos1Sd,
        Double heightPos2Sd,
        Double heightPos3Sd,
        Double heightPos4Sd,

        // BMI
        Double bmiNeg4Sd,
        Double bmiNeg3Sd,
        Double bmiNeg2Sd,
        Double bmiNeg1Sd,
        Double bmiMedian,
        Double bmiPos1Sd,
        Double bmiPos2Sd,
        Double bmiPos3Sd,
        Double bmiPos4Sd,

        // Head circumference
        Double headCircumferenceNeg4Sd,
        Double headCircumferenceNeg3Sd,
        Double headCircumferenceNeg2Sd,
        Double headCircumferenceNeg1Sd,
        Double headCircumferenceMedian,
        Double headCircumferencePos1Sd,
        Double headCircumferencePos2Sd,
        Double headCircumferencePos3Sd,
        Double headCircumferencePos4Sd
) {
}
