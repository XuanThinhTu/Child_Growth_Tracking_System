package com.swp.project.repository;

import com.swp.project.entity.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FAQRepository extends JpaRepository<FAQ, Integer> {
    Optional<FAQ> findByIdAndIsDeletedFalse(int id);

    List<FAQ> findAllByIsDeletedFalse();
}
