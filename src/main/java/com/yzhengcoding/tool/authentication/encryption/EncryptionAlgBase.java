package com.yzhengcoding.tool.authentication.encryption;

/**
 * 加密算法基类
 *
 * @author yzheng
 * @date 2021/5/30 4:40 下午
 */
public interface EncryptionAlgBase {

    /**
     * 将字符串加密
     *
     * @param content 待加密内容
     * @return 加密后的内容
     */
    String encryptString(String content);
}
