package com.itgo.util.encrypt;

import com.itgo.util.CastUtil;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * Create by xigexb
 *
 * @versio 1.0.0
 * @Author xigexb
 * @date 2019/5/12 11:29
 * @description desc:
 */
public class EncryptRSAUtil {

    /**
     * 随机生成密钥对
     * @param length 密钥大小为96-1024位
     * @throws NoSuchAlgorithmException
     * @return map 0 公钥 1 私钥
     */
    public static Map<Integer,String> createKey(int length){
        if(length>1024 ||  length<96){
            return null;
        }
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        Map<Integer,String> map = new HashMap<>();
        try {
//            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
//            // 初始化密钥对生成器，密钥大小为96-1024位
//            keyPairGen.initialize(length,new SecureRandom());
//            // 生成一个密钥对，保存在keyPair中
//            KeyPair keyPair = keyPairGen.generateKeyPair();
//            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
//            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公
//            String pub =new BigInteger(CastUtil.str2HexStr(CastUtil.str2HexStr(new String(Base64.encodeBase64((publicKey.getEncoded())))))).toString(16);
//            String pri = new BigInteger(CastUtil.str2HexStr(CastUtil.str2HexStr(new String(Base64.encodeBase64((privateKey.getEncoded())))))).toString(16) ;
//            byte[] p0 = Base64.encodeBase64(pub.getBytes("UTF-8"));
//            byte[] p1 = Base64.encodeBase64(pri.getBytes("UTF-8"));
//            map.put(0,new String(p0));
//            map.put(1,new String(p1));
            // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            // 初始化密钥对生成器，密钥大小为96-1024位
            keyPairGen.initialize(1024,new SecureRandom());
            // 生成一个密钥对，保存在keyPair中
            KeyPair keyPair = keyPairGen.generateKeyPair();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
            String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
            // 得到私钥字符串
            String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
            // 将公钥和私钥保存到Map
            map.put(0,publicKeyString);  //0表示公钥
            map.put(1,privateKeyString);  //1表示私钥
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * RSA公钥加密
     *
     * @param str
     *            加密字符串
     * @param publicKey
     *            公钥
     * @return 密文
     * @throws Exception
     *             加密过程中的异常信息
     */
    public static String encrypt( String str, String publicKey ) {
        String outStr = null;
        try {
            //base64编码的公钥
            byte[] decoded = Base64.decodeBase64(publicKey);
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
            //RSA加密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
        }catch (Exception e){
            e.printStackTrace();
        }
        return outStr;
    }

    /**
     * RSA私钥解密
     *
     * @param str
     *            加密字符串
     * @param privateKey
     *            私钥
     * @return 铭文
     * @throws Exception
     *             解密过程中的异常信息
     */
    public static String decrypt(String str, String privateKey) {
        String outStr = null;
        try {
            //64位解码加密后的字符串
            byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
            //base64编码的私钥
            byte[] decoded = Base64.decodeBase64(privateKey);
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
            //RSA解密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            outStr = new String(cipher.doFinal(inputByte));
        }catch (Exception e){
            e.printStackTrace();
        }
        return outStr;
    }



}
