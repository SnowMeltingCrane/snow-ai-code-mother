package com.snow.snowaicodemother.constant;

/**
 * @author xueruohang
 * @date 2025/9/27 17:10
 */
public interface AppConstant {

    /**
     * 精选应用的优先级
     */
    Integer GOOD_APP_PRIORITY = 99;

    /**
     * 默认应用优先级
     */
    Integer DEFAULT_APP_PRIORITY = 0;

    /**
     * 应用生成目录
     */
    String CODE_OUTPUT_ROOT_DIR = System.getProperty("user.dir") + "/tmp/code_output";

    /**
     * 应用部署目录
     */
    String CODE_DEPLOY_ROOT_DIR = System.getProperty("user.dir") + "/tmp/code_deploy";

    /**
     * nginx访问目录
     */
    String NGINX_ROOT_DIR = "/usr/share/nginx/html";

    /**
     * 应用部署域名
     */
    String CODE_DEPLOY_HOST = "http://localhost";

}
