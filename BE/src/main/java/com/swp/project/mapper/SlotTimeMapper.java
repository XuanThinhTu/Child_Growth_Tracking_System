package com.swp.project.mapper;

import com.swp.project.dto.response.SlotTimeDTO;
import com.swp.project.entity.SlotTime;
import org.springframework.stereotype.Component;

@Component
public class SlotTimeMapper {
    public SlotTimeDTO toSlotTimeDTO(SlotTime slotTime) {
        return SlotTimeDTO.builder()
                .id(slotTime.getId())
                .startTime(slotTime.getStartTime())
                .endTime(slotTime.getEndTime())
                .build();
    }
}
