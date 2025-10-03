package com.snow.snowaicodemother.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * @author xueruohang
 * @date 2025/10/3 03:14
 */
@SpringBootTest
class WebScreenshotUtilsTest {

    @Test
    void saveWebPageScreenshot() {
        String url = "https://www.bilibili.com";
        String webPageScreenshot = WebScreenshotUtils.saveWebPageScreenshot(url);
        Assertions.assertNotNull(webPageScreenshot);
    }
}