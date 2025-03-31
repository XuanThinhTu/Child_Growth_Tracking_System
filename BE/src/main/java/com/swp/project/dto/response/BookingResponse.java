package com.swp.project.dto.response;

import com.swp.project.enums.BookingStatus;
import lombok.Builder;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
public record BookingResponse(
        int id,

        LocalDate date,

        String content,

        String meetingLink,

        BookingStatus status,

        LocalDateTime createdAt,

        int childrenId,

        String childName,

        Date childBirthDate,

        String childGender,

        int memberId,

        String memberFirstName,

        String memberLastName,

        int doctorId,

        String doctorFirstName,

        String doctorLastName,

        int slotTimeId,

        LocalTime startTime,

        LocalTime endTime
) {
}
