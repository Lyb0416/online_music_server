package com.lyb.olinemusicserver.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ObjectStoreService {

    String uploadFile(String dirName, String objectName, MultipartFile file) throws IOException;

    boolean deleteFile(String objectName);

    void downloadFile(String objectUrl, String targetFilePath);
}
