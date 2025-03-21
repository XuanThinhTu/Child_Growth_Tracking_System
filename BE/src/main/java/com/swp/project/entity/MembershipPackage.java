package com.swp.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "membership_package")
public class MembershipPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private double price;
    private int duration;
    private boolean isEnable = true;
    private boolean isDeleted = false;

    @OneToMany(mappedBy = "membershipPackage")
    private List<MembershipSubscription> membershipSubscriptions;

    @ManyToMany
    private Set<Permission> permissions;
}
