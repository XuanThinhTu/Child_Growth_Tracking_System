package com.swp.project.service.Impl;


import com.swp.project.dto.response.FAQResponse;
import com.swp.project.mapper.FAQMapper;
import com.swp.project.repository.CategoryRepository;
import com.swp.project.repository.FAQRepository;
import com.swp.project.service.IFAQService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class FAQService implements IFAQService {

    private final FAQRepository faqRepository;
    private final CategoryRepository categoryRepository;
    private final FAQMapper faqMapper;




    @Override
    public List<FAQResponse> getAllFAQ() {

        return faqRepository.findAllByIsDeletedFalse()

                .stream()

                .map(faqMapper::toDto)

                .collect(Collectors.toList());


    }





}
