package com.swp.project.service;

import com.swp.project.dto.request.MembershipPackageRequest;
import com.swp.project.dto.response.MembershipPackageResponse;

import java.util.List;

public interface IMembershipPackageService {
    MembershipPackageResponse updateMembershipPackage(int id, MembershipPackageRequest request);
    void deleteMembershipPackage(int id);
    MembershipPackageResponse getMembershipPackageById(int id);
    List<MembershipPackageResponse> getAllMembershipPackages();
    void enableMembershipPackage(int id);
    void disableMembershipPackage(int id);
}
