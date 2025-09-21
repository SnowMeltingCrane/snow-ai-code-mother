package com.snow.snowaicodemother.ai.model;

import dev.langchain4j.model.output.structured.Description;
import lombok.Data;

/**
 * @author xueruohang
 * @date 2025/9/13 15:46
 */
@Data
@Description("生产html代码文件的结果")
public class HtmlCodeResult {

    /**
     * html代码
     */
    @Description("HTML代码")
    private String htmlCode;

    /**
     * 描述
     */
    @Description("生成代码的描述描述")
    private String description;
}
