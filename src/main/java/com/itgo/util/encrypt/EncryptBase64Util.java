package com.itgo.util.encrypt;

import org.apache.commons.codec.binary.Base64;

/**
 * Create by xigexb
 *
 * @versio 1.0.0
 * @Author xigexb
 * @date 2019/5/12 11:26
 * @description desc:
 */
public class EncryptBase64Util {


    /**
     * 解码
     * @param str
     * @return
     */
    public static String decode(String str) {
        return new String(Base64.decodeBase64(str));
    }

    /**
     * 编码
     * @param str
     * @return
     */
    public static String encode(String str) {
        return new String(Base64.encodeBase64(str.getBytes()));
    }
}

