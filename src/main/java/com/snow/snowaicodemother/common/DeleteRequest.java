package com.snow.snowaicodemother.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xueruohang
 * @date 2025/8/31 19:32
 */
@Data
public class DeleteRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}

