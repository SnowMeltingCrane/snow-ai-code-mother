package com.snow.snowaicodemother.model.dto.app;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xueruohang
 * @date 2025/10/1 02:38
 */
@Data
public class AppDeployRequest implements Serializable {

    /**
     * 应用id
     */
    private Long appId;

    private static final long serialVersionUID = 1L;
}
