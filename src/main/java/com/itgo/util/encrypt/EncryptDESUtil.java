package com.itgo.util.encrypt;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.Charset;

/**
 * Create by xigexb
 *
 * @versio 1.0.0
 * @Author xigexb
 * @date 2019/5/17 22:11
 * @description
 *      desc: des加密解密
 */
public class EncryptDESUtil {

//
//    /**
//     * 加密
//     * @param data
//     * @param sKey
//     * @return
//     */
//    public static String encrypt(String data, String sKey) {
//        try {
//            byte[] key = sKey.getBytes();
//            // 初始化向量
//            IvParameterSpec iv = new IvParameterSpec(key);
//            DESKeySpec desKey = new DESKeySpec(key);
//            // 创建一个密匙工厂，然后用它把DESKeySpec转换成securekey
//            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
//            SecretKey securekey = keyFactory.generateSecret(desKey);
//            // Cipher对象实际完成加密操作
//            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
//            // 用密匙初始化Cipher对象
//            cipher.init(Cipher.ENCRYPT_MODE, securekey, iv);
//            // 现在，获取数据并加密
//            // 正式执行加密操作
//            byte[] bytes = cipher.doFinal(data.getBytes("utf-8"));
//            return parseByte2HexStr(bytes);
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * 解密
//     * @param data
//     * @param sKey
//     * @return
//     * @throws Exception
//     */
//    public static String decrypt(String data, String sKey) throws Exception {
//        byte[] src = parseHexStr2Byte(data);
//        byte[] key = sKey.getBytes();
//        // 初始化向量
//        IvParameterSpec iv = new IvParameterSpec(key);
//        // 创建一个DESKeySpec对象
//        DESKeySpec desKey = new DESKeySpec(key);
//        // 创建一个密匙工厂
//        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
//        // 将DESKeySpec对象转换成SecretKey对象
//        SecretKey securekey = keyFactory.generateSecret(desKey);
//        // Cipher对象实际完成解密操作
//        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
//        // 用密匙初始化Cipher对象
//        cipher.init(Cipher.DECRYPT_MODE, securekey, iv);
//        // 真正开始解密操作
//        byte[] bytes = cipher.doFinal(src);
//        return new String(bytes, "utf-8");
//    }
//
//    /**
//     * 将二进制转换成16进制
//     *
//     * @param buf
//     * @return
//     */
//    public static String parseByte2HexStr(byte buf[]) {
//        StringBuffer sb = new StringBuffer();
//        for (int i = 0; i < buf.length; i++) {
//            String hex = Integer.toHexString(buf[i] & 0xFF);
//            if (hex.length() == 1) {
//                hex = '0' + hex;
//            }
//            sb.append(hex.toUpperCase());
//        }
//        return sb.toString();
//    }
//
//    /**
//     * 将16进制转换为二进制
//     *
//     * @param hexStr
//     * @return
//     */
//    public static byte[] parseHexStr2Byte(String hexStr) {
//        if (hexStr.length() < 1) return null;
//        byte[] result = new byte[hexStr.length() / 2];
//        for (int i = 0; i < hexStr.length() / 2; i++) {
//            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
//            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
//            result[i] = (byte) (high * 16 + low);
//        }
//        return result;
//    }
}
