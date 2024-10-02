package com.mcddhub.backend.manager;

import com.mcddhub.backend.config.CosClientConfig;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Cos 对象存储操作
 *
 * @author: caobaoqi1029
 * @date: 2024/10/1 11:51
 */
@Component
public class CosManager {

    private final CosClientConfig cosClientConfig;
    private final COSClient cosClient;

    public CosManager(CosClientConfig cosClientConfig, COSClient cosClient) {
        this.cosClientConfig = cosClientConfig;
        this.cosClient = cosClient;
    }


    /**
     * 上传对象
     *
     * @param key 唯一键
     * @param localFilePath 本地文件路径
     * @return result
     */
    public PutObjectResult putObject(String key, String localFilePath) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(cosClientConfig.getBucket(), key,
                new File(localFilePath));
        return cosClient.putObject(putObjectRequest);
    }

    /**
     * 上传对象
     *
     * @param key 唯一键
     * @param file 文件
     * @return result
     */
    public PutObjectResult putObject(String key, File file) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(cosClientConfig.getBucket(), key,
                file);
        return cosClient.putObject(putObjectRequest);
    }
}
