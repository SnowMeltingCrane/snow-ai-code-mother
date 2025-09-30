package com.snow.snowaicodemother.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.snow.snowaicodemother.model.dto.app.AppDeployRequest;
import com.snow.snowaicodemother.model.dto.app.AppQueryRequest;
import com.snow.snowaicodemother.model.entity.App;
import com.snow.snowaicodemother.model.entity.User;
import com.snow.snowaicodemother.model.vo.AppVO;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * 应用 服务层。
 *
 * @author <a href="https://github.com/SnowMeltingCrane">雪融鹤</a>
 */
public interface AppService extends IService<App> {

    /**
     * 校验应用
     *
     * @param app 应用
     * @param add 是否为创建校验
     */
    void validApp(App app, boolean add);

    /**
     * 获取查询包装类
     *
     * @param appQueryRequest 应用查询请求
     * @return 查询包装类
     */
    QueryWrapper getQueryWrapper(AppQueryRequest appQueryRequest);

    /**
     * 获取应用封装
     *
     * @param app 应用
     * @return 应用封装
     */
    AppVO getAppVO(App app);

    /**
     * 分页获取应用封装
     *
     * @param appPage 应用分页
     * @return 应用封装分页
     */
    Page<AppVO> getAppVOPage(Page<App> appPage);

    /**
     * 获取应用封装列表
     *
     * @param appList 应用列表
     * @return 应用封装列表
     */
    List<AppVO> getAppVOList(List<App> appList);

    /**
     * 分页查询精选应用
     *
     * @param appQueryRequest 查询请求
     * @return 精选应用分页
     */
    Page<AppVO> listRecommendAppVOByPage(AppQueryRequest appQueryRequest);

    /**
     * 分页查询我的应用
     *
     * @param appQueryRequest 查询请求
     * @param loginUser 当前登录用户
     * @return 我的应用分页
     */
    Page<AppVO> listMyAppVOByPage(AppQueryRequest appQueryRequest, User loginUser);

    /**
     * 校验应用权限（是否为应用创建者）
     *
     * @param app 应用
     * @param loginUser 当前登录用户
     */
    void checkAppAuth(App app, User loginUser);

    /**
     * 通过聊天生成代码
     * @param appId     应用id
     * @param message   用户提示词
     * @param loginUser 当前登录用户
     * @return 生成的代码
     */
    Flux<String> chatToGenCode(Long appId, String message, User loginUser);


    /**
     * 应用部署
     *
     * @param appId     应用id
     * @param loginUser 当前登录用户
     * @return 可访问的部署地址
     */
    String deployApp(Long appId, User loginUser);


}
