package com.yzhengcoding.tool.authentication.encryption.impl;

import com.yzhengcoding.tool.authentication.encryption.EncryptionAlgBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 * Ase对称加密
 *
 * @author yzheng
 * @date 2021/6/6 12:02 下午
 */
public class AesEncryptionAlg implements EncryptionAlgBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(AesEncryptionAlg.class);

    private static SecretKey secretKey;

    public static boolean init(){
        try {
            secretKey = generateKey();
        }catch (NoSuchAlgorithmException e){
            // 打印日志
            LOGGER.error("ase加密算法初始化失败！ exception: ", e);
            return false;
        }
        return true;
    }

    @Override
    public String encryptString(String content) {
        String encryptContent = null;
        try {
            encryptContent = encrypt(content, secretKey).toString();
        }catch (Exception e){
            // 打印日志
            LOGGER.error("ase算法加密失败！exception", e);
        }
        return encryptContent;
    }


    static final String ALGORITHM = "AES";

    /**
     * 生成密钥
     *
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static SecretKey generateKey() throws NoSuchAlgorithmException {
        // 生成密钥
        KeyGenerator secretGenerator = KeyGenerator.getInstance(ALGORITHM);
        SecureRandom secureRandom = new SecureRandom();
        secretGenerator.init(secureRandom);
        SecretKey secretKey = secretGenerator.generateKey();
        return secretKey;
    }

    static Charset charset = Charset.forName("UTF-8");

    /**
     * 加密
     *
     * @param content 待加密信息
     * @param secretKey 密钥
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static byte[] encrypt(String content, SecretKey secretKey)
            throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        return aes(content.getBytes(charset),Cipher.ENCRYPT_MODE,secretKey);
    }

    private static byte[] aes(byte[] contentArray, int mode, SecretKey secretKey) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(mode, secretKey);
        byte[] result = cipher.doFinal(contentArray);
        return result;
    }
}
