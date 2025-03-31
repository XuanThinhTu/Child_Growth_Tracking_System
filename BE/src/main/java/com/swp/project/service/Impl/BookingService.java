package com.swp.project.service.Impl;

import com.swp.project.dto.response.BookingAvailableResponse;
import com.swp.project.dto.response.BookingResponse;
import com.swp.project.entity.*;
import com.swp.project.enums.BookingStatus;
import com.swp.project.exception.ResourceNotFoundException;
import com.swp.project.mapper.BookingMapper;
import com.swp.project.repository.*;
import com.swp.project.service.IBookingService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService implements IBookingService {

    private final WorkingScheduleRepository workingScheduleRepository;

    private final BookingRepository bookingRepository;

    private final BookingMapper bookingMapper;

    private final EmailService emailService;

    private final UserRepository userRepository;

    private final ChildrenRepository childrenRepository;

    private final UserService userService;

    private final GoogleMeetService googleMeetService;

    @Override
    public List<LocalDate> getAvailableBookingDates(YearMonth month) {
        LocalDate start = month.atDay(1);
        LocalDate end = month.atEndOfMonth();
        return workingScheduleRepository.findByDateBetween(start, end).stream()
                .filter(workingSchedule ->
                        !bookingRepository.existsByDateAndDoctorAndSlotTime(
                            workingSchedule.getDate(),
                            workingSchedule.getDoctor(),
                            workingSchedule.getSlotTime()
                ) && LocalDate.now().isBefore(workingSchedule.getDate()))
                .map(WorkingSchedule::getDate)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public BookingAvailableResponse getAvailableBookingResponse(YearMonth month) {
        return BookingAvailableResponse.builder()
                .yearMonth(month.toString())
                .availableDates(
                        getAvailableBookingDates(month).stream()
                                .map(date -> BookingAvailableResponse.LocalDate.builder()
                                        .date(date.toString())
                                        .slotTimes(
                                                getAvailableSlotsByDate(date).stream()
                                                        .map(slot -> BookingAvailableResponse.LocalDate.SlotTime.builder()
                                                                .slotTimeId(slot.getId())
                                                                .startTime(slot.getStartTime().toString())
                                                                .endTime(slot.getEndTime().toString())
                                                                .build())
                                                        .collect(Collectors.toList())
                                        )
                                        .build())
                                .collect(Collectors.toList())
                )
                .build();
    }


    @Override
    public List<SlotTime> getAvailableSlotsByDate(LocalDate date) {
        return workingScheduleRepository.findByDate(date).stream()
                .filter(workingSchedule ->
                        !bookingRepository
                                .existsByDateAndDoctorAndSlotTime(
                                        date,
                                        workingSchedule.getDoctor(),
                                        workingSchedule.getSlotTime()
                                ) && LocalDate.now().isBefore(date))
                .map(WorkingSchedule::getSlotTime)
                .collect(Collectors.toList());
    }

    @Override
    public BookingResponse createBooking(int childrenId, LocalDate date, int slotTimeId, String note) throws MessagingException {
        // Kiểm tra nếu ngày đặt lịch trước ngày hiện tại
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Cannot book a time in the past.");
        }
        List<WorkingSchedule> schedules = workingScheduleRepository.findByDateAndSlotTimeId(date, slotTimeId);
        WorkingSchedule availableSchedule = schedules.stream()
                .filter(ws -> !bookingRepository.existsByDateAndDoctorAndSlotTime(
                                date, ws.getDoctor(),
                                ws.getSlotTime()
                        ))
                .findAny()
                .orElseThrow(() -> new ResourceNotFoundException("No more available doctor for this slot."));

        if (LocalDateTime.now().isAfter(LocalDateTime.of(date, availableSchedule.getSlotTime().getStartTime()))) {
            throw new IllegalArgumentException("Cannot book: the slot time has already passed.");
        }

        User member = userService.getAuthenticatedUser();
        User doctor = availableSchedule.getDoctor();
        Children child = childrenRepository.findById(childrenId)
                .orElseThrow(() -> new ResourceNotFoundException("Children with id: " + childrenId + " not found"));

        Booking booking = new Booking();
        booking.setDate(date);
        booking.setMember(member);
        booking.setChildren(child);
        booking.setDoctor(doctor);
        booking.setSlotTime(availableSchedule.getSlotTime());
        booking.setContent(note);
        booking.setStatus(BookingStatus.PENDING);
        booking.setCreatedAt(LocalDateTime.now());
        Map<String, String> googleEvent = generateGoogleMeetLink(member, doctor, date, availableSchedule.getSlotTime());
        booking.setMeetingLink(googleEvent.get(googleEvent.keySet().toArray()[0]));
        booking.setGoogleEventId((String) googleEvent.keySet().toArray()[0]);

        bookingRepository.save(booking);

        // Gửi email xác nhận kèm link Google Meet
//        emailService.sendBookingConfirmation(member, booking);
        emailService.sendEmail(member.getEmail(), emailService.subjectCreateBooking(), emailService.sendBookingConfirmation(member, booking));

        return bookingMapper.toDto(booking);
    }

    @Override
    public Map<String, String> generateGoogleMeetLink(User member, User doctor, LocalDate bookingDate, SlotTime bookingSlotTime) {
        return googleMeetService.generateGoogleMeetLink(member, doctor, bookingDate, bookingSlotTime);
    }

    @Override
    public List<BookingResponse> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingResponse> getAllBookingsByUser() {
        User user = userService.getAuthenticatedUser();
        return bookingRepository.findByMemberOrDoctor(user, user).stream()
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void changeStateToProcessing(int bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));

        // Nếu booking đã hủy hoặc hoàn thành thì không cho phép hủy nữa (tùy nghiệp vụ)
        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new RuntimeException("Appointment is in a state that can not be processing");
        }

        if (booking.getStatus() == BookingStatus.PROCESSING) {
            throw new RuntimeException("Appointment is already processing");
        }

        booking.setStatus(BookingStatus.PROCESSING);
        bookingRepository.save(booking);
    }

    @Override
    public void changeStateToClosed(int bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));

        // Nếu booking đã hủy hoặc hoàn thành thì không cho phép hủy nữa (tùy nghiệp vụ)
        if (booking.getStatus() != BookingStatus.PROCESSING) {
            throw new RuntimeException("Appointment is in a state that can not be closed");
        }

        if (booking.getStatus() == BookingStatus.CLOSED) {
            throw new RuntimeException("Appointment is already closed");
        }

        booking.setStatus(BookingStatus.PROCESSING);
        bookingRepository.save(booking);
    }


    @Override
    public void cancelBooking(int bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));

        // Nếu booking đã hủy hoặc hoàn thành thì không cho phép hủy nữa (tùy nghiệp vụ)
        if (booking.getStatus() == BookingStatus.CANCELLED || booking.getStatus() == BookingStatus.CLOSED) {
            throw new RuntimeException("Appointment is already canceled or closed");
        }

        if (booking.getStatus() == BookingStatus.PROCESSING) {
            throw new RuntimeException("Appointment is in a state that can not be canceled");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        googleMeetService.cancelAppointment(booking.getGoogleEventId());

        bookingRepository.save(booking);

    }
}
