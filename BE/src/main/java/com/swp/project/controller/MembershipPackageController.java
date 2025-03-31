package com.swp.project.controller;

import com.swp.project.dto.request.MembershipPackageRequest;
import com.swp.project.dto.response.ApiResponse;
import com.swp.project.dto.response.MembershipPackageResponse;
import com.swp.project.service.Impl.MembershipPackageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/membership-package")
@RequiredArgsConstructor
public class MembershipPackageController {
    private final MembershipPackageService membershipPackageService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<?>> getAllMembershipPackages() {
        List<MembershipPackageResponse> list = membershipPackageService.getAllMembershipPackages();
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Membership Package listed successfully")
                        .data(list)
                        .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<?>> getMembershipPackagesById(@PathVariable int id) {
        MembershipPackageResponse membershipPackage = membershipPackageService.getMembershipPackageById(id);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Membership Package detail")
                        .data(membershipPackage)
                        .build()
        );
    }


    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<?>> updateMembershipPackagesById(
            @PathVariable int id,
            @RequestBody MembershipPackageRequest request
    ) {
        MembershipPackageResponse membershipPackage = membershipPackageService.updateMembershipPackage(id, request);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Membership Package updated successfully")
                        .data(membershipPackage)
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<?>> deleteMembershipPackagesById(
            @PathVariable int id
    ) {
        membershipPackageService.deleteMembershipPackage(id);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Membership Package deleted successfully")
                        .build()
        );
    }

    @PutMapping("/enable/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<?>> enableMembershipPackagesById(
            @PathVariable int id
    ) {
        membershipPackageService.enableMembershipPackage(id);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Membership Package enable successfully")
                        .build()
        );
    }

    @PutMapping("/disable/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<ApiResponse<?>> disableMembershipPackagesById(
            @PathVariable int id
    ) {
        membershipPackageService.disableMembershipPackage(id);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Membership Package disable successfully")
                        .build()
        );
    }

}
