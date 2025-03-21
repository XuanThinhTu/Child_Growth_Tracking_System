package com.swp.project.service.Impl;

import com.swp.project.dto.request.GrowTrackerRequest;
import com.swp.project.dto.response.GrowthPredictionResponse;
import com.swp.project.dto.response.GrowthTrackerResponse;
import com.swp.project.entity.Children;
import com.swp.project.entity.GrowthTracker;
import com.swp.project.exception.ResourceNotFoundException;
import com.swp.project.mapper.GrowthTrackerMapper;
import com.swp.project.repository.ChildrenRepository;
import com.swp.project.repository.GrowthTrackerRepository;
import com.swp.project.service.IGrowthTrackerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GrowthTrackerService implements IGrowthTrackerService {

    private final GrowthTrackerRepository growthTrackerRepository;

    private final GrowthTrackerMapper growthTrackerMapper;

    private final ChildrenRepository childrenRepository;

    @Override
    public GrowthTrackerResponse addGrowthTracker(int childId ,GrowTrackerRequest growTrackerRequest) {
        Children children = childrenRepository.findById(childId).orElseThrow(() -> new ResourceNotFoundException("Children id: " + childId + " not found"));
        Date measureDate = Date.valueOf(growTrackerRequest.measuredAt());

        if (children.getBirthDate().after(measureDate) || measureDate.after(Date.valueOf(LocalDate.now()))) {
            // Nghĩa là birthDate > measureDate
            throw new ResourceNotFoundException("Measured date must be after birth date");
        }

        GrowthTracker growthTracker = new GrowthTracker();
        growthTracker.setWeight(growTrackerRequest.weight());
        growthTracker.setHeight(growTrackerRequest.height());
        growthTracker.setHeadCircumference(growTrackerRequest.headCircumference());
        // Tính BMI với đơn vị chuyển đổi đúng
        double weightKg = growTrackerRequest.weight() / 1000.0;
        double heightM = growTrackerRequest.height() / 100.0;
        double bmi = weightKg / (heightM * heightM);

        // Làm tròn BMI về 2 chữ số thập phân
        double bmiRounded = Math.round(bmi * 100.0 * 100.0) / 100.0;
        growthTracker.setBmi(bmiRounded);
        growthTracker.setMeasuredAt(measureDate.toLocalDate().atStartOfDay());
        growthTracker.setChildren(children);
        growthTrackerRepository.save(growthTracker);
        return growthTrackerMapper.toDto(growthTracker);
    }

    @Override
    public GrowthTrackerResponse updateGrowthTracker(int id, GrowTrackerRequest growTrackerRequest) {
        GrowthTracker growthTracker = growthTrackerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Growth Tracker id: " + id + " not found"));
        growthTracker.setWeight(growTrackerRequest.weight());
        growthTracker.setHeight(growTrackerRequest.height());
        growthTracker.setHeadCircumference(growTrackerRequest.headCircumference());
        // Tính BMI với đơn vị chuyển đổi đúng
        double weightKg = growTrackerRequest.weight() / 1000.0;
        double heightM = growTrackerRequest.height() / 100.0;
        double bmi = weightKg / (heightM * heightM);

        // Làm tròn BMI về 2 chữ số thập phân
        double bmiRounded = Math.round(bmi * 100.0 * 100.0) / 100.0;
        growthTracker.setBmi(bmiRounded);
        growthTracker.setMeasuredAt(LocalDate.parse(growTrackerRequest.measuredAt()).atStartOfDay());
        growthTrackerRepository.save(growthTracker);
        return growthTrackerMapper.toDto(growthTracker);
    }

    @Override
    public void deleteGrowthTracker(int id) {
        GrowthTracker growthTracker = growthTrackerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Growth Tracker id: " + id + " not found"));
        growthTracker.setDeleted(true);
        growthTrackerRepository.save(growthTracker);
    }

    @Override
    public List<GrowthTrackerResponse> showAllGrowthTracker(int childId) {
        Children children = childrenRepository.findById(childId).orElseThrow(() -> new ResourceNotFoundException("Children id: " + childId + " not found"));
        return growthTrackerRepository.findAllByChildrenIdAndDeletedFalse(childId)
                .stream()
                .map(growthTrackerMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Phương thức dự đoán n mốc tiếp theo dựa trên thời gian đo thực tế (tính ngày từ lúc sinh).
     *  - Lấy dữ liệu lịch sử (đã sort theo measuredAt).
     *  - Tính x = số ngày từ birthDate -> measuredAt.
     *  - Hồi quy tuyến tính cho height, weight, headCircumference.
     *  - Tính khoảng cách trung bình giữa các lần đo (theo ngày).
     *  - Dự đoán n mốc kế tiếp: xNext = xLast + i * avgGap (i=1..n).
     *  - Tính ra predictedHeight, predictedWeight, predictedHeadCirc, predictedBmi cho mỗi mốc.
     *  - Trả về danh sách GrowthPredictionResponse.
     */
    public List<GrowthPredictionResponse> predictNextNPoints(int childId, int n) {
        // 1. Lấy thông tin Children
        Children child = childrenRepository.findById(childId)
                .orElseThrow(() -> new ResourceNotFoundException("Children id: " + childId + " not found"));

        // 2. Lấy tất cả GrowthTracker (chưa xóa) theo childId, sắp xếp theo ngày đo
        List<GrowthTracker> trackers = growthTrackerRepository.findAllByChildrenIdAndDeletedFalse(childId);
        if (trackers.size() < 2) {
            throw new RuntimeException("Không đủ dữ liệu để dự đoán (cần >= 2 lần đo).");
        }
        trackers.sort(Comparator.comparing(GrowthTracker::getMeasuredAt));

        // 3. Chuẩn bị dữ liệu: x = ngày từ (birthDate) đến (measuredAt)
        long birthTime = child.getBirthDate().getTime(); // ms
        List<Double> xDays = new ArrayList<>();
        List<Double> heights = new ArrayList<>();
        List<Double> weights = new ArrayList<>();
        List<Double> heads   = new ArrayList<>();

        for (GrowthTracker gt : trackers) {
            long measureTime = gt.getMeasuredAt().atZone(ZoneId.systemDefault())
                    .toInstant()
                    .toEpochMilli(); // ms
            // Số ngày (double) giữa measuredAt và birthDate
            double dayDiff = (measureTime - birthTime) / (1000.0 * 60 * 60 * 24);
            xDays.add(dayDiff);

            // Lưu height, weight, headCircumference
            heights.add(gt.getHeight() == null ? 0.0 : gt.getHeight());
            weights.add(gt.getWeight() == null ? 0.0 : gt.getWeight());
            heads.add(gt.getHeadCircumference() == null ? 0.0 : gt.getHeadCircumference());
        }

        // 4. Hồi quy tuyến tính cho height, weight, headCircumference
        double[] heightCoeffs = simpleLinearRegression(xDays, heights); // {a, b}
        double[] weightCoeffs = simpleLinearRegression(xDays, weights); // {a, b}
        double[] headCoeffs   = simpleLinearRegression(xDays, heads);   // {a, b}

        // 5. Tính khoảng cách trung bình (theo ngày) giữa các lần đo
        double totalGap = 0;
        for (int i = 1; i < xDays.size(); i++) {
            totalGap += (xDays.get(i) - xDays.get(i - 1));
        }
        double avgGap = totalGap / (xDays.size() - 1); // trung bình số ngày giữa 2 lần đo
        if (avgGap < 1) {
            // Nếu trung bình < 1 ngày, tùy bạn xử lý
            avgGap = 1;
        }

        // 6. Lấy xLast = xDays cuối cùng
        double xLast = xDays.getLast();

        // Tính luôn ngày đo cuối cùng để cộng dồn ra ngày tương lai
        LocalDateTime lastMeasuredDate = trackers.getLast().getMeasuredAt();
        LocalDate lastMeasuredLocalDate = lastMeasuredDate.toLocalDate();

        // 7. Tạo list kết quả
        List<GrowthPredictionResponse> predictionList = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            // xNext = xLast + i * avgGap
            double xNext = xLast + i * avgGap;

            // Dự đoán height, weight, headCirc
            double predictedHeight = heightCoeffs[0] + heightCoeffs[1] * xNext;
            double predictedWeight = weightCoeffs[0] + weightCoeffs[1] * xNext;
            double predictedHead   = headCoeffs[0]   + headCoeffs[1]   * xNext;

            // Tính BMI = (cân nặng(kg)) / (chiều cao(m))^2
            // Nếu weight đang là gram -> chuyển sang kg
            double weightKg = predictedWeight / 1000.0;
            // Nếu height đang là cm -> chuyển sang m
            double heightM = predictedHeight / 100.0;
            double predictedBmi = 0.0;
            if (heightM > 0) {
                predictedBmi = weightKg / (heightM * heightM);
            }

            // Làm tròn
            predictedHeight = Math.round(predictedHeight * 100.0) / 100.0;
            predictedWeight = Math.round(predictedWeight * 100.0) / 100.0;
            predictedHead   = Math.round(predictedHead   * 100.0) / 100.0;
            predictedBmi    = Math.round(predictedBmi    * 100.0) / 100.0;

            // Dự tính ngày đo tiếp theo (theo logic: lastMeasuredDate + i * avgGap)
            // avgGap là double (có thể không nguyên), ta làm tròn hoặc giữ nguyên tùy ý
            long daysToAdd = Math.round(i * avgGap);
            LocalDate futureDate = lastMeasuredLocalDate.plusDays(daysToAdd);

            GrowthPredictionResponse gpr = new GrowthPredictionResponse();
            gpr.setPredictedDate(futureDate.toString());
            gpr.setPredictedHeight(predictedHeight);
            gpr.setPredictedWeight(predictedWeight);
            gpr.setPredictedHeadCircumference(predictedHead);
            gpr.setPredictedBmi(predictedBmi);

            predictionList.add(gpr);
        }

        return predictionList;
    }

    /**
     * Hồi quy tuyến tính đơn giản (Simple Linear Regression) cho 1 biến:
     *   y = a + b*x
     * Trả về mảng [a, b].
     */
    private double[] simpleLinearRegression(List<Double> xData, List<Double> yData) {
        int n = xData.size();
        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;

        for (int i = 0; i < n; i++) {
            double x = xData.get(i);
            double y = yData.get(i);
            sumX += x;
            sumY += y;
            sumXY += x * y;
            sumX2 += x * x;
        }

        double b = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX);
        double a = (sumY - b * sumX) / n;
        return new double[]{a, b};
    }
}
