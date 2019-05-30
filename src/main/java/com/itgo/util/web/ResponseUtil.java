package com.itgo.util.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Create by xigexb
 *
 * @versio 1.0.0
 * @Author xigexb
 * @date 2019/5/30 13:30
 * @description desc: Response工具类
 */
public class ResponseUtil {

    public static final Logger logger = LoggerFactory.getLogger(ResponseUtil.class);

    /**
     * 发送文本。使用UTF-8编码。
     *
     * @param response
     *            HttpServletResponse
     * @param text
     *            发送的字符串
     */
    public static void renderText(HttpServletResponse response, String text) {
        render(response, "text/plain;charset=UTF-8", text);
    }

    /**
     * 发送json。使用UTF-8编码。
     *
     * @param response
     *            HttpServletResponse
     * @param text
     *            发送的字符串
     */
    public static void renderJson(HttpServletResponse response, String text) {
        render(response, "application/json;charset=UTF-8", text);
    }

    /**
     * 发送xml。使用UTF-8编码。
     *
     * @param response
     *            HttpServletResponse
     * @param text
     *            发送的字符串
     */
    public static void renderXml(HttpServletResponse response, String text) {
        render(response, "text/xml;charset=UTF-8", text);
    }

    /**
     * 发送内容。使用UTF-8编码。
     *
     * @param response
     * @param contentType
     * @param text
     */
    public static void render(HttpServletResponse response, String contentType,
                              String text) {
        response.setContentType(contentType);
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        try {
            response.getWriter().write(text);
            response.getWriter().flush();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
