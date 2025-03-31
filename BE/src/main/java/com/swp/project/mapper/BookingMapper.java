package com.swp.project.mapper;

import com.swp.project.dto.response.BookingResponse;
import com.swp.project.entity.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {
    public BookingResponse toDto(Booking booking) {
        if (booking == null) {
            return null;
        }
        return new BookingResponse(
                booking.getId(),
                booking.getDate(),
                booking.getContent(),
                booking.getMeetingLink(),
                booking.getStatus(),
                booking.getCreatedAt(),
                booking.getChildren().getId(),
                booking.getChildren().getName(),
                booking.getChildren().getBirthDate(),
                booking.getChildren().getGender(),
                booking.getMember().getId(),
                booking.getMember().getFirstName(),
                booking.getMember().getLastName(),
                booking.getDoctor().getId(),
                booking.getDoctor().getFirstName(),
                booking.getDoctor().getLastName(),
                booking.getSlotTime().getId(),
                booking.getSlotTime().getStartTime(),
                booking.getSlotTime().getEndTime()
        );
    }
}
