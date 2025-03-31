package com.swp.project.service.Impl;

import com.swp.project.dto.response.ConsultationResponseCreation;
import com.swp.project.dto.response.ConsultationResponseDTO;
import com.swp.project.entity.ConsultationRequest;
import com.swp.project.entity.ConsultationResponse;
import com.swp.project.entity.User;
import com.swp.project.enums.ConsultationStatus;
import com.swp.project.mapper.ConsultationResponseMapper;
import com.swp.project.repository.ConsultationRequestRepository;
import com.swp.project.repository.ConsultationResponseRepository;
import com.swp.project.service.IConsultationResponseService;
import com.swp.project.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ConsultationResponseService implements IConsultationResponseService {

    private final ConsultationRequestRepository consultationRequestRepository;
    private final ConsultationResponseRepository consultationResponseRepository;
    private final IUserService userService;
    private final ConsultationResponseMapper consultationResponseMapper;

    @Override
    public ConsultationResponseDTO addConsultationResponse(int consultationId, ConsultationResponseCreation response) {
        ConsultationRequest consultationRequest = consultationRequestRepository.findById(consultationId)
                .orElseThrow(() -> new RuntimeException("Consultation request not found"));
        User user = userService.getAuthenticatedUser();
        if (user.getId() == consultationRequest.getParent().getId() || user.getId() == consultationRequest.getDoctor().getId()) {
            if (consultationRequest.getConsultationResponses().isEmpty()) {
                consultationRequest.setStatus(ConsultationStatus.PROCESSING);
                consultationRequestRepository.save(consultationRequest);
            }
            ConsultationResponse consultationResponse = new ConsultationResponse();
            consultationResponse.setUser(user);
            consultationResponse.setContent(response.getContent());
            consultationResponse.setConsultationRequest(consultationRequest);
            consultationResponse.setCreatedAt(LocalDateTime.now());
            consultationResponse = consultationResponseRepository.save(consultationResponse);

            return consultationResponseMapper.toConsultationResponseDTO(consultationResponse);
        }else{
            throw new RuntimeException("You are not allowed to add response to this consultation request");
        }
    }

    @Override
    public Page<ConsultationResponseDTO> getConsultationResponsesByConsultationId(int consultationId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ConsultationResponse> responses = consultationResponseRepository.findByConsultationRequestId(consultationId,pageable);
        return responses.map(consultationResponseMapper::toConsultationResponseDTO);
    }
}
