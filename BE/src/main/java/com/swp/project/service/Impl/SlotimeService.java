package com.swp.project.service.Impl;

import com.swp.project.dto.request.SlotTimeRequest;
import com.swp.project.entity.SlotTime;
import com.swp.project.repository.SlotTimeRepository;
import com.swp.project.service.IUSlotTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SlotimeService implements IUSlotTimeService {


    private final SlotTimeRepository SlotTimeRepository;


    @Override
    public SlotTime createSlotTime(SlotTimeRequest request) {

        if (!request.getStartTime().isBefore(request.getEndTime()) ) {
            throw new IllegalArgumentException("Slot start time cannot be before end time");
        }

        SlotTime slotTime = new SlotTime();
        slotTime.setStartTime(request.getStartTime());
        slotTime.setEndTime(request.getEndTime());

        Optional<SlotTime> existing = SlotTimeRepository.findByStartTimeAndEndTime(request.getStartTime(), request.getEndTime());
        if(existing.isPresent()) {
            throw new RuntimeException("Slot time đã tồn tại"); // tieng anh
        }

        return SlotTimeRepository.save(slotTime);

    }


}
