package com.snow.snowaicodemother.manager;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.snow.snowaicodemother.config.CosClientConfig;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Objects;

/**
 * Cos 对象存储工具类
 *
 * @author xueruohang
 * @date 2025/10/3 03:38
 */
@Component
@Slf4j
public class CosManager {

    @Resource
    private CosClientConfig cosClientConfig;

    @Resource
    private COSClient cosClient;


    /**
     * 上传对象
     *
     * @param key  唯一键
     * @param file 文件
     * @return 上传结果
     */
    public PutObjectResult putObject(String key, File file) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(cosClientConfig.getBucket(), key, file);
        return cosClient.putObject(putObjectRequest);
    }

    public String uploadFile(String key, File file){
        PutObjectResult putObjectResult = putObject(key, file);
        if(Objects.nonNull(putObjectResult)){
            String url = String.format("%s%s", cosClientConfig.getHost(), key);
            log.info("上传到cos成功: {} -> {}", file.getName(),url);
            return url;
        } else {
            log.error("上传到cos失败: {} 返回结果为空", file.getName());
            return null;
        }
    }

}
