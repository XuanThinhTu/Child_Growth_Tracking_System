package com.swp.project.service;

import org.springframework.web.multipart.MultipartFile;

public interface IFileService {

    void saveDataFromFile(MultipartFile file, boolean isGreaterFiveYearsOld, String fileType, String gender);
}
