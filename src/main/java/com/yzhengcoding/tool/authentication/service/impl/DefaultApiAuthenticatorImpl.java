package com.yzhengcoding.tool.authentication.service.impl;

import com.yzhengcoding.tool.authentication.AuthToken;
import com.yzhengcoding.tool.authentication.dao.CredentialStorage;
import com.yzhengcoding.tool.authentication.dao.impl.MysqlCredentialStorage;
import com.yzhengcoding.tool.authentication.entity.ApiRequest;
import com.yzhengcoding.tool.authentication.service.ApiAuthenticator;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 默认鉴权接口实现类（基于mysql存储实现）
 *
 * @author yzheng
 * @date 2021/5/30 2:28 下午
 */
public class DefaultApiAuthenticatorImpl implements ApiAuthenticator {

    @Resource(type = MysqlCredentialStorage.class)
    private CredentialStorage credentialStorage;


    public DefaultApiAuthenticatorImpl(CredentialStorage credentialStorage) {
        this.credentialStorage = credentialStorage;
    }

    /**
     * 接口鉴权
     *
     * @param apiRequest 认证请求体
     */
    @Override
    public void auth(ApiRequest apiRequest) {
        String appId = apiRequest.getAppId();
        String token = apiRequest.getToken();
        long timestamp = apiRequest.getTimestamp();
        // 创建客户端认证token对象
        AuthToken clientAuthToken = new AuthToken(token, timestamp);
        // 客户端发送端token是否已经过期
        if(clientAuthToken.isExpired()) {
            // 鉴权接口和外边程序以异常进行交互
            throw new RuntimeException("Token is expired.");
        }
        String password = credentialStorage.getPasswordByAppId(appId);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("appId", appId);
        paramMap.put("password", password);
        // 创建服务端认证token对象
        AuthToken serverAuthToken = AuthToken.buildToken(timestamp, paramMap);
        // 进行权限验证
        if(!serverAuthToken.match(clientAuthToken)){
            throw new RuntimeException("Token verfication failed.");
        }
    }
}
