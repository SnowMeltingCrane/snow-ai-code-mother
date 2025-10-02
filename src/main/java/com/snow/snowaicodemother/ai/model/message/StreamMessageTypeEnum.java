package com.snow.snowaicodemother.ai.model.message;

/**
 * @author xueruohang
 * @date 2025/10/2 13:55
 */
public enum StreamMessageTypeEnum {

    /**
     * AI响应
     */
    AI_RESPONSE("ai_response","AI响应"),

    /**
     * 工具请求
     */
    TOOL_REQUEST("tool_request","工具请求"),

    /**
     * 工具执行结果
     */
    TOOL_EXECUTED("tool_excuted","工具执行结果");

    private final String value;
    private final String test;

    StreamMessageTypeEnum(String value, String test) {
        this.value = value;
        this.test = test;
    }

    public static StreamMessageTypeEnum getEnumByValue(String value) {
        for (StreamMessageTypeEnum typeEnum : StreamMessageTypeEnum.values()) {
            if (typeEnum.getValue().equals(value)) {
                return typeEnum;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public String getTest() {
        return test;
    }

}
