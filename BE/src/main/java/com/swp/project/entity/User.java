package com.swp.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Email is required")
    @Email(message = "Email is invalid")
    @Column(unique = true, nullable = false, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    private String email;
    @JsonIgnore
    @NotEmpty(message = "Password is required")
    private String password;
    @NotEmpty(message = "First name is required")
    private String firstName;
    @NotEmpty(message = "Last name is required")
    private String lastName;
    private String phone;
    private String address;
    private String avatar;
    private boolean isActive;
    private boolean isBanned;
    @ManyToOne
    private Role role;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Children> children;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<MembershipSubscription> membershipSubscriptions;

    @JsonIgnore
    @OneToMany(mappedBy = "doctor")
    private List<WorkingSchedule> workingSchedules;

    @JsonIgnore
    @OneToMany(mappedBy = "doctor")
    private List<Booking> doctorBookings;

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Booking> memberBookings;

    @JsonIgnore
    @OneToMany(mappedBy = "parent")
    private List<ConsultationRequest> consultationRequestsAsParent;

    @JsonIgnore
    @OneToMany(mappedBy = "doctor")
    private List<ConsultationRequest> consultationRequestsAsDoctor;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<ConsultationResponse> consultationResponses;

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Feedback> memberFeedbacks;

    @JsonIgnore
    @OneToMany(mappedBy = "doctor")
    private List<Feedback> doctorFeedbacks;

}
