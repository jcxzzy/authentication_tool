package com.yzhengcoding.tool.authentication;

import com.yzhengcoding.tool.authentication.encryption.EncryptionAlgBase;

import java.util.Map;

/**
 * 授权操作类
 *
 * @author yzheng
 * @date 2021/5/30 12:46 下午
 */
public class AuthToken {

    /**
     * 获取身份验证令牌
     */
    private String token;

    /**
     * 默认token有效时间为1分钟
     */
    private static final long DEFAULT_EXPIRED_TIME_INTERVAL= 60000;

    /**
     * 创建时间
     */
    private long createTime;

    /**
     * 加密算法
     */
    private static EncryptionAlgBase encryptionAlgBase;

    /**
     * 默认token有效时间 (单位：毫秒)
     */
    private long expiredTimeInterval = DEFAULT_EXPIRED_TIME_INTERVAL;

    public AuthToken(String token, long createTime) {
        this.token = token;
        this.createTime = createTime;
    }

    public AuthToken(String token, long createTime, long expiredTimeInterval) {
        this.token = token;
        this.createTime = createTime;
        this.expiredTimeInterval = expiredTimeInterval;
    }

    /**
     * 创建token
     *
     * @param createTime 创建时间
     * @param paramMap 参数集合
     * @return 创建授权操作类对象
     */
    public static AuthToken buildToken(long createTime, Map<String, Object> paramMap){
        StringBuilder paramStringBuilder = new StringBuilder();
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            paramStringBuilder.append(entry.getValue());
        }
        paramStringBuilder.append(createTime);
        // 使用加密算法计算token
        String token = encryptionAlgBase.encryptString(paramStringBuilder.toString());
        // 返回授权操作类
        return new AuthToken(token, createTime);
    }

    /**
     * 获取身份验证令牌
     *
     * @return token字段
     */
    public String getToken(){
        return token;
    }

    /**
     * 判断token是否过期
     * @return token是否过期
     */
    public boolean isExpired(){
        // 当前时间间隔
        long timeInterval = System.currentTimeMillis() - createTime;
        // 是否过期
        return timeInterval > expiredTimeInterval;
    }

    /**
     * 比较token信息
     *
     * @param authToken token信息
     * @return 权限验证是否成功
     */
    public boolean match(AuthToken authToken){
        // 验证token是否相等
        return token.equals(authToken.getToken());
    }
}
