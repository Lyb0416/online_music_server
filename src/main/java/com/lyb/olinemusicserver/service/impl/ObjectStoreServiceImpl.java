package com.lyb.olinemusicserver.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.DownloadFileRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.lyb.olinemusicserver.service.ObjectStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Oss 相关业务实现类
 * @author yaojianfeng
 */

@Service
public class ObjectStoreServiceImpl implements ObjectStoreService {

    @Autowired
    private OSS ossClient;

    @Value("${aliyun.oss.bucketName}")
    private String BUCKET_NAME;
    @Value("${aliyun.oss.endPoint}")
    private String ENDPOINT;


    @Override
    public String uploadFile(String dirName, String objectName, MultipartFile file) throws IOException {
        String fullObjectName = dirName + "/" + objectName;
        PutObjectResult result = ossClient.putObject(BUCKET_NAME, fullObjectName, file.getInputStream());
        return getAccessibleUrl(result.getETag(), fullObjectName);
    }

    private String getAccessibleUrl(String eTag, String objectName) {
        return "https://" + BUCKET_NAME + "." + ENDPOINT + "/" + objectName;
    }

    @Override
    public boolean deleteFile(String objectName) {
        boolean flag = false;
        String host = "https://" + BUCKET_NAME + "." + ENDPOINT + "/";
        //获取文件路径  路径不包含Bucket名称和ENDPOINT名称
        objectName = objectName.substring(host.length());
        try {
            boolean isExist = ossClient.doesObjectExist(BUCKET_NAME,objectName);

            if(isExist) {
                // 删除文件或目录。如果要删除目录，目录必须为空。
                ossClient.deleteObject(BUCKET_NAME, objectName);
                System.out.println("删除oss文件 success!!");
                flag = true;
            }
        } catch (OSSException oe) {
            System.out.println("删除oss文件 failure:"+oe);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return flag;
    }


    @Override
    public void downloadFile(String objectUrl,String targetFilePath) {
        String host = "https://" + BUCKET_NAME + "." + ENDPOINT + "/";
        // 从objectUrl中提取文件名
        String fileName = objectUrl.substring(objectUrl.lastIndexOf('/') + 1);

        String objectKey = objectUrl.substring(host.length());
        //创建文件夹
        File folder = new File(targetFilePath);
        if (!(folder.exists()&&folder.isDirectory())){
            boolean created = folder.mkdirs();
            System.out.println(created?"创建文件夹成功":"创建文件夹失败");
        }


        try {
            DownloadFileRequest downloadFileRequest = new DownloadFileRequest(BUCKET_NAME, objectKey);
            // Sets the local file to download to
            downloadFileRequest.setDownloadFile(targetFilePath + fileName);
            // Sets the concurrent task thread count 5. By default it's 1.
            downloadFileRequest.setTaskNum(5);
            // Sets the part size, by default it's 100K.
            downloadFileRequest.setPartSize(1024 * 1024 * 1);
            // Enable checkpoint. By default it's false.
            downloadFileRequest.setEnableCheckpoint(true);

            ossClient.downloadFile(downloadFileRequest);


        }  catch (Throwable e) {
            e.printStackTrace();
        }
    }


}
