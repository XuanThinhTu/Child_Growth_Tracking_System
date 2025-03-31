package com.swp.project.service.Impl;


import com.swp.project.dto.request.FeedbackRequest;
import com.swp.project.dto.response.FeedbackDTO;
import com.swp.project.entity.Feedback;
import com.swp.project.entity.User;
import com.swp.project.mapper.FeedbackMapper;
import com.swp.project.repository.FeedbackRepository;
import com.swp.project.repository.UserRepository;
import com.swp.project.service.IFeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class FeedbackService implements IFeedbackService {


    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;
    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    public FeedbackDTO createFeedback(FeedbackRequest request) {
        Feedback feedback = new Feedback();
        feedback.setRating(request.getRating());
        feedback.setCreatedAt(LocalDateTime.now());
        feedback.setComment(request.getComment());
        feedback.setFeedbackType(request.getFeedbackType());
        feedback.setMember(userService.getAuthenticatedUser());
        User doctor = userRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        feedback.setDoctor(doctor);
        feedbackRepository.save(feedback);
        return feedbackMapper.toFeedbackDTO(feedback);
    }

    @Override
    public Page<FeedbackDTO> getAllFeedbacks(int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size);

        Page<Feedback> feedbacks = feedbackRepository.findAll(pageRequest);

        return feedbacks.map(feedbackMapper::toFeedbackDTO);

    }











}
