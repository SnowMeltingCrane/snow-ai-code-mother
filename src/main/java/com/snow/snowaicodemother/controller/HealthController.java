package com.snow.snowaicodemother.controller;

import com.snow.snowaicodemother.common.BaseResponse;
import com.snow.snowaicodemother.common.ResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xueruohang
 * @date 2025/8/31 18:50
 */
@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping("/")
    public BaseResponse<String> healthCheck() {
        return ResultUtils.success("ok");
    }
}
