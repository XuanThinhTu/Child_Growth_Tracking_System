package com.swp.project.repository;

import com.swp.project.entity.ConsultationResponse;
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
public interface ConsultationResponseRepository extends JpaRepository<ConsultationResponse, Integer> {

    Page<ConsultationResponse> findByConsultationRequestId(int consultationRequestId, Pageable pageable);

    List<ConsultationResponse> findByConsultationRequestId(int consultationRequestId);

    @Modifying
    @Transactional
    @Query("DELETE FROM ConsultationResponse cr WHERE cr.consultationRequest.id = :consultationRequestId")
    void deleteAllByConsultationRequestId(@Param("consultationRequestId") int consultationRequestId);
}


