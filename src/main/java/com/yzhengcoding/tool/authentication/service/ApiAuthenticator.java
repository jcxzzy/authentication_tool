package com.yzhengcoding.tool.authentication.service;

import com.yzhengcoding.tool.authentication.entity.ApiRequest;

/**
 * 接口鉴权抽象接口类
 *
 * @author yzheng
 * @date 2021/5/30 2:25 下午
 */
public interface ApiAuthenticator {

    /**
     * 接口鉴权
     *
     * @param apiRequest 认证请求体
     */
    void auth(ApiRequest apiRequest);

}
