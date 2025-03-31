package com.swp.project.repository;

import com.swp.project.entity.ConsultationRequest;
import com.swp.project.enums.ConsultationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ConsultationRequestRepository extends JpaRepository<ConsultationRequest, Integer> {

    Page<ConsultationRequest> findAllByStatus(ConsultationStatus status, Pageable pageable);

    List<ConsultationRequest> findByChildId(int childId);

    @Modifying
    @Transactional
    @Query("DELETE FROM ConsultationRequest cr WHERE cr.child.id = :childId")
    void deleteAllByChildId(@Param("childId") int childId);

    void deleteByChildId(int childId);

}
