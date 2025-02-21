package com.swp.project.repository;

import com.swp.project.entity.Children;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChildrenRepository extends JpaRepository<Children, Integer> {
    List<Children> findByUserId(int userId);
}
