package com.swp.project.service.Impl;

import com.swp.project.dto.response.StandardIndexResponse;
import com.swp.project.enums.PeriodType;
import com.swp.project.mapper.StandardIndexMapper;
import com.swp.project.repository.BmiStandardsRepository;
import com.swp.project.service.IStandardIndexService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.swp.project.util.Converter.convertToDouble;

@Service
@RequiredArgsConstructor
public class StandardIndexService implements IStandardIndexService {

    private final BmiStandardsRepository bmiStandardsRepository;

    private final StandardIndexMapper standardIndexMapper;

    @Cacheable("standards")
    @Override
    public List<StandardIndexResponse> getALLStandardIndex() {

        return bmiStandardsRepository.findAll()
                .stream()
                .map(standardIndexMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<StandardIndexResponse> getStandardIndexStoredProcedure() {
        List<Object[]> results = bmiStandardsRepository.getAllBmiStandards();
        List<StandardIndexResponse> dtos = new ArrayList<>();

        for (Object[] row : results) {
            // Lưu ý: cần đảm bảo thứ tự các cột trả về từ stored procedure trùng khớp với thứ tự trong DTO.
            StandardIndexResponse dto = new StandardIndexResponse(
                    row[0] != null ? (Long) row[0] : null,      // id
                    row[10] != null ? (String) row[10] : null,                                             // gender
                    row[29] != null ? (Integer) row[29] : null,         // period
                    row[30] != null ? PeriodType.valueOf((String) row[30]) : null,    // periodType

                    // Weight: mapping theo thứ tự đã định nghĩa
                    convertToDouble(row[35]), // weightNeg4Sd
                    convertToDouble(row[34]), // weightNeg3Sd
                    convertToDouble(row[33]), // weightNeg2Sd
                    convertToDouble(row[32]), // weightNeg1Sd
                    convertToDouble(row[31]), // weightMedian
                    convertToDouble(row[36]), // weightPos1Sd
                    convertToDouble(row[37]), // weightPos2Sd
                    convertToDouble(row[38]), // weightPos3Sd
                    convertToDouble(row[39]), // weightPos4Sd

                    // Height: mapping theo thứ tự đã định nghĩa
                    convertToDouble(row[24]), // heightNeg4Sd
                    convertToDouble(row[23]), // heightNeg3Sd
                    convertToDouble(row[22]), // heightNeg2Sd
                    convertToDouble(row[21]), // heightNeg1Sd
                    convertToDouble(row[20]), // heightMedian
                    convertToDouble(row[25]), // heightPos1Sd
                    convertToDouble(row[26]), // heightPos2Sd
                    convertToDouble(row[27]), // heightPos3Sd
                    convertToDouble(row[28]), // heightPos4Sd

                    // BMI: mapping theo thứ tự đã định nghĩa
                    convertToDouble(row[5]),  // bmiNeg4Sd
                    convertToDouble(row[4]),  // bmiNeg3Sd
                    convertToDouble(row[3]),  // bmiNeg2Sd
                    convertToDouble(row[2]),  // bmiNeg1Sd
                    convertToDouble(row[1]),  // bmiMedian
                    convertToDouble(row[6]),  // bmiPos1Sd
                    convertToDouble(row[7]),  // bmiPos2Sd
                    convertToDouble(row[8]),  // bmiPos3Sd
                    convertToDouble(row[9]),  // bmiPos4Sd

                    // Head circumference: mapping theo thứ tự đã định nghĩa
                    convertToDouble(row[15]), // headCircumferenceNeg4Sd
                    convertToDouble(row[14]), // headCircumferenceNeg3Sd
                    convertToDouble(row[13]), // headCircumferenceNeg2Sd
                    convertToDouble(row[12]), // headCircumferenceNeg1Sd
                    convertToDouble(row[11]), // headCircumferenceMedian
                    convertToDouble(row[16]), // headCircumferencePos1Sd
                    convertToDouble(row[17]), // headCircumferencePos2Sd
                    convertToDouble(row[18]), // headCircumferencePos3Sd
                    convertToDouble(row[19])  // headCircumferencePos4Sd
            );
            dtos.add(dto);
        }

        return dtos;
    }
}
