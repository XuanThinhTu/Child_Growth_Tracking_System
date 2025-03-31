package com.swp.project.service;

import com.swp.project.dto.request.ConsultationRequestCreation;
import com.swp.project.dto.response.ConsultationRequestDTO;
import com.swp.project.enums.ConsultationStatus;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IConsultationRequestService {

    ConsultationRequestDTO createConsultationRequest(ConsultationRequestCreation request);

    Page<ConsultationRequestDTO> getPendingConsultationRequest(int page, int size);

    Page<ConsultationRequestDTO> getAllConsultation(int page, int size);

    Page<ConsultationRequestDTO> getAllConsultationRequest(int page, int size, ConsultationStatus status);

    ConsultationRequestDTO getConsultationRequestById(int id);

    ConsultationRequestDTO assignDoctor(int consultationRequestId, int doctorId);

    List<ConsultationRequestDTO> getAllConsultationRequestByUser();

    ConsultationRequestDTO cancelConsultationRequestByUser(int consultationRequestId);

    ConsultationRequestDTO closeConsultationRequestByUser(int consultationRequestId);
}
