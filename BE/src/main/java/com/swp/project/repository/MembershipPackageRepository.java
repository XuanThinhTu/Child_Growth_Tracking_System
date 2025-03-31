package com.swp.project.repository;

import com.swp.project.entity.MembershipPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MembershipPackageRepository extends JpaRepository<MembershipPackage, Integer> {
    // Lấy tất cả các gói chưa bị xóa
    List<MembershipPackage> findAllByIsDeletedFalse();

    // Lấy một gói theo id nếu chưa bị xóa
    Optional<MembershipPackage> findByIdAndIsDeletedFalse(Integer id);
}
