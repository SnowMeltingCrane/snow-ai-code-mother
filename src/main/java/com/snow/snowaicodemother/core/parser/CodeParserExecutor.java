package com.snow.snowaicodemother.core.parser;

import com.snow.snowaicodemother.exception.BusinessException;
import com.snow.snowaicodemother.exception.ErrorCode;
import com.snow.snowaicodemother.model.enums.CodeGenTypeEnum;

/**
 * 代码解析器执行器
 * @author xueruohang
 * @date 2025/9/21 15:32
 */
public class CodeParserExecutor {

    private static final HtmlCodeParser htmlCodeParser = new HtmlCodeParser();

    private static final MultiFileCodeParser multiFileCodeParser = new MultiFileCodeParser();

    /**
     * 执行代码解析
     * @param codeContent 代码内容
     * @return 解析结果(HTML、多文件)
     */
    public static Object executeParser(String codeContent ,CodeGenTypeEnum codeGenTypeEnum) {
        return switch(codeGenTypeEnum){
            case HTML -> htmlCodeParser.parseCode(codeContent);
            case MULTI_FILE -> multiFileCodeParser.parseCode(codeContent);
            default -> throw new BusinessException(ErrorCode.SYSTEM_ERROR,"不支持的代码生成类型");
        };
    }
}
