package com.itgo.util.web;


import com.itgo.util.encrypt.EncryptBase64Util;

import java.io.UnsupportedEncodingException;
import java.util.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Create by xigexb
 *
 * @versio 1.0.0
 * @Author xigexb
 * @date 2019/5/30 13:23
 * @description desc:Http与Servlet工具类.
 */
public class ServletUtil {

    //-- Content Type 定义 --//
    public static final String EXCEL_TYPE = "application/vnd.ms-excel";
    public static final String HTML_TYPE = "text/html";
    public static final String JS_TYPE = "text/javascript";
    public static final String JSON_TYPE = "application/json";
    public static final String XML_TYPE = "text/xml";
    public static final String TEXT_TYPE = "text/plain";

    //-- Header 定义 --//
    public static final String AUTHENTICATION_HEADER = "Authorization";

    //-- 常用数值定义 --//
    public static final long ONE_YEAR_SECONDS = 60 * 60 * 24 * 365;

    /**
     * 设置客户端缓存过期时间 的Header.
     * @param response
     * @param expiresSeconds
     */
    public static void setExpiresHeader(HttpServletResponse response, long expiresSeconds) {
        //Http 1.0 header
        response.setDateHeader("Expires", System.currentTimeMillis() + expiresSeconds * 1000);
        //Http 1.1 header
        response.setHeader("Cache-Control", "private, max-age=" + expiresSeconds);
    }

    /**
     * 设置禁止客户端缓存的Header.
     */
    public static void setDisableCacheHeader(HttpServletResponse response) {
        //Http 1.0 header
        response.setDateHeader("Expires", 1L);
        response.addHeader("Pragma", "no-cache");
        //Http 1.1 header
        response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
    }

    /**
     * 设置LastModified Header.
     */
    public static void setLastModifiedHeader(HttpServletResponse response, long lastModifiedDate) {
        response.setDateHeader("Last-Modified", lastModifiedDate);
    }

    /**
     * 设置Etag Header.
     */
    public static void setEtag(HttpServletResponse response, String etag) {
        response.setHeader("ETag", etag);
    }

    /**
     * 根据浏览器If-Modified-Since Header, 计算文件是否已被修改.
     * <p>
     * 如果无修改, checkIfModify返回false ,设置304 not modify status.
     *
     * @param lastModified 内容的最后修改时间.
     */
    public static boolean checkIfModifiedSince(HttpServletRequest request, HttpServletResponse response,
                                               long lastModified) {
        long ifModifiedSince = request.getDateHeader("If-Modified-Since");
        if ((ifModifiedSince != -1) && (lastModified < ifModifiedSince + 1000)) {
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            return false;
        }
        return true;
    }

    /**
     * 根据浏览器 If-None-Match Header, 计算Etag是否已无效.
     * <p>
     * 如果Etag有效, checkIfNoneMatch返回false, 设置304 not modify status.
     *
     * @param etag 内容的ETag.
     */
    public static boolean checkIfNoneMatchEtag(HttpServletRequest request, HttpServletResponse response, String etag) {
        String headerValue = request.getHeader("If-None-Match");
        if (headerValue != null) {
            boolean conditionSatisfied = false;
            if (!"*".equals(headerValue)) {
                StringTokenizer commaTokenizer = new StringTokenizer(headerValue, ",");

                while (!conditionSatisfied && commaTokenizer.hasMoreTokens()) {
                    String currentToken = commaTokenizer.nextToken();
                    if (currentToken.trim().equals(etag)) {
                        conditionSatisfied = true;
                    }
                }
            } else {
                conditionSatisfied = true;
            }

            if (conditionSatisfied) {
                response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                response.setHeader("ETag", etag);
                return false;
            }
        }
        return true;
    }

    /**
     * 设置让浏览器弹出下载对话框的Header.
     *
     * @param fileName 下载后的文件名.
     */
    public static void setFileDownloadHeader(HttpServletResponse response, String fileName) {
        try {
            //中文文件名支持
            String encodedfileName = new String(fileName.getBytes(), "ISO8859-1");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + "\"");
        } catch (UnsupportedEncodingException e) {
        }
    }

    /**
     * 取得带相同前缀的Request Parameters.
     * 返回的结果的Parameter名已去除前缀.
     */
    public static Map<String, Object> getParametersStartingWith(ServletRequest request, String prefix) {
        if(request == null ){
            return null;
        }
        Enumeration paramNames = request.getParameterNames();
        Map<String, Object> params = new TreeMap<String, Object>();
        if (prefix == null) {
            prefix = "";
        }
        while (paramNames != null && paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            if ("".equals(prefix) || paramName.startsWith(prefix)) {
                String unprefixed = paramName.substring(prefix.length());
                String[] values = request.getParameterValues(paramName);
                if (values == null || values.length == 0) {
                    // Do nothing, no values found at all.
                } else if (values.length > 1) {
                    params.put(unprefixed, values);
                } else {
                    params.put(unprefixed, values[0]);
                }
            }
        }
        return params;
    }

    /**
     * 对Http Basic验证的 Header进行编码.
     */
    public static String encodeHttpBasic(String userName, String password) {
        String encode = userName + ":" + password;
        return "Basic " + EncryptBase64Util.encode(encode);
    }


    /**
     * 获取完整请求路径，带请求参数
     *
     * @param request
     * @return
     */
    public static String getFullPath(HttpServletRequest request) {
        StringBuffer uri = request.getRequestURL();
        String url = uri.toString();
        // 获取所有请求,返回 Map 集合
        Map<String, String[]> map = request.getParameterMap();
        Set<Map.Entry<String, String[]>> entry = map.entrySet();
        Iterator<Map.Entry<String, String[]>> iterator = entry.iterator();

        // 遍历集合
        StringBuffer sb = new StringBuffer();
        while (iterator.hasNext()) {
            Map.Entry<String, String[]> item = iterator.next();
            //请求名
            String key = item.getKey();
            //请求值
            for (String value : item.getValue()) {
                // 拼接每个请求参数 key=value&
                sb.append(key + "=" + value + "&");
            }
        }

        String string = sb.toString();
        // 拼接 URL, URL?key=value&key=value& 并且去掉最后一个 &
        url = url + "?" + string.substring(0, string.lastIndexOf("&"));
        return url;
    }
}