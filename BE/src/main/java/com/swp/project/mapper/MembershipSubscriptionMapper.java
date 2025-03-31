package com.swp.project.mapper;

import com.swp.project.dto.response.MembershipSubscriptionResponse;
import com.swp.project.entity.MembershipSubscription;
import org.springframework.stereotype.Component;

@Component
public class MembershipSubscriptionMapper {

    public MembershipSubscriptionResponse toMembershipSubscriptionResponse(MembershipSubscription membershipSubscription) {
        return new MembershipSubscriptionResponse(
                membershipSubscription.getId(),
                membershipSubscription.getMembershipPackage().getId(),
                membershipSubscription.getMembershipPackage().getName(),
                membershipSubscription.getMembershipPackage().getDescription(),
                membershipSubscription.getMembershipPackage().getPrice(),
                membershipSubscription.getMembershipPackage().getDuration(),
                membershipSubscription.getStartDate(),
                membershipSubscription.getEndDate(),
                membershipSubscription.getStatus().name()
        );
    }
}
