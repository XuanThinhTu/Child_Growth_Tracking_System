package com.swp.project.service.Impl;


import com.swp.project.dto.request.FAQRequest;
import com.swp.project.dto.response.FAQResponse;
import com.swp.project.entity.Category;
import com.swp.project.entity.FAQ;
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

    @Override
    public FAQResponse createFAQ(FAQRequest request) {

        Category category = categoryRepository.findById(request.categoryId())

                .orElseThrow(() -> new IllegalArgumentException("Category with id: " + request.categoryId() + " not found"));
        FAQ faq = new FAQ();

        faq.setQuestion(request.question());

        faq.setAnswer(request.answer());

        faq.setCategory(category);

        faqRepository.save(faq);

        return faqMapper.toDto(faq);


    }


    @Override
    public FAQResponse updateFAQ(int fqaId, FAQRequest request) {
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category with id: " + request.categoryId() + " not found"));
        FAQ faq = faqRepository.findByIdAndIsDeletedFalse(fqaId)
                .orElseThrow(() -> new IllegalArgumentException("FAQ with id: " + fqaId + " not found"));
        faq.setQuestion(request.question());
        faq.setAnswer(request.answer());
        faq.setCategory(category);
        faqRepository.save(faq);
        return faqMapper.toDto(faq);
    }

    @Override
    public void deleteFAQ(int fqaId) {
        FAQ faq = faqRepository.findByIdAndIsDeletedFalse(fqaId)
                .orElseThrow(() -> new IllegalArgumentException("FAQ with id: " + fqaId + " not found"));
        faq.setDeleted(true);
        faqRepository.save(faq);
    }

}
