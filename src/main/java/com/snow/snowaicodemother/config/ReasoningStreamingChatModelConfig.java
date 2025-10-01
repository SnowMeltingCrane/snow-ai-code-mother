package com.snow.snowaicodemother.config;

import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xueruohang
 * @date 2025/10/2 01:33
 */
@Configuration
@ConfigurationProperties(prefix = "langchain4j.openai.chat-model")
@Data
public class ReasoningStreamingChatModelConfig {

    private String baseUrl;

    private String apiKey;

    @Bean
    public StreamingChatModel reasoningStreamingChatModel() {
        //测试环境使用
        final String modelName = "deepseek-chat";
        final int maxTokens = 8192;
        //生产环境使用
        //final String modelName = "deepseek-reasoner";
        //final int maxTokens = 32768;
        return OpenAiStreamingChatModel.builder()
                .apiKey(apiKey)
                .baseUrl(baseUrl)
                .modelName(modelName)
                .maxTokens(maxTokens)
                .logRequests(true)
                .logResponses(true)
                .build();
    }
}
