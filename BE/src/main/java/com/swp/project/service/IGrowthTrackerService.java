package com.swp.project.service;

import com.swp.project.dto.request.GrowTrackerRequest;
import com.swp.project.dto.response.GrowthTrackerResponse;

import java.util.List;

public interface IGrowthTrackerService {
    GrowthTrackerResponse addGrowthTracker(int childId, GrowTrackerRequest growTrackerRequest);
    GrowthTrackerResponse updateGrowthTracker(int id, GrowTrackerRequest growTrackerRequest);
    void deleteGrowthTracker(int id);
    List<GrowthTrackerResponse> showAllGrowthTracker(int childId);
}
