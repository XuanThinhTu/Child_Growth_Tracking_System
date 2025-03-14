package com.swp.project.dto.response;

import lombok.*;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WorkingScheduleDTO {
    private int id;
    private String date;
    private String status;
    private int doctorId;
    private String doctorName;
    private LocalTime startTime;
    private LocalTime endTime;
}
