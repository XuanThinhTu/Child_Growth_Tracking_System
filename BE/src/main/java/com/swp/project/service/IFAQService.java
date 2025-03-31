package com.swp.project.service;

import com.swp.project.dto.request.FAQRequest;
import com.swp.project.dto.response.FAQResponse;
import java.util.List;

public interface IFAQService {

    List<FAQResponse> getAllFAQ();
    FAQResponse createFAQ(FAQRequest request);

}
