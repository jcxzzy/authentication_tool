package com.yzhengcoding.tool.authentication.dao.impl;

import com.yzhengcoding.tool.authentication.dao.CredentialStorage;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * 通过mysql获取证书信息
 * @author yzheng
 * @date 2021/5/30 2:51 下午
 */
@Repository
public class MysqlCredentialStorage implements CredentialStorage {

    @Resource
    private UserMapper userMapper;
    @Override
    public String getPasswordByAppId(String appId) {
        return userMapper.queryPasswordByAppId(appId);
    }
}
