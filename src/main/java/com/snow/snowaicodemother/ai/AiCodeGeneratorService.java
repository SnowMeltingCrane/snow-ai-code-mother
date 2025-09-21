package com.snow.snowaicodemother.ai;

import com.snow.snowaicodemother.ai.model.HtmlCodeResult;
import com.snow.snowaicodemother.ai.model.MultiFileCodeResult;
import dev.langchain4j.service.SystemMessage;
import reactor.core.publisher.Flux;

/**
 * @author xueruohang
 * @date 2025/9/13 14:52
 */
public interface AiCodeGeneratorService {

    /**
     * 生成代码
     * @param userMessage 用户提示词
     * @return ai 输出的结果
     */
    @SystemMessage(fromResource = "prompt/codegen-html-system-prompt.txt")
    HtmlCodeResult generateHtmlCode(String userMessage);

    /**
     * 生成代码
     * @param userMessage 用户提示词
     * @return ai 输出的结果
     */
    @SystemMessage(fromResource = "prompt/codegen-multi-file-system-prompt.txt")
    MultiFileCodeResult generateMultiFileCode(String userMessage);

    /**
     * 流式生成代码
     * @param userMessage 用户提示词
     * @return ai 输出的结果
     */
    @SystemMessage(fromResource = "prompt/codegen-html-system-prompt.txt")
    Flux<String> generateHtmlCodeStream(String userMessage);

    /**
     * 流式生成代码
     * @param userMessage 用户提示词
     * @return ai 输出的结果
     */
    @SystemMessage(fromResource = "prompt/codegen-multi-file-system-prompt.txt")
    Flux<String>  generateMultiFileCodeStream(String userMessage);
}
