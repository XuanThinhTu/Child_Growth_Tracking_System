package com.swp.project.controller;

import com.swp.project.dto.request.FAQRequest;
import com.swp.project.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.swp.project.service.Impl.FAQService;

@CrossOrigin("*")
@RequestMapping("/faq")
@RestController
@RequiredArgsConstructor
public class FAQController {

    private final FAQService faqService;
    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllFAQs() {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("List of FAQs")
                        .data(faqService.getAllFAQ())
                        .build()
        );
    }

    @PostMapping
//    @SecurityRequirement(name = "bearerAuth")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<?>> createFAQs(@RequestBody FAQRequest request) {

        return ResponseEntity.ok(

                ApiResponse.builder()

                        .message("List of FAQs")

                        .data(faqService.createFAQ(request))

                        .build()

        );
    }


}
