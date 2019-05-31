package com.itgo.util.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Create by xb
 * The file is [ UrlCodecUtil] on [ case ] project
 * The file path is com.itgo.util.web.UrlCodecUtil
 *
 * @versio 1.0.o
 * @Author he ming xi
 * @date 2019/3/23 15:15
 * @description
 */
public class UrlCodecUtil {

    /**
     * URL 编码
     * @param url
     * @return
     */
    public static String encodeURL(String url){
        String target = null;
        try {
            target = URLEncoder.encode(url,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return target;
    }


    /**
     * URL 解码
     * @param url
     * @return
     */
    public static String dencodeURL(String url){
        String target = null;
        try {
            target = URLDecoder.decode(url,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return target;
    }
}
