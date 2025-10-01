package com.snow.snowaicodemother.ai;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.snow.snowaicodemother.ai.tools.FileWriteTool;
import com.snow.snowaicodemother.exception.BusinessException;
import com.snow.snowaicodemother.exception.ErrorCode;
import com.snow.snowaicodemother.model.enums.CodeGenTypeEnum;
import com.snow.snowaicodemother.service.ChatHistoryService;
import dev.langchain4j.community.store.memory.chat.redis.RedisChatMemoryStore;
import dev.langchain4j.data.message.ToolExecutionResultMessage;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * @author xueruohang
 * @date 2025/9/13 15:02
 * ai服务创建工厂
 */
@Configuration
@Slf4j
public class AiCodeGeneratorServiceFactory {

    @Resource
    private ChatModel chatModel;

    @Resource
    private StreamingChatModel openAiStreamingChatModel;

    @Resource
    private StreamingChatModel reasoningStreamingChatModel;

    @Resource
    private RedisChatMemoryStore redisChatMemoryStore;

    @Resource
    private ChatHistoryService chatHistoryService;

    /**
     * 缓存ai服务
     * 缓存策略
     * -最大缓存1000
     * -写入后30分钟过期
     * -访问后10分钟过期
     */
    private final Cache<String, AiCodeGeneratorService> serviceCache = Caffeine.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(Duration.ofMinutes(30))
            .expireAfterAccess(Duration.ofMinutes(10))
            .removalListener((key, value, cause) -> {
                log.debug("Ai服务缓存被移除 缓存键: {}, 原因: {}", key, cause);
            })
            .build();

    /**
     * 根据appId创建ai服务 (兼容老逻辑)
     *
     * @param appId 应用id
     * @return ai服务
     */
    public AiCodeGeneratorService getAiCodeGeneratorService(long appId) {
        // 根据appId获取对应的chatMemory
        return getAiCodeGeneratorService(appId, CodeGenTypeEnum.HTML);
    }

    /**
     * 根据appId创建ai服务
     *
     * @param appId 应用id
     * @return ai服务
     */
    public AiCodeGeneratorService getAiCodeGeneratorService(long appId,CodeGenTypeEnum codeGenType) {
        String cacheKey = buildCacheKey(appId, codeGenType);
        // 根据appId获取对应的chatMemory
        return serviceCache.get(cacheKey,key -> createAiCodeGeneratorService(appId, codeGenType));
    }

    /**
     * 创建ai服务
     *
     * @param appId         应用id
     * @param codeGenType   代码生成类型
     * @return ai服务
     */
    private AiCodeGeneratorService createAiCodeGeneratorService(Long appId, CodeGenTypeEnum codeGenType) {
        log.info("创建ai服务 appId: {}", appId);
        MessageWindowChatMemory chatMemory = MessageWindowChatMemory
                .builder()
                .id(appId)
                .maxMessages(20)
                .chatMemoryStore(redisChatMemoryStore)
                .build();
        // 从数据库中加载对话历史
        chatHistoryService.loadChatHistoryToMemory(appId, chatMemory, 20);
        return switch (codeGenType){
            // Vue 项目生成，使用工具调用和推理模型
            case VUE_PROJECT -> AiServices.builder(AiCodeGeneratorService.class)
                    .chatModel(chatModel)
                    .streamingChatModel(reasoningStreamingChatModel)
                    .chatMemoryProvider(memoryId -> chatMemory)
                    .tools(new FileWriteTool())
                    // 处理工具调用幻觉问题
                    .hallucinatedToolNameStrategy(toolExecutionRequest ->
                            ToolExecutionResultMessage.from(toolExecutionRequest, "Error: there is no tool called" + toolExecutionRequest.name()))
                    .build();
            // HTML 和 多文件生成，使用流式对话模型
            case HTML,MULTI_FILE  -> AiServices.builder(AiCodeGeneratorService.class)
                    .chatModel(chatModel)
                    .streamingChatModel(openAiStreamingChatModel)
                    .chatMemory(chatMemory)
                    .build();
            default -> throw new BusinessException(ErrorCode.SYSTEM_ERROR,"不支持的代码生成类型" + codeGenType);
        };
    }

    @Bean
    public AiCodeGeneratorService createAiCodeGeneratorService() {
        return getAiCodeGeneratorService(0);
    }

    /**
     * 构造缓存键
     *
     * @param appId
     * @param codeGenType
     * @return
     */
    private String buildCacheKey(Long appId, CodeGenTypeEnum codeGenType) {
        return appId + "_" + codeGenType.getValue();
    }

}
