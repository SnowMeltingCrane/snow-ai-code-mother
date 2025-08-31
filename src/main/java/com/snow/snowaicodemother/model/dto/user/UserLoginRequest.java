package com.snow.snowaicodemother.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xueruohang
 * @date 2025/8/31 22:54
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;
}

