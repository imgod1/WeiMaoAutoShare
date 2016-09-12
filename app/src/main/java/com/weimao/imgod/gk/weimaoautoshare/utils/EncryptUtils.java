package com.weimao.imgod.gk.weimaoautoshare.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * 加密工具类
 */
public class EncryptUtils {

    private RSAPrivateKey privateKey;

    private RSAPublicKey publicKey;

    private static class LazyHolder {
        private static final EncryptUtils INSTANCE = new EncryptUtils();
    }

    private EncryptUtils() {
        try {
            //KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            //初始化密钥对生成器，密钥大小为1024位
            keyPairGen.initialize(1024);
            //生成一个密钥对，保存在keyPair中
            KeyPair keyPair = keyPairGen.generateKeyPair();
            //得到私钥
            privateKey = (RSAPrivateKey) keyPair.getPrivate();
            //得到公钥
            publicKey = (RSAPublicKey) keyPair.getPublic();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static EncryptUtils getInstance() {
        return LazyHolder.INSTANCE;
    }

    /**
     * MD5加密
     *
     * @param info 明文字符串
     * @return 密文字符串
     */
    public String encryptByMD5(String info) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(info.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString().toUpperCase();
    }


    //把字节转换为字符串
    private String transformBytes(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bytes) {
            stringBuilder.append(b);
        }
        return stringBuilder.toString();
    }


    /**
     * RSA加密
     *
     * @param info 明文字符串
     * @return 字节数组
     */
    public byte[] encryptByRSA(String info) {
        if (publicKey != null) {
            try {
                byte[] srcBytes = info.getBytes();
                Cipher cipher = Cipher.getInstance("RSA");
                cipher.init(Cipher.ENCRYPT_MODE, publicKey);
                return cipher.doFinal(srcBytes);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * RSA解密
     *
     * @param srcBytes 密文字符串
     * @return 明文字符串
     */
    public String decrypt(byte[] srcBytes) {
        String result = "";
        if (privateKey != null) {
            try {
                Cipher cipher = Cipher.getInstance("RSA");
                cipher.init(Cipher.DECRYPT_MODE, privateKey);
                result = new String(cipher.doFinal(srcBytes));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
