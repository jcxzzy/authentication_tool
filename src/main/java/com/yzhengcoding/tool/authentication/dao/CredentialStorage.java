package com.yzhengcoding.tool.authentication.dao;

/**
 * 从存储获取证书信息
 *
 * @author yzheng
 * @date 2021/5/30 2:14 下午
 */
public interface CredentialStorage {

    /**
     * 根据请求Id获取密码
     *
     * @param appId 请求id
     * @return 密码
     */
    String getPasswordByAppId(String appId);
}
