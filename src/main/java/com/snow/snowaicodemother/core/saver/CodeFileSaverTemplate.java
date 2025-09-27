package com.snow.snowaicodemother.core.saver;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.snow.snowaicodemother.exception.BusinessException;
import com.snow.snowaicodemother.exception.ErrorCode;
import com.snow.snowaicodemother.model.enums.CodeGenTypeEnum;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 抽象代码文件保存器 - 模板方法模式
 * @author xueruohang
 * @date 2025/9/21 15:41
 */
public abstract class CodeFileSaverTemplate<T>  {

    /**
     * 临时文件保存目录
     */
    private static final String FILE_SAVE_ROOT_DIR = System.getProperty("user.dir") + "/tmp/code_output";


    /**
     * 模版方法具体流程
     * @param result 结果对象
     * @param appId 应用id
     * @return 保存后的文件
     */
    public final File saveCode(T result,Long appId){
        // 1.验证输入
        validateInput(result);
        // 2.构建目录
        String baseDirPath = buildUniqueDir(appId);
        // 3.保存文件
        saveFiles(result, baseDirPath);
        // 4.返回文件
        return new File(baseDirPath);
    }

    /**
     * 保存单个文件
     * @param dirPath 目录路径
     * @param fileName 文件名
     * @param content 文件内容
     */
    public final void writeToFile(String dirPath, String fileName, String content) {
        if(StrUtil.isNotBlank(dirPath)){
            String filePath = dirPath + File.separator + fileName;
            FileUtil.writeString(content, filePath, StandardCharsets.UTF_8);
        }
    }

    /**
     * 验证输入
     * @param result 结果对象
     */
    protected void validateInput(T result){
        if(Objects.isNull(result)){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"代码结果对象不能为空");
        }
    };

    /**
     * 生成唯一目录
     * @param appId 应用id
     * @return 返回目录路径
     */
    protected String buildUniqueDir(Long appId){
        if(Objects.isNull(appId)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"应用id不能为空");
        }
        String codeType = getCodeGenType().getValue();
        String uniqueDirName = StrUtil.format("{}_{}" , codeType, appId);
        String dirPath = FILE_SAVE_ROOT_DIR + File.separator + uniqueDirName;
        FileUtil.mkdir(dirPath);
        return dirPath;
    }

    /**
     * 获取代码生成类型
     * @return
     */
    protected abstract CodeGenTypeEnum getCodeGenType();

    /**
     * 保存文件
     * @param result 结果对象
     * @param baseDirPath 基础目录路径
     */
    protected abstract void saveFiles(T result, String baseDirPath);

}
