package com.swp.project.entity;

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
@Table(name = "growth_trackers")
public class GrowthTracker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Double height;
    private Double weight;
    private Double headCircumference;
    private Double bmi;
    private LocalDateTime measuredAt;
    @ManyToOne(cascade =CascadeType.ALL)
    private Children children;
    private boolean deleted = false;
}
