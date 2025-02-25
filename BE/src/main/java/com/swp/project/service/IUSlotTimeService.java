package com.swp.project.service;

import com.swp.project.dto.request.SlotTimeRequest;
import com.swp.project.entity.SlotTime;


public interface IUSlotTimeService {


    SlotTime createSlotTime(SlotTimeRequest request);
}
