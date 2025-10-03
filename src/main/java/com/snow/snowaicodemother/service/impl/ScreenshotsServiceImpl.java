package com.snow.snowaicodemother.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.snow.snowaicodemother.exception.ErrorCode;
import com.snow.snowaicodemother.exception.ThrowUtils;
import com.snow.snowaicodemother.manager.CosManager;
import com.snow.snowaicodemother.service.ScreenshotsService;
import com.snow.snowaicodemother.utils.WebScreenshotUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * @author xueruohang
 * @date 2025/10/3 03:53
 */
@Service
@Slf4j
public class ScreenshotsServiceImpl implements ScreenshotsService {
    private final CosManager cosManager;

    public ScreenshotsServiceImpl(CosManager cosManager) {
        this.cosManager = cosManager;
    }

    @Override
    public String generateAndUploadScreenshot(String webUrl) {
        // 参数校验
        ThrowUtils.throwIf(StrUtil.isBlank(webUrl), ErrorCode.PARAMS_ERROR, "网页链接不能为空");
        // 本地截图
        log.info("开始生成网页截图：{}", webUrl);
        String localScreenshotPath = WebScreenshotUtils.saveWebPageScreenshot(webUrl);
        ThrowUtils.throwIf(StrUtil.isBlank(localScreenshotPath), ErrorCode.SYSTEM_ERROR, "生成网页截图失败");
        // 上传到cos
        String cosUrl = null;
        try {
            cosUrl = uploadScreenshotToCos(localScreenshotPath);
            ThrowUtils.throwIf(StrUtil.isBlank(cosUrl), ErrorCode.SYSTEM_ERROR, "上传截图到COS失败");
            log.info("上传截图成功：{}", cosUrl);
            return cosUrl;
        } finally {
            // 清理本地文件
            cleanupLocalFile(localScreenshotPath);
        }
    }

    /**
     * 上传截图到COS
     *
     * @param localScreenshotPath 本地截图路径
     * @return 上传的URL
     */
    private String uploadScreenshotToCos(String localScreenshotPath) {
        if (StrUtil.isBlank(localScreenshotPath)) {
            return null;
        }
        File screenshotFile = new File(localScreenshotPath);
        if (!screenshotFile.exists()) {
            log.error("本地截图文件不存在：{}", localScreenshotPath);
            return null;
        }
        String fileName = UUID.randomUUID().toString().substring(0, 8) + "_compressed.png";
        String cosKey = generateScreenshotKey(fileName);
        return cosManager.uploadFile(cosKey, screenshotFile);
    }

    /**
     * 生成截图的COS Key
     *
     * @param fileName 文件名
     * @return COS Key
     */
    private String generateScreenshotKey(String fileName) {
        String dataPath = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        return String.format("/screenshots/%s/%s", dataPath, fileName);
    }

    /**
     * 清理本地文件
     *
     * @param localScreenshotPath
     */
    private void cleanupLocalFile(String localScreenshotPath) {
        File screenshotFile = new File(localScreenshotPath);
        if (screenshotFile.exists()) {
            FileUtil.del(screenshotFile);
            log.info("清理本地文件成功：{}", localScreenshotPath);
        }
    }
}
