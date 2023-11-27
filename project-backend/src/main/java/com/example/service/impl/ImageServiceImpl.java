package com.example.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.example.service.ImageService;
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
        MultiValueMap<String, MultipartFile> map = new LinkedMultiValueMap<>();
        map.add("file",file);
        HttpHeaders header = new HttpHeaders();
        // 需求需要传参为form-data格式
        header.setContentType(MediaType.MULTIPART_FORM_DATA);
        header.add("authorization",imageAuthorization);
        HttpEntity<MultiValueMap<String, MultipartFile>> httpEntity = new HttpEntity<>(map, header);
        JSONObject response = restTemplate.postForObject(imageGetUrl, httpEntity, JSONObject.class);
        System.out.println("response = " + response);
        return  "123";
    }

    @Override
    public String testUploadAvatar(File file, Integer id) {
        MultiValueMap<String, File> map = new LinkedMultiValueMap<>();
        map.add("file",file);
        HttpHeaders header = new HttpHeaders();
        // 需求需要传参为form-data格式
        header.setContentType(MediaType.MULTIPART_FORM_DATA);
        header.add("authorization",imageAuthorization);
        HttpEntity<MultiValueMap<String, File>> httpEntity = new HttpEntity<>(map, header);
        JSONObject response = restTemplate.postForObject(imageGetUrl, httpEntity, JSONObject.class);
        System.out.println("response = " + response);
        return  "123";
    }
}
