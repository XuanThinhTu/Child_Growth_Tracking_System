package com.swp.project.mapper;

import com.swp.project.dto.response.GrowthTrackerResponse;
import com.swp.project.entity.GrowthTracker;
import org.springframework.stereotype.Component;

@Component
public class GrowthTrackerMapper {
    public GrowthTrackerResponse toDto(GrowthTracker growthTracker) {
        if (growthTracker == null) {
            return null;
        }
        return GrowthTrackerResponse.builder()
                .id(growthTracker.getId())
                .height(growthTracker.getHeight())
                .weight(growthTracker.getWeight())
                .headCircumference(growthTracker.getHeadCircumference())
                .bmi(growthTracker.getBmi())
                .measuredAt(growthTracker.getMeasuredAt())
                .childrenId(growthTracker.getChildren().getId())
                .build();
    }

//    GrowthTracker toGrowthTracker(GrowTrackerRequest growTrackerRequest) {
//        if (growTrackerRequest == null) {
//            return null;
//        }
//        GrowthTracker growthTracker = new GrowthTracker();
//        growthTracker.setHeight(growTrackerRequest.height());
//        growthTracker.setWeight(growTrackerRequest.weight());
//        growthTracker.setHeadCircumference(growTrackerRequest.headCircumference());
//    }
}
