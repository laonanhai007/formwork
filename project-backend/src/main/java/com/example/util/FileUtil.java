package com.example.util;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Objects;

public class FileUtil {

    /**
     * MultipartFile类型转File类型，文件名同被转换文件名称一致，如有需要可以拓展方法。
     */
    public static File MultipartFileToFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }
        // 获取InoutString
        InputStream inputStream = multipartFile.getInputStream();
        // 创建文件
        File toFile = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        // 写入文件
        OutputStream outputStream = new FileOutputStream(toFile);
        byte[] buffer = new byte[8192];
        int bytesRead = 0;
        while ((bytesRead = inputStream.read(buffer, 0, buffer.length)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.close();
        inputStream.close();
        return toFile;
    }
    /**
     * 下载文件至浏览器默认位置
     */
    public static ResponseEntity<InputStreamResource> downLoadFile(InputStream in, String fileName) throws IOException {
        byte[] testBytes = new byte[in.available()];
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(testBytes.length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(in));
    }
}