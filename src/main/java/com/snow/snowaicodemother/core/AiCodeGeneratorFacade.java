package com.snow.snowaicodemother.core;

import com.snow.snowaicodemother.ai.AiCodeGeneratorService;
import com.snow.snowaicodemother.ai.AiCodeGeneratorServiceFactory;
import com.snow.snowaicodemother.ai.model.HtmlCodeResult;
import com.snow.snowaicodemother.ai.model.MultiFileCodeResult;
import com.snow.snowaicodemother.core.parser.CodeParserExecutor;
import com.snow.snowaicodemother.core.saver.CodeFileSaverExecutor;
import com.snow.snowaicodemother.exception.BusinessException;
import com.snow.snowaicodemother.exception.ErrorCode;
import com.snow.snowaicodemother.model.enums.CodeGenTypeEnum;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.File;
import java.util.Objects;

/**
 * @author xueruohang
 * @date 2025/9/14 14:25
 */
@Service
@Slf4j
public class AiCodeGeneratorFacade {

    @Resource
    private AiCodeGeneratorServiceFactory aiCodeGeneratorServiceFactory;

    /**
     * 生成并保存代码
     *
     * @param userMessage     用户提示词
     * @param codeGenTypeEnum 生成模式
     * @param appId           应用id
     * @return 保存目录
     */
    public File generateAndSaveCode(String userMessage, CodeGenTypeEnum codeGenTypeEnum, Long appId) {
        if (Objects.isNull(codeGenTypeEnum)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "生成模式不能为空");
        }
        AiCodeGeneratorService aiCodeGeneratorService = aiCodeGeneratorServiceFactory.getAiCodeGeneratorService(appId, codeGenTypeEnum);
        return switch (codeGenTypeEnum) {
            case HTML -> {
                HtmlCodeResult codeResult = aiCodeGeneratorService.generateHtmlCode(userMessage);
                yield CodeFileSaverExecutor.executeSaver(codeResult, codeGenTypeEnum, appId);
            }
            case MULTI_FILE -> {
                MultiFileCodeResult codeResult = aiCodeGeneratorService.generateMultiFileCode(userMessage);
                yield CodeFileSaverExecutor.executeSaver(codeResult, codeGenTypeEnum, appId);
            }
            default -> {
                String errorMsg = "不支持的生成模式" + codeGenTypeEnum.getValue();
                throw new BusinessException(ErrorCode.PARAMS_ERROR, errorMsg);
            }
        };
    }

    /**
     * 生成并保存代码(流式)
     *
     * @param userMessage     用户提示词
     * @param codeGenTypeEnum 生成模式
     * @param appId           应用id
     * @return 流式响应
     */
    public Flux<String> generateAndSaveCodeStream(String userMessage, CodeGenTypeEnum codeGenTypeEnum, Long appId) {
        if (Objects.isNull(codeGenTypeEnum)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "生成模式不能为空");
        }
        AiCodeGeneratorService aiCodeGeneratorService = aiCodeGeneratorServiceFactory.getAiCodeGeneratorService(appId, codeGenTypeEnum);
        return switch (codeGenTypeEnum) {
            case HTML -> {
                Flux<String> result = aiCodeGeneratorService.generateHtmlCodeStream(userMessage);
                yield processCodeStream(result, CodeGenTypeEnum.HTML, appId);
            }
            case MULTI_FILE -> {
                Flux<String> codeStream = aiCodeGeneratorService.generateMultiFileCodeStream(userMessage);
                yield processCodeStream(codeStream, CodeGenTypeEnum.MULTI_FILE, appId);
            }
            case VUE_PROJECT -> {
                Flux<String> codeStream = aiCodeGeneratorService.generateVueProjectCodeStream(appId, userMessage);
                yield processCodeStream(codeStream, CodeGenTypeEnum.MULTI_FILE, appId);
            }
            default -> {
                String errorMsg = "不支持的生成模式" + codeGenTypeEnum.getValue();
                throw new BusinessException(ErrorCode.PARAMS_ERROR, errorMsg);
            }
        };
    }

    /**
     * 通用(流式)
     *
     * @param codeStream      代码流
     * @param codeGenTypeEnum 生成类型
     * @param appId           应用id
     * @return 流式响应
     */
    private Flux<String> processCodeStream(Flux<String> codeStream, CodeGenTypeEnum codeGenTypeEnum, Long appId) {
        // 定义一个字符串拼接器，用于流式返回所有代码后，保存代码
        StringBuilder codeBuilder = new StringBuilder();
        return codeStream.doOnNext(codeBuilder::append).doOnComplete(() -> {
            // 流失返回完成，保存代码
            try {
                String completeCode = codeBuilder.toString();
                // 使用执行器解析代码
                Object codeResult = CodeParserExecutor.executeParser(completeCode, codeGenTypeEnum);
                // 使用执行器保存代码
                File saveDir = CodeFileSaverExecutor.executeSaver(codeResult, codeGenTypeEnum, appId);
                log.info("保存成功 目录为：{}", saveDir.getAbsolutePath());
            } catch (Exception e) {
                log.error("保存失败：{}", e.getMessage());
            }
        });
    }
}
