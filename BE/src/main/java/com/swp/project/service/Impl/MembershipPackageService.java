package com.swp.project.service.Impl;

import com.swp.project.dto.request.MembershipPackageRequest;
import com.swp.project.dto.response.MembershipPackageResponse;
import com.swp.project.entity.MembershipPackage;
import com.swp.project.exception.ResourceNotFoundException;
import com.swp.project.mapper.MembershipPackageMapper;
import com.swp.project.repository.MembershipPackageRepository;
import com.swp.project.service.IMembershipPackageService;
import com.swp.project.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MembershipPackageService implements IMembershipPackageService {
    private final MembershipPackageRepository packageRepository;
    private final MembershipPackageMapper packageMapper;
    private final IUserService userService;
    private final String CURRENCY = "USD";
    private final String METHOD = "paypal";
    private final String INTENT = "sale";

    @Override
    public MembershipPackageResponse updateMembershipPackage(int id,MembershipPackageRequest request) {
        MembershipPackage membershipPackage = packageRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new ResourceNotFoundException("Package with id " + id + " not found"));
        membershipPackage.setName(request.name());
        membershipPackage.setDescription(request.description());
        membershipPackage.setPrice(request.price());
        membershipPackage.setDuration(request.duration());
        membershipPackage = packageRepository.save(membershipPackage);
        return packageMapper.toDto(membershipPackage);
    }

    @Override
    public void deleteMembershipPackage(int id) {
        MembershipPackage membershipPackage = packageRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Membership Package id: " + id + " not found"));
        membershipPackage.setDeleted(true);
        packageRepository.save(membershipPackage);
    }

    @Override
    public MembershipPackageResponse getMembershipPackageById(int id) {
        MembershipPackage membershipPackage = packageRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Membership Package id: " + id + " not found"));
        return packageMapper.toDto(membershipPackage);
    }

    @Override
    public List<MembershipPackageResponse> getAllMembershipPackages() {
        return packageRepository.findAllByIsDeletedFalse()
                .stream()
                .map(packageMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void enableMembershipPackage(int id) {
        MembershipPackage membershipPackage = packageRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Membership Package id: " + id + " not found"));
        membershipPackage.setEnable(true);
        packageRepository.save(membershipPackage);
    }

    @Override
    public void disableMembershipPackage(int id) {
        MembershipPackage membershipPackage = packageRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Membership Package id: " + id + " not found"));
        membershipPackage.setEnable(false);
        packageRepository.save(membershipPackage);
    }

}
