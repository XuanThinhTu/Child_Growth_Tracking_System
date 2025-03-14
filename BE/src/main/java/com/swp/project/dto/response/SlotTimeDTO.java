package com.swp.project.dto.response;

import lombok.*;

import java.time.LocalTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SlotTimeDTO {
    private int id;
    private LocalTime startTime;
    private LocalTime endTime;

}
