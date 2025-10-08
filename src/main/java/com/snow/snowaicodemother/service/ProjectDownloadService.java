package com.snow.snowaicodemother.service;

import jakarta.servlet.http.HttpServletResponse;

/**
 * @author xueruohang
 * @date 2025/10/3 23:52
 */
public interface ProjectDownloadService {
    void downloadProjectAsZip(String projectPath, String downloadFileName, HttpServletResponse response);
}
