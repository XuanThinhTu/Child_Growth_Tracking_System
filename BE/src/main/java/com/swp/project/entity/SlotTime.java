package com.swp.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swp.project.enums.SlotTimeShift;
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
    @Enumerated(EnumType.STRING)
    private SlotTimeShift shifts;

    // 1 SlotTime có thể được nhiều WorkingSchedule tham chiếu
    @JsonIgnore
    @OneToMany(mappedBy = "slotTime")
    private List<WorkingSchedule> workingSchedules;

    @JsonIgnore
    @OneToMany(mappedBy = "slotTime")
    private List<Booking> bookings;
}
