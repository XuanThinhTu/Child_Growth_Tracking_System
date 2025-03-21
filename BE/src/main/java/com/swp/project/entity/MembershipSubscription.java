package com.swp.project.entity;

import com.swp.project.enums.MembershipSubscriptionStatus;
import com.swp.project.enums.PaymentStatus;
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
@Table(name = "membership_subscription")
public class MembershipSubscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date startDate;
    private Date endDate;
    @Enumerated(EnumType.STRING)
    private MembershipSubscriptionStatus status;
    private Date createdAt;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @ManyToOne
    private MembershipPackage membershipPackage;

    @ManyToOne
    private User user;
}
