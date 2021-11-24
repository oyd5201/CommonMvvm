package com.kunminx.architecture.utils;

import android.text.TextUtils;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author oyd
 * @since 2021/11/22
 */

public class SignUtils {
    private static final String SIGN_METHOD_MD5 = "MD5";
    private static final String SIGN_METHOD_HMAC = "HMAC_MD5";
    private static final String CHARSET_UTF8 = "utf-8";
    public static String APP_SEC = "android*c";
    public static String signTopRequest(Map<String, String> params) throws IOException {
        return signTopRequest(params, APP_SEC, SIGN_METHOD_MD5);
    }

    public static String signTopRequest(Map<String, String> params, String secret, String signMethod) throws IOException {
        // 第一步：检查参数是否已经排序
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);

        // 第二步：把所有参数名和参数值串在一起
        StringBuilder query = new StringBuilder();
        //if (SIGN_METHOD_MD5.equals(signMethod)) {
        //    query.append(secret);
        //}
        for (String key : keys) {
            String value = params.get(key);
            if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value) && !TextUtils.isEmpty(value.trim())) {
                // 长字符串和emoji不参与
                if (!"message".equalsIgnoreCase(key)
                        && !"goodsName".equalsIgnoreCase(key)
                        && !"itemName".equalsIgnoreCase(key)
                        && !"content".equalsIgnoreCase(key)
                        && !"descript".equalsIgnoreCase(key)
                        && !"callback".equalsIgnoreCase(key)
                        && !"file_id".equalsIgnoreCase(key)
                        && !"message".equalsIgnoreCase(key)
                        && !"title".equalsIgnoreCase(key)) {
                    query.append(key).append(value);
                }
            }
        }

        // 第三步：使用MD5/HMAC加密
        byte[] bytes;
        if (SIGN_METHOD_HMAC.equals(signMethod)) {
            bytes = encryptHMAC(query.toString(), secret);
        } else {
            bytes = encryptMD5(query.toString());
            query = new StringBuilder(byte2hex(bytes));
            query.append(secret);
            bytes = encryptMD5(query.toString());
        }

        // 第四步：把二进制转化为大写的十六进制
        return byte2hex(bytes);
    }

    public static byte[] encryptHMAC(String data, String secret) throws IOException {
        byte[] bytes = null;
        try {
            SecretKey secretKey = new SecretKeySpec(secret.getBytes(CHARSET_UTF8), "HmacMD5");
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            bytes = mac.doFinal(data.getBytes(CHARSET_UTF8));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse.toString());
        }
        return bytes;
    }


    private static byte[] encryptMD5(String data) throws IOException {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            bytes = md.digest(data.getBytes(CHARSET_UTF8));
        } catch (GeneralSecurityException gse) {
            String msg = getStringFromException(gse);
            throw new IOException(msg);
        }
        return bytes;
    }

    public static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            //paySign.append(hex.toUpperCase());
            sign.append(hex);
        }
        return sign.toString();
    }

    private static String getStringFromException(Throwable e) {
        String result = "";
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(bos);
        e.printStackTrace(ps);
        try {
            result = bos.toString(CHARSET_UTF8);
        } catch (IOException ioe) {
        }
        return result;
    }


    /**
     * 验证TOP回调地址的签名是否合法。要求所有参数均为已URL反编码的。
     *
     * @param topParams  TOP私有参数（未经BASE64解密）
     * @param topSession TOP私有会话码
     * @param topSign    TOP回调签名
     * @param appKey     应用公钥
     * @param appSecret  应用密钥
     * @return 验证成功返回true，否则返回false
     * @throws IOException
     */
    public static boolean verifyTopResponse(String topParams, String topSession, String topSign,
                                            String appKey, String appSecret) throws IOException {
        StringBuilder result = new StringBuilder();
        result.append(appKey).append(topParams).append(topSession).append(appSecret);
        byte[] bytes = encryptMD5(result.toString());
        //Base64 encoder = new Base64();
        return new String(bytes).equals(topSign);
    }
}
