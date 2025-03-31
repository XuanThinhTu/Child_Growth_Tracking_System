package com.swp.project.controller;

import com.swp.project.dto.response.ApiResponse;
import com.swp.project.service.IFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/import")
public class FileController {

    private final IFileService fileService;

    // Import file excel for under 5 years old
    @PostMapping
    public ApiResponse<String> importExcelFileForUnder5YearsOld(@RequestParam("file") MultipartFile file,
                                                                @RequestParam("isGreaterFiveYearsOld") boolean isGreaterFiveYearsOld,
                                                                @RequestParam("fileType") String fileType,
                                                                @RequestParam("gender") String gender){

        try {
            fileService.saveDataFromFile(file, isGreaterFiveYearsOld, fileType, gender);
            return ApiResponse.<String>builder()
                    .message("Import file successfully")
                    .data("Import file successfully")
                    .build();
        } catch (Exception e) {
            return ApiResponse.<String>builder()
                    .message("Import file failed")
                    .data("Import file failed")
                    .build();
        }
    }
}
