package com.example.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface ImageService {
    String uploadAvatar(MultipartFile file, Integer id);
    String testUploadAvatar(File file, Integer id);

}
