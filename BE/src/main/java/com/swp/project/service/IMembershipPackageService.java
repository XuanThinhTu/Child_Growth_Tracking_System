package com.swp.project.service;

import com.paypal.base.rest.PayPalRESTException;
import com.swp.project.dto.request.MembershipPackageRequest;
import com.swp.project.dto.response.MembershipPackageResponse;
import com.swp.project.dto.response.MembershipSubscriptionResponse;
import com.swp.project.dto.response.PaymentDTO;

import java.util.List;

public interface IMembershipPackageService {
    MembershipPackageResponse createMembershipPackage(MembershipPackageRequest request);
    MembershipPackageResponse updateMembershipPackage(int id, MembershipPackageRequest request);
    void deleteMembershipPackage(int id);
    MembershipPackageResponse getMembershipPackageById(int id);
    List<MembershipPackageResponse> getAllMembershipPackages();
    void enableMembershipPackage(int id);
    void disableMembershipPackage(int id);
    PaymentDTO createMembershipPayment(int membershipId) throws PayPalRESTException;
    String executeMembershipPayment(String paymentId, String payerId) throws PayPalRESTException ;

    MembershipSubscriptionResponse getUserMembership();

    void cancelMembershipPayment(String token);
}
