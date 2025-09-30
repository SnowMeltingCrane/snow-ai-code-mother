package com.snow.snowaicodemother.core;

import com.snow.snowaicodemother.model.enums.CodeGenTypeEnum;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author xueruohang
 * @date 2025/9/14 14:38
 */
@SpringBootTest
class AiCodeGeneratorFacadeTest {

    @Resource
    private AiCodeGeneratorFacade aiCodeGeneratorFacade;

    @Test
    void generateAndSaveCode() {
        File file = aiCodeGeneratorFacade.generateAndSaveCode("生成一个html页面，内容是Hello World", CodeGenTypeEnum.HTML, 1L);
        Assertions.assertNotNull(file);
    }

    @Test
    void generateAndSaveCodeStream() {
        Flux<String> codeStream = aiCodeGeneratorFacade.generateAndSaveCodeStream("生成一个html页面，内容是Hello World", CodeGenTypeEnum.HTML, 1L);
        List<String> result = codeStream.collectList().block();
        Assertions.assertNotNull(result);
        String completeCode = String.join("", result);
        Assertions.assertNotNull(completeCode);
    }
}