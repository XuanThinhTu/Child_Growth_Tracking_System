package com.swp.project.repository;

import com.swp.project.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByIdAndIsDeletedFalse(int id);
    List<Category> findAllByIsDeletedFalse();
}
