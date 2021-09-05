package com.yzhengcoding.tool.authentication.dao.impl;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 查询用户信息
 * @author yzheng
 * @date 2021/6/6 2:40 下午
 */
@Mapper
@Repository
public interface UserMapper {

    /**
     *
     * @param appId
     * @return
     */
    String queryPasswordByAppId(String appId);
}
