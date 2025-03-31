package com.swp.project.service;

import com.swp.project.dto.request.ConsultationResponseCreation;
import com.swp.project.dto.response.ConsultationResponseDTO;
import org.springframework.data.domain.Page;

public interface IConsultationResponseService {

    ConsultationResponseDTO addConsultationResponse(int consultationId, ConsultationResponseCreation response);

    Page<ConsultationResponseDTO> getConsultationResponsesByConsultationId(int consultationId, int page, int size);
}
