package com.swp.project.entity;

import com.swp.project.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String content;
    private String meetingLink;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @ManyToOne
    private User parent;

    @ManyToOne
    private Children children;

    @ManyToOne
    private WorkingSchedule workingSchedule;

}
