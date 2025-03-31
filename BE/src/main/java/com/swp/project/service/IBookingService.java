package com.swp.project.service;

import com.swp.project.dto.response.BookingAvailableResponse;
import com.swp.project.dto.response.BookingResponse;
import com.swp.project.entity.SlotTime;
import com.swp.project.entity.User;
import jakarta.mail.MessagingException;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

public interface IBookingService {
    List<LocalDate> getAvailableBookingDates(YearMonth month);
    List<SlotTime> getAvailableSlotsByDate(LocalDate date);
    BookingResponse createBooking(int childrenId, LocalDate date, int slotTimeId, String note) throws MessagingException;
    BookingAvailableResponse getAvailableBookingResponse(YearMonth month);
    Map<String, String> generateGoogleMeetLink(User member, User doctor, LocalDate bookingDate, SlotTime bookingSlotTime);
    List<BookingResponse> getAllBookings();
    void cancelBooking(int bookingId);
    List<BookingResponse> getAllBookingsByUser();
    void changeStateToProcessing(int bookingId);
    void changeStateToClosed(int bookingId);
}
