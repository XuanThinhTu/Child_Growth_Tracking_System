package com.swp.project.entity;

import com.swp.project.validator.DobConstraint;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "children")
public class Children {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @DobConstraint(maxAge = 18, message = "Invalid date of birth")
    private Date birthDate;
    private String gender;

    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "children", cascade = {CascadeType.ALL, CascadeType.REMOVE}, orphanRemoval = true)
    private List<GrowthTracker> growthTrackers;

    @OneToMany(mappedBy = "children", cascade = {CascadeType.ALL, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Booking> bookings;

    @OneToMany(mappedBy = "child", cascade = {CascadeType.ALL, CascadeType.REMOVE}, orphanRemoval = true)
    private List<ConsultationRequest> consultationRequests;

}
