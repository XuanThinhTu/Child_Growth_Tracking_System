package com.swp.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrowthPredictionResponse {
    // Ngày (hoặc mốc thời gian) dự đoán
    private String predictedDate;
    // Dự đoán chiều cao (cm)
    private double predictedHeight;
    // Dự đoán cân nặng (gram)
    private double predictedWeight;
    // Dự đoán vòng đầu (cm)
    private double predictedHeadCircumference;
    // Dự đoán BMI
    private double predictedBmi;
}