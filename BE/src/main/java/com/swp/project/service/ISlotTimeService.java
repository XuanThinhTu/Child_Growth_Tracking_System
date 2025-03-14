package com.swp.project.service;

import com.swp.project.dto.request.SlotTimeCreationRequest;
import com.swp.project.dto.response.SlotTimeDTO;

import java.util.List;

public interface ISlotTimeService {
    SlotTimeDTO addSlotTime(SlotTimeCreationRequest request);

    List<SlotTimeDTO> getAllSlotTimes();
}
