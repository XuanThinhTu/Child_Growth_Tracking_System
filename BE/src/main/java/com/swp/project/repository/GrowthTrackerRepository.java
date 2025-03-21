package com.swp.project.repository;

import com.swp.project.entity.GrowthTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GrowthTrackerRepository extends JpaRepository<GrowthTracker, Integer> {
    List<GrowthTracker> findAllByChildrenIdAndDeletedFalse(int childrenId);

    @Modifying
    @Transactional
    @Query("DELETE FROM GrowthTracker g WHERE g.children.id = :childrenId")
    void deleteAllByChildrenId(@Param("childrenId") int childrenId);
}
