package com.swp.project.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record BookingAvailableResponse(
        String yearMonth,
        List<LocalDate> availableDates
) {
    @Builder
    public record LocalDate(
            String date,
            List<SlotTime> slotTimes
    ) {
        @Builder
        public record SlotTime(
                int slotTimeId,
                String startTime,
                String endTime
        ) {
        }
    }
}
