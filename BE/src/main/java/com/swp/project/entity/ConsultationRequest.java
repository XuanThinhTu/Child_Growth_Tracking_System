package com.swp.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swp.project.enums.ConsultationStatus;
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
@Table(name = "consultation_request")
public class ConsultationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "request_title")
    private String requestTitle;
    @Column(name = "note")
    private String note;

    @Column(name = "request_date")
    private Date requestDate;
    @Column
    @Enumerated(EnumType.STRING)
    private ConsultationStatus status;

    @ManyToOne(cascade = CascadeType.ALL)
    private Children child;

    @ManyToOne(optional = false)
    @JoinColumn(name = "parent_id")
    private User parent;

    @ManyToOne(optional = true)
    @JoinColumn(name = "doctor_id")
    private User doctor;

    @JsonIgnore
    @OneToMany(mappedBy = "consultationRequest", cascade = {CascadeType.ALL, CascadeType.REMOVE})
    private List<ConsultationResponse> consultationResponses;
}
