package com.yzhengcoding.tool.authentication.entity;

/**
 * 鉴权请求请求信息父类
 *
 * @author yzheng
 * @date 2021/5/30 2:07 下午
 */
public class ApiRequest {

    /**
     * 鉴权信息
     */
    private String token;

    /**
     * 请求id
     */
    private String appId;

    /**
     * 时间戳
     */
    private long timestamp;

    public ApiRequest(String token, String appId, long timestamp) {
        this.token = token;
        this.appId = appId;
        this.timestamp = timestamp;
    }

    public String getToken() {
        return token;
    }

    public String getAppId() {
        return appId;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
