package com.snow.snowaicodemother.core.parser;

/**
 * @author xueruohang
 * @date 2025/9/21 15:26
 */
public interface CodeParser<T> {

    /**
     * 解析代码内容
     * @param codeContent
     * @return
     */
    T parseCode(String codeContent);
}
