package com.itgo.util.web;

import com.alibaba.fastjson.JSONObject;
import com.itgo.util.GsonUtil;
import com.itgo.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Create by xigexb
 *
 * @versio 1.0.0
 * @Author xigexb
 * @date 2019/5/30 13:36
 * @description
 *      desc: HttpU请求工具类
 */
public class HttpUtil {

    public static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private static final int READ_TIMEOUT = 60000;

    private static final int CONNECT_TIMEOUT = 60000;

    /**
     * http get request get请求
     * @param urlAddr address
     * @param paramsMap params
     * @param connectTimeout timeout
     * @param readTimeout readTimeout
     * @return
     * @throws Exception
     */
    public static String get(String urlAddr, Map<String, Object> paramsMap, int connectTimeout, int readTimeout) throws Exception {
        logger.info("get request url: {}, params: {}", urlAddr, GsonUtil.toJson(paramsMap));
        String line;
        String params = "";
        HttpURLConnection conn = null;
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        StringBuffer result = new StringBuffer();
        try {
            if (connectTimeout < 1) {
                connectTimeout = CONNECT_TIMEOUT;
            }
            if (readTimeout < 1) {
                readTimeout = READ_TIMEOUT;
            }
            if (paramsMap != null && !paramsMap.isEmpty()) {
                StringBuffer str = new StringBuffer();
                Set set = paramsMap.keySet();
                Iterator iter = set.iterator();
                while (iter.hasNext()) {
                    String key = iter.next().toString();
                    if (paramsMap.get(key) == null) {
                        continue;
                    }
                    str.append(key).append("=").append(paramsMap.get(key)).append("&");
                }
                if (str.length() > 0) {
                    params = "?" + str.substring(0, str.length() - 1);
                }
            }
            URL url = new URL(urlAddr + params);
            conn = (HttpURLConnection) url.openConnection();
            // 设置读取超时时间
            conn.setReadTimeout(readTimeout);
            // 设置连接超时时间
            conn.setConnectTimeout(connectTimeout);
            conn.connect();
            inputStreamReader = new InputStreamReader(conn.getInputStream(), "utf-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result.toString();
    }


    /**
     * http get request post请求
     * @param urlAddr address
     * @param paramsMap params
     * @param connectTimeout timeout
     * @param readTimeout readTimeout
     * @return
     * @throws Exception
     */
    public static String post(String urlAddr, Map<String, Object> paramsMap, int connectTimeout, int readTimeout) throws Exception {
        logger.info("post request url: {}, params: {}", urlAddr, GsonUtil.toJson(paramsMap));
        String line;
        String params = "";
        HttpURLConnection conn = null;
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        StringBuffer result = new StringBuffer();
        try {
            if (connectTimeout < 1) {
                connectTimeout = CONNECT_TIMEOUT;
            }
            if (readTimeout < 1) {
                readTimeout = READ_TIMEOUT;
            }
            if (paramsMap != null && !paramsMap.isEmpty()) {
                StringBuffer str = new StringBuffer();
                Set set = paramsMap.keySet();
                Iterator iter = set.iterator();
                while (iter.hasNext()) {
                    String key = iter.next().toString();
                    if (paramsMap.get(key) == null) {
                        continue;
                    }
                    str.append(key).append("=").append(paramsMap.get(key)).append("&");
                }
                if (str.length() > 0) {
                    params = str.substring(0, str.length() - 1);
                }
            }
            URL url = new URL(urlAddr);
            conn = (HttpURLConnection) url.openConnection();
            // 设置读取超时时间
            conn.setReadTimeout(readTimeout);
            // 设置连接超时时间
            conn.setConnectTimeout(connectTimeout);
            // 设置是否向HttpURLConnection输出，因为这个是post请求，参数要放在http正文内，
            // 因此需要设为true, 默认情况下是false
            conn.setDoOutput(true);
            // 不使用缓存,默认情况下是true
            conn.setUseCaches(false);
            // 设定请求的方法为"POST",默认是GET
            conn.setRequestMethod("POST");
            if (!params.isEmpty()) {
                // 此处getOutputStream会隐含的进行connect()
                outputStreamWriter = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
                // 写入
                outputStreamWriter.write(params);
                // 刷新该流的缓冲
                outputStreamWriter.flush();
            }
            inputStreamReader = new InputStreamReader(conn.getInputStream(), "utf-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (outputStreamWriter != null) {
                try {
                    outputStreamWriter.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result.toString();
    }


    /**
     * http get request get-ssl连接
     * @param urlAddr address
     * @param paramsMap params
     * @param connectTimeout timeout
     * @param readTimeout readTimeout
     * @return
     * @throws Exception
     */
    public static String getSSL(String urlAddr, Map<String, Object> paramsMap, int connectTimeout, int readTimeout) throws Exception {
        logger.info("get request url: {}, params: {}", urlAddr, GsonUtil.toJson(paramsMap));
        String line;
        String params = "";
        HttpsURLConnection conn = null;
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        StringBuffer result = new StringBuffer();
        TrustManager[] trustManager = {new DefaultX509TrustManager()};
        // 创建SSLContext
        SSLContext sslContext = SSLContext.getInstance("SSL");
        // 初始化
        sslContext.init(null, trustManager, new java.security.SecureRandom());
        // 获取SSLSocketFactory对象
        SSLSocketFactory ssf = sslContext.getSocketFactory();
        try {
            if (connectTimeout < 1) {
                connectTimeout = CONNECT_TIMEOUT;
            }
            if (readTimeout < 1) {
                readTimeout = READ_TIMEOUT;
            }
            if (paramsMap != null && !paramsMap.isEmpty()) {
                StringBuffer str = new StringBuffer();
                Set set = paramsMap.keySet();
                Iterator iter = set.iterator();
                while (iter.hasNext()) {
                    String key = iter.next().toString();
                    if (paramsMap.get(key) == null) {
                        continue;
                    }
                    str.append(key).append("=").append(paramsMap.get(key)).append("&");
                }
                if (str.length() > 0) {
                    params = "?" + str.substring(0, str.length() - 1);
                }
            }
            URL url = new URL(urlAddr + params);
            conn = (HttpsURLConnection) url.openConnection();
            // 设置读取超时时间
            conn.setReadTimeout(readTimeout);
            // 设置连接超时时间
            conn.setConnectTimeout(connectTimeout);
            // 设置当前实例使用的SSLSocketFactory
            conn.setSSLSocketFactory(ssf);
            conn.connect();
            inputStreamReader = new InputStreamReader(conn.getInputStream(), "utf-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result.toString();
    }
    /**
     * http get request post-ssl连接
     * @param urlAddr address
     * @param paramsMap params
     * @param connectTimeout timeout
     * @param readTimeout readTimeout
     * @return
     * @throws Exception
     */
    public static String postSSL(String urlAddr, Map<String, Object> paramsMap, int connectTimeout, int readTimeout) throws Exception {
        logger.info("post request url: {}, params: {}", urlAddr, GsonUtil.toJson(paramsMap));
        String line;
        String params = "";
        HttpsURLConnection conn = null;
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        StringBuffer result = new StringBuffer();
        TrustManager[] trustManager = {new DefaultX509TrustManager()};
        // 创建SSLContext
        SSLContext sslContext = SSLContext.getInstance("SSL");
        // 初始化
        sslContext.init(null, trustManager, new java.security.SecureRandom());
        // 获取SSLSocketFactory对象
        SSLSocketFactory ssf = sslContext.getSocketFactory();
        try {
            if (connectTimeout < 1) {
                connectTimeout = CONNECT_TIMEOUT;
            }
            if (readTimeout < 1) {
                readTimeout = READ_TIMEOUT;
            }
            if (paramsMap != null && !paramsMap.isEmpty()) {
                StringBuffer str = new StringBuffer();
                Set set = paramsMap.keySet();
                Iterator iter = set.iterator();
                while (iter.hasNext()) {
                    String key = iter.next().toString();
                    if (paramsMap.get(key) == null) {
                        continue;
                    }
                    str.append(key).append("=").append(paramsMap.get(key)).append("&");
                }
                if (str.length() > 0) {
                    params = str.substring(0, str.length() - 1);
                }
            }
            URL url = new URL(urlAddr);
            conn = (HttpsURLConnection) url.openConnection();
            // 设置读取超时时间
            conn.setReadTimeout(readTimeout);
            // 设置连接超时时间
            conn.setConnectTimeout(connectTimeout);
            // 设置当前实例使用的SSLSocketFactory
            conn.setSSLSocketFactory(ssf);
            // 设置是否向HttpURLConnection输出，因为这个是post请求，参数要放在http正文内，
            // 因此需要设为true, 默认情况下是false
            conn.setDoOutput(true);
            // 不使用缓存,默认情况下是true
            conn.setUseCaches(false);
            // 设定请求的方法为"POST",默认是GET
            conn.setRequestMethod("POST");
            if (!params.isEmpty()) {
                // 此处getOutputStream会隐含的进行connect()
                outputStreamWriter = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
                // 写入
                outputStreamWriter.write(params);
                // 刷新该流的缓冲
                outputStreamWriter.flush();
            }
            inputStreamReader = new InputStreamReader(conn.getInputStream(), "utf-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (outputStreamWriter != null) {
                try {
                    outputStreamWriter.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result.toString();
    }

    /**
     * http get request get-ssl连接
     * @param urlAddr address 地址
     * @param paramsStr 参数
     * @param connectTimeout timeout 连接超时时间
     * @param readTimeout readTimeout 响应超时时间
     * @return
     * @throws Exception
     */
    public static String post(String urlAddr, String paramsStr, int connectTimeout, int readTimeout) throws Exception {
        logger.info("post request url: {}, params: {}", urlAddr, paramsStr);
        String line;
        HttpURLConnection conn = null;
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        StringBuffer result = new StringBuffer();
        try {
            if (connectTimeout < 1) {
                connectTimeout = CONNECT_TIMEOUT;
            }
            if (readTimeout < 1) {
                readTimeout = READ_TIMEOUT;
            }
            URL url = new URL(urlAddr);
            conn = (HttpURLConnection) url.openConnection();
            // 设置读取超时时间
            conn.setReadTimeout(readTimeout);
            // 设置连接超时时间
            conn.setConnectTimeout(connectTimeout);
            // 设置是否向HttpURLConnection输出，因为这个是post请求，参数要放在http正文内，
            // 因此需要设为true, 默认情况下是false
            conn.setDoOutput(true);
            // 不使用缓存,默认情况下是true
            conn.setUseCaches(false);
            // 设定请求的方法为"POST",默认是GET
            conn.setRequestMethod("POST");
            if (paramsStr != null && !paramsStr.isEmpty()) {
                // 此处getOutputStream会隐含的进行connect()
                outputStreamWriter = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
                // 写入
                outputStreamWriter.write(paramsStr);
                // 刷新该流的缓冲
                outputStreamWriter.flush();
            }
            inputStreamReader = new InputStreamReader(conn.getInputStream(), "utf-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (outputStreamWriter != null) {
                try {
                    outputStreamWriter.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result.toString();
    }


    /**
     *
     * @param urlAddr
     * @param paramsStr
     * @param connectTimeout
     * @param readTimeout
     * @return
     * @throws Exception
     */
    public static String postSSL(String urlAddr, String paramsStr, int connectTimeout, int readTimeout) throws Exception {
        logger.info("post request url: {}, params: {}", urlAddr, paramsStr);
        String line;
        HttpsURLConnection conn = null;
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        StringBuffer result = new StringBuffer();
        TrustManager[] trustManager = {new DefaultX509TrustManager()};
        // 创建SSLContext
        SSLContext sslContext = SSLContext.getInstance("SSL");
        // 初始化
        sslContext.init(null, trustManager, new java.security.SecureRandom());
        // 获取SSLSocketFactory对象
        SSLSocketFactory ssf = sslContext.getSocketFactory();
        try {
            if (connectTimeout < 1) {
                connectTimeout = CONNECT_TIMEOUT;
            }
            if (readTimeout < 1) {
                readTimeout = READ_TIMEOUT;
            }
            URL url = new URL(urlAddr);
            conn = (HttpsURLConnection) url.openConnection();
            // 设置读取超时时间
            conn.setReadTimeout(readTimeout);
            // 设置连接超时时间
            conn.setConnectTimeout(connectTimeout);
            // 设置当前实例使用的SSLSocketFactory
            conn.setSSLSocketFactory(ssf);
            // 设置是否向HttpURLConnection输出，因为这个是post请求，参数要放在http正文内，
            // 因此需要设为true, 默认情况下是false
            conn.setDoOutput(true);
            // 不使用缓存,默认情况下是true
            conn.setUseCaches(false);
            // 设定请求的方法为"POST",默认是GET
            conn.setRequestMethod("POST");
            if (paramsStr != null && !paramsStr.isEmpty()) {
                // 此处getOutputStream会隐含的进行connect()
                outputStreamWriter = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
                // 写入
                outputStreamWriter.write(paramsStr);
                // 刷新该流的缓冲
                outputStreamWriter.flush();
            }
            inputStreamReader = new InputStreamReader(conn.getInputStream(), "utf-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (outputStreamWriter != null) {
                try {
                    outputStreamWriter.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result.toString();
    }


    /**
     * ajax response 异步
     * @param res
     * @param response
     */
    public static void toJson(Object res, HttpServletResponse response) {
        PrintWriter writer = null;
        try {
            response.setContentType("application/json;charset=utf-8");
            writer = response.getWriter();
            writer.print(GsonUtil.toJson(res));
        } catch (Exception e) {
            logger.error("", e);
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 获取请求IP
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request) {
        // 网宿cdn的真实ip
        String ip = request.getHeader("Cdn-Src-Ip");
        if (StringUtil.isEmpty(ip) || " unknown".equalsIgnoreCase(ip)) {
            // 蓝讯cdn的真实ip
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtil.isEmpty(ip) || " unknown".equalsIgnoreCase(ip)) {
            // 获取代理ip
            ip = request.getHeader("X-Forwarded-For");
        }
        if (StringUtil.isEmpty(ip) || " unknown".equalsIgnoreCase(ip)) {
            // 获取代理ip
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            // 获取代理ip
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            // 获取真实ip
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    /**
     * 根据IP获取地址
     */
    public static JSONObject getIpAddress(String ip) {
        if (ip == null || ip.trim().length() < 1) {
            return null;
        }
        String code = "0";
        String url = "http://ip.taobao.com/service/getIpInfo.php?ip=" + ip;
        try {
            String res = HttpUtil.get(url, null, 0, 0);
            JSONObject json = JSONObject.parseObject(res);
            String backCode = json.getString("code");
            if (code.equals(backCode)) {
                return json.getJSONObject("data");
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }


    /**
     * 下载
     * @param request
     * @param response
     * @param fileName
     * @throws Exception
     */
    public static void setDownLoadResponse(HttpServletRequest request, HttpServletResponse response, String fileName) throws Exception {
        String msie = "msie";
        String chrome = "chrome";
        String windows = "windows";
        String firefox = "firefox";
        String browserType = request.getHeader("User-Agent").toLowerCase();
        if (browserType.contains(firefox) || browserType.contains(chrome)) {
            fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
        } else if (browserType.contains(msie) || browserType.contains(windows)) {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } else {
            fileName = new String(fileName.getBytes());
        }
        // 重置
        response.reset();
        // 告知浏览器不缓存
        response.setHeader("pragma", "no-cache");
        response.setHeader("cache-control", "no-cache");
        response.setHeader("expires", "0");
        // 响应编码
        response.setCharacterEncoding("UTF-8");
        // 用给定的名称和值添加一个响应头
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
    }


    /**
     * 获取输入流中的字符串
     * @param request
     * @return
     */
    public static String getInputStream(HttpServletRequest request) {
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        ServletInputStream servletInputStream = null;
        StringBuffer result = new StringBuffer();
        try {
            String line;
            servletInputStream = request.getInputStream();
            inputStreamReader = new InputStreamReader(servletInputStream, "utf-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            logger.error("", e);
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (servletInputStream != null) {
                try {
                    servletInputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result.toString();
    }
}
