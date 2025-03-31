package com.swp.project.mapper;

import com.swp.project.dto.request.MembershipPackageRequest;
import com.swp.project.dto.response.MembershipPackageResponse;
import com.swp.project.entity.MembershipPackage;
import com.swp.project.entity.Permission;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;


@Component
public class MembershipPackageMapper {

    // Chuyển từ entity sang DTO
    public MembershipPackageResponse toDto(MembershipPackage membershipPackage) {
        if (membershipPackage == null) {
            return null;
        }
        Set<String> permissions = membershipPackage.getPermissions()
                .stream()
                .map(Permission::getPermissionName)
                .collect(Collectors.toSet());
        return new MembershipPackageResponse(
                membershipPackage.getId(),
                membershipPackage.getName(),
                membershipPackage.getDescription(),
                membershipPackage.getPrice(),
                membershipPackage.getDuration(),
                membershipPackage.isEnable(),
                permissions
        );
    }

    // Chuyển từ request sang entity (tạo mới)
    public MembershipPackage toMembershipPackage(MembershipPackageRequest request) {
        if (request == null) {
            return null;
        }
        MembershipPackage membershipPackage = new MembershipPackage();
        membershipPackage.setName(request.name());
        membershipPackage.setDescription(request.description());
        membershipPackage.setPrice(request.price());
        membershipPackage.setDuration(request.duration());
        return membershipPackage;
    }
}

