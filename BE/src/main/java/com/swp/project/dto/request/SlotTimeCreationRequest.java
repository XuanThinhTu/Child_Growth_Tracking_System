package com.swp.project.dto.request;

import com.swp.project.enums.SlotTimeShift;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SlotTimeCreationRequest {
    private String startTime;
    private String endTime;
    private SlotTimeShift shifts;
}
