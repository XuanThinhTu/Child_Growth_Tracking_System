package com.swp.project.service;


import com.swp.project.dto.request.FeedbackRequest;
import com.swp.project.dto.response.FeedbackDTO;
import org.springframework.data.domain.Page;

public interface IFeedbackService {

    FeedbackDTO createFeedback(FeedbackRequest request);
    Page<FeedbackDTO> getAllFeedbacks(int page, int size);











}
