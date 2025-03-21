package com.swp.project.entity;

import com.swp.project.enums.FeedbackType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Min(value = 0, message = "Feedback rating must be greater than 0")
    @Max(value = 5, message = "Feedback rating must be smaller than 5")
    private double rating;

    @Column(length = 5000, columnDefinition = "TEXT")
    private String comment;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private FeedbackType feedbackType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "member_id")
    private User member;

    @ManyToOne(optional = true)
    @JoinColumn(name = "doctor_id")
    private User doctor;
}
