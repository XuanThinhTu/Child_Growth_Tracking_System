package com.swp.project.controller;

import com.swp.project.dto.request.CreateBookingNoteRequest;
import com.swp.project.dto.response.ApiResponse;
import com.swp.project.dto.response.BookingAvailableResponse;
import com.swp.project.dto.response.BookingResponse;
import com.swp.project.repository.SlotTimeRepository;
import com.swp.project.repository.UserRepository;
import com.swp.project.service.Impl.BookingService;
//import com.swp.project.service.Impl.GoogleMeetService;
import com.swp.project.service.Impl.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    private final UserService userService;

    private final UserRepository userRepository;

    private final SlotTimeRepository slotTimeRepository;

//    private final GoogleMeetService googleMeetService;



    @GetMapping("/available")
    public ResponseEntity<ApiResponse<BookingAvailableResponse>> getAllAvailableBookingsSchedule(@RequestParam YearMonth yearMonth) {
        return ResponseEntity.ok(
                ApiResponse.<BookingAvailableResponse>builder()
                        .message("Available bookings")
                        .data(bookingService.getAvailableBookingResponse(yearMonth))
                        .build()
        );
    }

    @PostMapping("/{childId}")
    public ResponseEntity<ApiResponse<?>> createBookingSchedule(
            @PathVariable int childId,
            @RequestParam LocalDate date,
            @RequestParam int slotTimeId,
            @RequestBody CreateBookingNoteRequest note
    ) throws MessagingException {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Booking schedule")
                        .data(bookingService.createBooking(childId, date, slotTimeId, note.note()))
                        .build()
        );
    }

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<List<BookingResponse>>> getAllBookings() {
        return ResponseEntity.ok(
                ApiResponse.<List<BookingResponse>>builder()
                        .message("List all bookings")
                        .data(bookingService.getAllBookings())
                        .build()
        );
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse<List<BookingResponse>>> getAllBookingsByUser() {
        return ResponseEntity.ok(
                ApiResponse.<List<BookingResponse>>builder()
                        .message("List all bookings by user")
                        .data(bookingService.getAllBookingsByUser())
                        .build()
        );
    }

    @PutMapping("/{bookingId}/process")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public ResponseEntity<ApiResponse<?>> processBooking(@PathVariable Integer bookingId) {
        bookingService.changeStateToProcessing(bookingId);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Booking with id: " + bookingId + " changed to processing successfully")
                        .build()
        );
    }

    @PutMapping("/{bookingId}/cancel")
    public ResponseEntity<ApiResponse<?>> cancelBooking(@PathVariable Integer bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Booking with id: " + bookingId + " cancelled successfully")
                        .build()
        );
    }

    @PutMapping("/{bookingId}/close")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DOCTOR')")
    public ResponseEntity<ApiResponse<?>> closeBooking(@PathVariable Integer bookingId) {
        bookingService.changeStateToClosed(bookingId);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Booking with id: " + bookingId + " closed successfully")
                        .build()
        );
    }

//    @PostMapping("/test")
//    public ResponseEntity<ApiResponse<?>> testMeetingLink() throws MessagingException {
//        SlotTime slotTime = new SlotTime();
//        LocalDate test = LocalDate.now();
//        return ResponseEntity.ok(
//                ApiResponse.builder()
//                        .message("Booking schedule")
//                        .data(bookingService.generateGoogleMeetLink(userService.getAuthenticatedUser(), userRepository.findById(2).get(), test, slotTimeRepository.findById(2).get()))
//                        .build()
//        );
//    }
//
//    @PostMapping("/test/delete")
//    public ResponseEntity<ApiResponse<?>> testDeleteMeetingLink(@RequestParam String eventId) throws MessagingException {
//        SlotTime slotTime = new SlotTime();
//        LocalDate test = LocalDate.now();
//
//        googleMeetService.cancelAppointment(eventId);
//        return ResponseEntity.ok(
//                ApiResponse.builder()
//                        .message("deleted schedule")
//                        .build()
//        );
//    }


}
