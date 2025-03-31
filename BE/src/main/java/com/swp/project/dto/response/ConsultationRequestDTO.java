package com.swp.project.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsultationRequestDTO {

    private int id;
    private String requestTitle;
    private String note;
    private Date requestDate;
    private Integer doctorId;
    private String doctorName;
    private String status;
    private ChildrenDTO child;

}
