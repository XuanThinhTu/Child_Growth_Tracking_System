package com.swp.project.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PaymentDTO {
    private LocalDateTime createdTime;
    private String paymentUrl;
}
