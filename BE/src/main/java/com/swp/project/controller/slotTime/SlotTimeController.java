package com.swp.project.controller.slotTime;

import com.swp.project.dto.request.SlotTimeRequest;
import com.swp.project.entity.SlotTime;
import com.swp.project.service.Impl.SlotimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/admin/slot-times")
public class SlotTimeController {

    @Autowired
    SlotimeService slotimeService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<SlotTime> createSlotTime(@RequestBody SlotTimeRequest request) {
        SlotTime newSlotTime = slotimeService.createSlotTime(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(newSlotTime);
    }

}
