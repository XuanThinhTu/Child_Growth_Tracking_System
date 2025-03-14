package com.swp.project.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WorkScheduleRequest {
    String date;
    Shifts shifts;
}
