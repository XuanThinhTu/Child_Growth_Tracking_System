package com.swp.project.mapper;

import com.swp.project.dto.response.StandardIndexResponse;
import com.swp.project.entity.BmiStandard;
import org.springframework.stereotype.Component;

@Component
public class StandardIndexMapper {
    public StandardIndexResponse toDto(BmiStandard entity) {
        if (entity == null) {
            return null;
        }
        return new StandardIndexResponse(
                entity.getId(),
                entity.getGender(),
                entity.getPeriod(),
                entity.getPeriodType(),

                // Weight
                entity.getWeightNeg4Sd(),
                entity.getWeightNeg3Sd(),
                entity.getWeightNeg2Sd(),
                entity.getWeightNeg1Sd(),
                entity.getWeightMedian(),
                entity.getWeightPos1Sd(),
                entity.getWeightPos2Sd(),
                entity.getWeightPos3Sd(),
                entity.getWeightPos4Sd(),

                // Height
                entity.getHeightNeg4Sd(),
                entity.getHeightNeg3Sd(),
                entity.getHeightNeg2Sd(),
                entity.getHeightNeg1Sd(),
                entity.getHeightMedian(),
                entity.getHeightPos1Sd(),
                entity.getHeightPos2Sd(),
                entity.getHeightPos3Sd(),
                entity.getHeightPos4Sd(),

                // BMI
                entity.getBmiNeg4Sd(),
                entity.getBmiNeg3Sd(),
                entity.getBmiNeg2Sd(),
                entity.getBmiNeg1Sd(),
                entity.getBmiMedian(),
                entity.getBmiPos1Sd(),
                entity.getBmiPos2Sd(),
                entity.getBmiPos3Sd(),
                entity.getBmiPos4Sd(),

                // Head circumference
                entity.getHeadCircumferenceNeg4Sd(),
                entity.getHeadCircumferenceNeg3Sd(),
                entity.getHeadCircumferenceNeg2Sd(),
                entity.getHeadCircumferenceNeg1Sd(),
                entity.getHeadCircumferenceMedian(),
                entity.getHeadCircumferencePos1Sd(),
                entity.getHeadCircumferencePos2Sd(),
                entity.getHeadCircumferencePos3Sd(),
                entity.getHeadCircumferencePos4Sd()
        );
    }
}
