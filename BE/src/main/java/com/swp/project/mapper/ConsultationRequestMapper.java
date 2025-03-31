package com.swp.project.mapper;

import com.swp.project.dto.response.ConsultationRequestDTO;
import com.swp.project.entity.ConsultationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConsultationRequestMapper {

    private final ChildrenMapper childrenMapper;

    public ConsultationRequestDTO toConsultationRequestDTO(ConsultationRequest request) {
        ConsultationRequestDTO consultationRequestDTO = new ConsultationRequestDTO();
        consultationRequestDTO.setId(request.getId());
        consultationRequestDTO.setRequestTitle(request.getRequestTitle());
        consultationRequestDTO.setNote(request.getNote());
        consultationRequestDTO.setRequestDate(request.getRequestDate());
        consultationRequestDTO.setChild(childrenMapper.toChildrenDTO(request.getChild()));
        consultationRequestDTO.setStatus(request.getStatus().name());
        if(request.getDoctor() != null) {
            consultationRequestDTO.setDoctorId(request.getDoctor().getId());
            consultationRequestDTO.setDoctorName(request.getDoctor().getFirstName() + " " + request.getDoctor().getLastName());
        }
        return consultationRequestDTO;
    }
}
