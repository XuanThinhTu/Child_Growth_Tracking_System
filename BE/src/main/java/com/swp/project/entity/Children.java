package com.swp.project.entity;

import com.swp.project.validator.DobConstraint;
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
}
