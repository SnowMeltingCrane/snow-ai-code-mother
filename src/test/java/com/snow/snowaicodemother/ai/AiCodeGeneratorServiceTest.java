package com.snow.snowaicodemother.ai;

import com.snow.snowaicodemother.ai.model.HtmlCodeResult;
import com.snow.snowaicodemother.ai.model.MultiFileCodeResult;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author xueruohang
 * @date 2025/9/13 15:34
 */
@SpringBootTest
class AiCodeGeneratorServiceTest {

    @Resource
    private AiCodeGeneratorService aiCodeGeneratorService;

    @Test
    void generateHtmlCode() {
        HtmlCodeResult htmlCodeResult = aiCodeGeneratorService.generateHtmlCode("请生成一个雪融鹤的博客页面，不超过20行");
        Assertions.assertNotNull(htmlCodeResult);
    }

    @Test
    void generateMultiFileCode() {
        MultiFileCodeResult multiFileCodeResult = aiCodeGeneratorService.generateMultiFileCode("请生成一个雪融鹤的留言板，不超过50行");
        Assertions.assertNotNull(multiFileCodeResult);
    }
}