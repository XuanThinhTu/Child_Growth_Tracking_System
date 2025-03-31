package com.swp.project.service.Impl;

import com.swp.project.entity.BmiStandard;
import com.swp.project.enums.PeriodType;
import com.swp.project.repository.BmiStandardsRepository;
import com.swp.project.service.IFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService implements IFileService {

    private final BmiStandardsRepository bmiStandardsRepository;
    //under 5 years old
    private final String BMI_FOR_AGE = "bfa";
    private final String HEAD_CIRCUMFERENCE_FOR_AGE = "hcfa";
    private final String LENGTH_HEIGHT_FOR_AGE = "lhfa";
    private final String WEIGHT_FOR_AGE = "wfa"; // WEIGHT_FOR_AGE_5_TO_19
    //greater than 5 years old
    private final String BMI_FOR_AGE_5_TO_19 = "bmi";
    private final String HEIGHT_FOR_AGE_5_TO_19 = "hfa";

    @Override
    public void saveDataFromFile(MultipartFile file, boolean isGreaterFiveYearsOld, String fileType, String gender) {
        try {
//            String fileName = file.getOriginalFilename();
//            String dataType = fileName.substring(0, fileName.indexOf("-"));
//            String gender = fileName.substring(fileName.indexOf("-") + 1, fileName.indexOf("-") + 5);
            InputStream inputStream = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            if (!rows.hasNext()) {
                throw new RuntimeException("File is empty");
            }
            // Đọc header và ánh xạ các cột
            Row headerRow = rows.next();
            Map<String, Integer> columnIndexMap = new HashMap<>();
            for (Cell cell : headerRow) {
                columnIndexMap.put(cell.getStringCellValue(), cell.getColumnIndex());
            }
            // Các cột cần thiết
            List<String> requiredColumns = Arrays.asList(
                    "SD4neg", "SD3neg", "SD2neg", "SD1neg", "SD0", "SD1", "SD2", "SD3", "SD4"
            );
            // Kiểm tra nếu thiếu cột nào
            for (String column : requiredColumns) {
                if (!columnIndexMap.containsKey(column)) {
                    throw new RuntimeException("Missing column: " + column);
                }
            }
            List<BmiStandard> bmiStandardsToSave = new ArrayList<>();
            while (rows.hasNext()) {
                Row row = rows.next();

                // Lấy thông tin period và periodType
                Integer period = getIntegerCellValue(row.getCell(isGreaterFiveYearsOld ? columnIndexMap.get("Month") : columnIndexMap.get("Day")));
                PeriodType periodType = isGreaterFiveYearsOld ? PeriodType.MONTH : PeriodType.DAY;

                // Tạo đối tượng tạm thời từ file
                BmiStandard tempStandard = new BmiStandard();
                tempStandard.setGender(gender);
                tempStandard.setPeriod(period);
                tempStandard.setPeriodType(periodType);

                // Gán các trường theo dataType từ file
                switch (fileType) {
                    case WEIGHT_FOR_AGE -> {
                        tempStandard.setWeightNeg4Sd(getNumericCellValue(row.getCell(columnIndexMap.get("SD4neg"))));
                        tempStandard.setWeightNeg3Sd(getNumericCellValue(row.getCell(columnIndexMap.get("SD3neg"))));
                        tempStandard.setWeightNeg2Sd(getNumericCellValue(row.getCell(columnIndexMap.get("SD2neg"))));
                        tempStandard.setWeightNeg1Sd(getNumericCellValue(row.getCell(columnIndexMap.get("SD1neg"))));
                        tempStandard.setWeightMedian(getNumericCellValue(row.getCell(columnIndexMap.get("SD0"))));
                        tempStandard.setWeightPos1Sd(getNumericCellValue(row.getCell(columnIndexMap.get("SD1"))));
                        tempStandard.setWeightPos2Sd(getNumericCellValue(row.getCell(columnIndexMap.get("SD2"))));
                        tempStandard.setWeightPos3Sd(getNumericCellValue(row.getCell(columnIndexMap.get("SD3"))));
                        tempStandard.setWeightPos4Sd(getNumericCellValue(row.getCell(columnIndexMap.get("SD4"))));
                    }
                    case LENGTH_HEIGHT_FOR_AGE, HEIGHT_FOR_AGE_5_TO_19 -> {
                        tempStandard.setHeightNeg4Sd(getNumericCellValue(row.getCell(columnIndexMap.get("SD4neg"))));
                        tempStandard.setHeightNeg3Sd(getNumericCellValue(row.getCell(columnIndexMap.get("SD3neg"))));
                        tempStandard.setHeightNeg2Sd(getNumericCellValue(row.getCell(columnIndexMap.get("SD2neg"))));
                        tempStandard.setHeightNeg1Sd(getNumericCellValue(row.getCell(columnIndexMap.get("SD1neg"))));
                        tempStandard.setHeightMedian(getNumericCellValue(row.getCell(columnIndexMap.get("SD0"))));
                        tempStandard.setHeightPos1Sd(getNumericCellValue(row.getCell(columnIndexMap.get("SD1"))));
                        tempStandard.setHeightPos2Sd(getNumericCellValue(row.getCell(columnIndexMap.get("SD2"))));
                        tempStandard.setHeightPos3Sd(getNumericCellValue(row.getCell(columnIndexMap.get("SD3"))));
                        tempStandard.setHeightPos4Sd(getNumericCellValue(row.getCell(columnIndexMap.get("SD4"))));
                    }
                    case HEAD_CIRCUMFERENCE_FOR_AGE -> {
                        tempStandard.setHeadCircumferenceNeg4Sd(getNumericCellValue(row.getCell(columnIndexMap.get("SD4neg"))));
                        tempStandard.setHeadCircumferenceNeg3Sd(getNumericCellValue(row.getCell(columnIndexMap.get("SD3neg"))));
                        tempStandard.setHeadCircumferenceNeg2Sd(getNumericCellValue(row.getCell(columnIndexMap.get("SD2neg"))));
                        tempStandard.setHeadCircumferenceNeg1Sd(getNumericCellValue(row.getCell(columnIndexMap.get("SD1neg"))));
                        tempStandard.setHeadCircumferenceMedian(getNumericCellValue(row.getCell(columnIndexMap.get("SD0"))));
                        tempStandard.setHeadCircumferencePos1Sd(getNumericCellValue(row.getCell(columnIndexMap.get("SD1"))));
                        tempStandard.setHeadCircumferencePos2Sd(getNumericCellValue(row.getCell(columnIndexMap.get("SD2"))));
                        tempStandard.setHeadCircumferencePos3Sd(getNumericCellValue(row.getCell(columnIndexMap.get("SD3"))));
                        tempStandard.setHeadCircumferencePos4Sd(getNumericCellValue(row.getCell(columnIndexMap.get("SD4"))));
                    }
                    case BMI_FOR_AGE, BMI_FOR_AGE_5_TO_19 -> {
                        tempStandard.setBmiNeg4Sd(getNumericCellValue(row.getCell(columnIndexMap.get("SD4neg"))));
                        tempStandard.setBmiNeg3Sd(getNumericCellValue(row.getCell(columnIndexMap.get("SD3neg"))));
                        tempStandard.setBmiNeg2Sd(getNumericCellValue(row.getCell(columnIndexMap.get("SD2neg"))));
                        tempStandard.setBmiNeg1Sd(getNumericCellValue(row.getCell(columnIndexMap.get("SD1neg"))));
                        tempStandard.setBmiMedian(getNumericCellValue(row.getCell(columnIndexMap.get("SD0"))));
                        tempStandard.setBmiPos1Sd(getNumericCellValue(row.getCell(columnIndexMap.get("SD1"))));
                        tempStandard.setBmiPos2Sd(getNumericCellValue(row.getCell(columnIndexMap.get("SD2"))));
                        tempStandard.setBmiPos3Sd(getNumericCellValue(row.getCell(columnIndexMap.get("SD3"))));
                        tempStandard.setBmiPos4Sd(getNumericCellValue(row.getCell(columnIndexMap.get("SD4"))));
                    }
                    default -> throw new RuntimeException("Unknown data type: " + fileType);
                }

                // Kiểm tra xem record đã tồn tại chưa dựa vào các trường khóa (gender, period, periodType)
                Optional<BmiStandard> optionalExisting = bmiStandardsRepository.findByGenderAndPeriodAndPeriodType(gender, period, periodType);
                if (optionalExisting.isPresent()) {
                    BmiStandard existingRecord = updateBmiStandardDataIfExist(optionalExisting, fileType, tempStandard);
                    bmiStandardsToSave.add(existingRecord);
                } else {
                    // Nếu record chưa tồn tại thì thêm mới
                    bmiStandardsToSave.add(tempStandard);
                }
            }
            bmiStandardsRepository.saveAll(bmiStandardsToSave);
            workbook.close();

        } catch (Exception e) {
            throw new RuntimeException("Failed to import data: " + e.getMessage());
        }
    }

    private BmiStandard updateBmiStandardDataIfExist(Optional<BmiStandard> optionalExisting, String dataType, BmiStandard tempStandard) {
        BmiStandard existingRecord = optionalExisting.get();
        // Cập nhật các cột theo dataType nếu giá trị hiện tại đang là null
        if (dataType.equals(WEIGHT_FOR_AGE)) {
            if(existingRecord.getWeightNeg4Sd() == null)
                existingRecord.setWeightNeg4Sd(tempStandard.getWeightNeg4Sd());
            if(existingRecord.getWeightNeg3Sd() == null)
                existingRecord.setWeightNeg3Sd(tempStandard.getWeightNeg3Sd());
            if(existingRecord.getWeightNeg2Sd() == null)
                existingRecord.setWeightNeg2Sd(tempStandard.getWeightNeg2Sd());
            if(existingRecord.getWeightNeg1Sd() == null)
                existingRecord.setWeightNeg1Sd(tempStandard.getWeightNeg1Sd());
            if(existingRecord.getWeightMedian() == null)
                existingRecord.setWeightMedian(tempStandard.getWeightMedian());
            if(existingRecord.getWeightPos1Sd() == null)
                existingRecord.setWeightPos1Sd(tempStandard.getWeightPos1Sd());
            if(existingRecord.getWeightPos2Sd() == null)
                existingRecord.setWeightPos2Sd(tempStandard.getWeightPos2Sd());
            if(existingRecord.getWeightPos3Sd() == null)
                existingRecord.setWeightPos3Sd(tempStandard.getWeightPos3Sd());
            if(existingRecord.getWeightPos4Sd() == null)
                existingRecord.setWeightPos4Sd(tempStandard.getWeightPos4Sd());
        } else if (dataType.equals(LENGTH_HEIGHT_FOR_AGE) || dataType.equals(HEIGHT_FOR_AGE_5_TO_19)) {
            if(existingRecord.getHeightNeg4Sd() == null)
                existingRecord.setHeightNeg4Sd(tempStandard.getHeightNeg4Sd());
            if(existingRecord.getHeightNeg3Sd() == null)
                existingRecord.setHeightNeg3Sd(tempStandard.getHeightNeg3Sd());
            if(existingRecord.getHeightNeg2Sd() == null)
                existingRecord.setHeightNeg2Sd(tempStandard.getHeightNeg2Sd());
            if(existingRecord.getHeightNeg1Sd() == null)
                existingRecord.setHeightNeg1Sd(tempStandard.getHeightNeg1Sd());
            if(existingRecord.getHeightMedian() == null)
                existingRecord.setHeightMedian(tempStandard.getHeightMedian());
            if(existingRecord.getHeightPos1Sd() == null)
                existingRecord.setHeightPos1Sd(tempStandard.getHeightPos1Sd());
            if(existingRecord.getHeightPos2Sd() == null)
                existingRecord.setHeightPos2Sd(tempStandard.getHeightPos2Sd());
            if(existingRecord.getHeightPos3Sd() == null)
                existingRecord.setHeightPos3Sd(tempStandard.getHeightPos3Sd());
            if(existingRecord.getHeightPos4Sd() == null)
                existingRecord.setHeightPos4Sd(tempStandard.getHeightPos4Sd());
        } else if (dataType.equals(HEAD_CIRCUMFERENCE_FOR_AGE)) {
            if(existingRecord.getHeadCircumferenceNeg4Sd() == null)
                existingRecord.setHeadCircumferenceNeg4Sd(tempStandard.getHeadCircumferenceNeg4Sd());
            if(existingRecord.getHeadCircumferenceNeg3Sd() == null)
                existingRecord.setHeadCircumferenceNeg3Sd(tempStandard.getHeadCircumferenceNeg3Sd());
            if(existingRecord.getHeadCircumferenceNeg2Sd() == null)
                existingRecord.setHeadCircumferenceNeg2Sd(tempStandard.getHeadCircumferenceNeg2Sd());
            if(existingRecord.getHeadCircumferenceNeg1Sd() == null)
                existingRecord.setHeadCircumferenceNeg1Sd(tempStandard.getHeadCircumferenceNeg1Sd());
            if(existingRecord.getHeadCircumferenceMedian() == null)
                existingRecord.setHeadCircumferenceMedian(tempStandard.getHeadCircumferenceMedian());
            if(existingRecord.getHeadCircumferencePos1Sd() == null)
                existingRecord.setHeadCircumferencePos1Sd(tempStandard.getHeadCircumferencePos1Sd());
            if(existingRecord.getHeadCircumferencePos2Sd() == null)
                existingRecord.setHeadCircumferencePos2Sd(tempStandard.getHeadCircumferencePos2Sd());
            if(existingRecord.getHeadCircumferencePos3Sd() == null)
                existingRecord.setHeadCircumferencePos3Sd(tempStandard.getHeadCircumferencePos3Sd());
            if(existingRecord.getHeadCircumferencePos4Sd() == null)
                existingRecord.setHeadCircumferencePos4Sd(tempStandard.getHeadCircumferencePos4Sd());
        } else if (dataType.equals(BMI_FOR_AGE) || dataType.equals(BMI_FOR_AGE_5_TO_19)) {
            if(existingRecord.getBmiNeg4Sd() == null)
                existingRecord.setBmiNeg4Sd(tempStandard.getBmiNeg4Sd());
            if(existingRecord.getBmiNeg3Sd() == null)
                existingRecord.setBmiNeg3Sd(tempStandard.getBmiNeg3Sd());
            if(existingRecord.getBmiNeg2Sd() == null)
                existingRecord.setBmiNeg2Sd(tempStandard.getBmiNeg2Sd());
            if(existingRecord.getBmiNeg1Sd() == null)
                existingRecord.setBmiNeg1Sd(tempStandard.getBmiNeg1Sd());
            if(existingRecord.getBmiMedian() == null)
                existingRecord.setBmiMedian(tempStandard.getBmiMedian());
            if(existingRecord.getBmiPos1Sd() == null)
                existingRecord.setBmiPos1Sd(tempStandard.getBmiPos1Sd());
            if(existingRecord.getBmiPos2Sd() == null)
                existingRecord.setBmiPos2Sd(tempStandard.getBmiPos2Sd());
            if(existingRecord.getBmiPos3Sd() == null)
                existingRecord.setBmiPos3Sd(tempStandard.getBmiPos3Sd());
            if(existingRecord.getBmiPos4Sd() == null)
                existingRecord.setBmiPos4Sd(tempStandard.getBmiPos4Sd());
        }
        return existingRecord;
    }

    private Integer getIntegerCellValue(Cell cell) {
        if (cell == null) return null;
        if (cell.getCellType() == CellType.NUMERIC) {
            return (int) cell.getNumericCellValue();
        } else if (cell.getCellType() == CellType.STRING) {
            try {
                return Integer.parseInt(cell.getStringCellValue().trim());
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    private Double getNumericCellValue(Cell cell) {
        return (cell != null && cell.getCellType() == CellType.NUMERIC) ? cell.getNumericCellValue() : null;
    }
}
