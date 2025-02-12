package com.swp.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "slot_times")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SlotTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Ví dụ dùng kiểu số hoặc LocalTime
    @Column(name = "start_time")
    private LocalTime startTime;
    @Column(name = "end_time")
    private LocalTime endTime;

    // 1 SlotTime có thể được nhiều WorkingSchedule tham chiếu
    @OneToMany(mappedBy = "slotTime")
    private List<WorkingSchedule> workingSchedules;

    @OneToMany(mappedBy = "slotTime")
    private List<Booking> bookings;
}
