package com.swp.project.entity;

import com.swp.project.enums.FeedbackType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

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

    private double rating;

    private String comment;

    private Date createdAt;

    @Enumerated(EnumType.STRING)
    private FeedbackType feedbackType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "member_id")
    private User member;

    @ManyToOne(optional = true)
    @JoinColumn(name = "doctor_id")
    private User doctor;
}
