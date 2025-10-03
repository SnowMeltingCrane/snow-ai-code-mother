package com.snow.snowaicodemother.service;


/**
 * 截图服务
 *
 * @author xueruohang
 * @date 2025/10/3 03:50
 */
public interface ScreenshotsService {

    /**
     * 通用截图服务，可得到访问地址
     *
     * @param webUrl 网页地址
     * @return 访问截图地址
     */
    String generateAndUploadScreenshot(String webUrl);
}
