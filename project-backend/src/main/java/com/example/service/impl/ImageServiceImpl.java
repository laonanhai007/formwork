package com.example.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.example.service.ImageService;
import com.example.util.FileUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService {
    @Value("${image.get-url}")
    String imageGetUrl;

    @Value("${image.authorization}")
    String imageAuthorization;

    @Resource
    RestTemplate restTemplate;

    @Override
    public String uploadAvatar(MultipartFile file, Integer id) {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        File file1;
        try {
            file1 = FileUtil.MultipartFileToFile(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        map.set("file", file1);
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", imageAuthorization);
        header.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(map, header);
        JSONObject response = restTemplate.postForObject(imageGetUrl, httpEntity, JSONObject.class);
        System.out.println("response = " + response);
        System.out.println(1111);
        return "123";
    }

    @Override
    public String testUploadAvatar(File file, Integer id) {
        return null;
    }
}
