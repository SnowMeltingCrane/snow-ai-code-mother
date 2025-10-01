package com.snow.snowaicodemother.ai;

import com.snow.snowaicodemother.ai.model.HtmlCodeResult;
import com.snow.snowaicodemother.ai.model.MultiFileCodeResult;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import reactor.core.publisher.Flux;

/**
 * @author xueruohang
 * @date 2025/9/13 14:52
 */
public interface AiCodeGeneratorService {

    /**
     * 生成代码
     *
     * @param userMessage 用户提示词
     * @return ai 输出的结果
     */
    @SystemMessage(fromResource = "prompt/codegen-html-system-prompt.txt")
    HtmlCodeResult generateHtmlCode(String userMessage);

    /**
     * 生成代码
     *
     * @param userMessage 用户提示词
     * @return ai 输出的结果
     */
    @SystemMessage(fromResource = "prompt/codegen-multi-file-system-prompt.txt")
    MultiFileCodeResult generateMultiFileCode(String userMessage);

    /**
     * 流式生成代码
     *
     * @param userMessage 用户提示词
     * @return ai 输出的结果
     */
    @SystemMessage(fromResource = "prompt/codegen-html-system-prompt.txt")
    Flux<String> generateHtmlCodeStream(String userMessage);

    /**
     * 流式生成代码
     *
     * @param userMessage 用户提示词
     * @return ai 输出的结果
     */
    @SystemMessage(fromResource = "prompt/codegen-multi-file-system-prompt.txt")
    Flux<String> generateMultiFileCodeStream(String userMessage);

    /**
     * 流式生成Vue项目代码
     *
     * @param appId         应用ID
     * @param userMessage   用户提示词
     * @return ai 输出的结果
     */
    @SystemMessage(fromResource = "prompt/codegen-vue-project-system-prompt.txt")
    Flux<String> generateVueProjectCodeStream(@MemoryId Long appId, @UserMessage String userMessage);
}
