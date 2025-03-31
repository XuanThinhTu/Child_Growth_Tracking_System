package com.swp.project.repository;

import com.swp.project.entity.MembershipSubscription;
import com.swp.project.enums.MembershipSubscriptionStatus;
import com.swp.project.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MembershipSubscriptionRepository extends JpaRepository<MembershipSubscription, Integer> {

    @Query("SELECT m FROM MembershipSubscription m WHERE m.user.id = :userId AND m.paymentStatus = :paymentStatus AND m.status = :status ORDER BY m.id DESC LIMIT 1")
    MembershipSubscription findByUserIdAndPaymentStatusAndStatus(int userId, PaymentStatus paymentStatus, MembershipSubscriptionStatus status);

    Optional<MembershipSubscription> findByUserIdAndStatus(int userId, MembershipSubscriptionStatus status);

    List<MembershipSubscription> findAllByStatus(MembershipSubscriptionStatus status);
}
