package com.swp.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

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
    private String requestDetail;
    private Date requestDate;
    private String responseDetail;
    private Date responseDate;
    @ManyToOne(optional = false)
    private User parent;
    @ManyToOne(optional = true)
    private User doctor;
}
