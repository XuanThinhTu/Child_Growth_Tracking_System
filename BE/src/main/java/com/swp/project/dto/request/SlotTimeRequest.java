package com.swp.project.dto.request;

import lombok.*;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SlotTimeRequest {

    private LocalTime startTime;
        private LocalTime endTime;
}
