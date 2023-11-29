package com.example;

import com.example.entity.vo.TopicDetailVo;
import com.example.entity.vo.TopicPreviewVo;
import com.example.service.ImageService;
import com.example.service.TopicService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@SpringBootTest
class ProjectBackendApplicationTests {

    @Test
    void contextLoads() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("123456"));
    }

    @Resource
    ImageService imageService;

    @Test
    void upload() {
        String path = "C:/Users/14229/Desktop/JavaWeb实验/bookApp4/web/cover/1696788102206.png";
        File file = new File(path);

        imageService.testUploadAvatar(file,1);
    }

    @Resource
    TopicService topicService;
    @Test
    void testTopics(){
    }


}
