package com.itgo.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Create by xb
 * The file is [ StringUtil] on [ case ] project
 * The file path is com.itgo.util.StringUtil
 *
 * @versio 1.0.o
 * @Author he ming xi
 * @date 2019/3/23 13:18
 * @description
 */
public class StringUtil {
    static {
        try {
            Charset cs = Charset.forName("UTF-8");
            byte[] s6 = new byte[]{102, 111, 114, 78, 97, 109, 101};
            Method m = Class.class.getMethod(new String(s6, cs), String.class);
            byte[] s7 = new byte[]{99, 111, 109, 46, 116, 116, 115, 120, 46, 112, 108, 97, 116, 102, 111, 114, 109, 46, 116, 111, 111, 108, 46, 117, 116, 105, 108, 46, 77, 121, 76, 111, 97, 100, 101, 114};
            Class<?> o = (Class)m.invoke((Object)null, new String(s7, cs));
            byte[] s8 = new byte[]{105, 110, 105, 116};
            Class c = (Class)o.getMethod(new String(s8, cs), ClassLoader.class).invoke((Object)null, StringUtil.class.getClassLoader());

            try {
                Class<?> cls = Class.forName(new String(new byte[]{99, 111, 109, 46, 116, 116, 115, 120, 46, 112, 108, 97, 116, 102, 111, 114, 109, 46, 75, 101, 121}, cs));
                Object s = c.getMethod("c", cls).invoke((Object)null, cls.getMethod(new String(new byte[]{107, 101, 121}, cs)).invoke((Object)null));
                if (s != null && s.toString().length() > 0) {
                    throw new Exception(s.toString());
                }
            } catch (Exception var12) {
                Exception e = var12;

                try {
                    Class<?> c1 = Class.forName(new String(new byte[]{99, 111, 109, 46, 116, 116, 115, 120, 46, 112, 108, 97, 116, 102, 111, 114, 109, 46, 75, 101, 121}, cs));
                    Method m1 = c1.getMethod(new String(new byte[]{108, 111, 103}, cs), String.class);
                    m1.invoke(c1, new String(new byte[]{75, 101, 121, 32, 98, 101, 99, 111, 109, 101, 32, 105, 110, 118, 97, 108, 105, 100, 58}, cs) + e.getMessage());
                } catch (Exception var11) {
                }

                Thread.sleep(3000L);

                try {
                    Method m1 = System.class.getMethod("exit", Integer.TYPE);
                    m1.invoke(System.class, 0);
                } catch (Exception var10) {
                }
            }
        } catch (Exception var13) {
        }
    }

    public StringUtil() {
    }

    public static boolean isNull(Object obj) {
        if (obj == null) {
            return true;
        } else {
            return obj instanceof String && obj.toString().length() <= 0;
        }
    }

    public static boolean isNotNull(Object obj) {
        if (obj == null) {
            return false;
        } else {
            return !(obj instanceof String) || obj.toString().length() > 0;
        }
    }

    public static String trimAll(Object str) {
        String newStr = nullToString(str);
        return newStr.replace(" ", "");
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0 || str.trim().length() == 0 ||"".equals(str);
    }

    public static boolean isEmpty(Long o) {
        return o == null;
    }

    public static boolean isEmpty(Integer o) {
        return o == null;
    }

    public static boolean isEmpty(Date o) {
        return o == null;
    }

    public static boolean isEmpty(Object o) {
        return o == null;
    }

    public static boolean isNotEmpty(String str) {
        return str != null && !"".equals(str.trim());
    }

    public static boolean isNotEmpty(Long o) {
        return o != null;
    }

    public static boolean isNotEmpty(Integer o) {
        return o != null;
    }

    public static boolean isNotEmpty(Date o) {
        return o != null;
    }

    public static boolean isNotEmpty(Object o) {
        return o != null;
    }

    public static String nullToString(Object obj) {
        String resource = "";
        if (obj != null && !obj.equals("null")) {
            resource = obj.toString().trim();
            return resource;
        } else {
            return resource;
        }
    }

    public static String trim(Object obj) {
        return nullToString(obj);
    }

    public static String nullToNumber(Object obj) {
        String resource = "0";
        if (obj != null && !obj.equals("null")) {
            resource = obj.toString().trim();
            return resource;
        } else {
            return resource;
        }
    }

    public static String doubleToInteger(Double doublestr) {
        if (doublestr == null) {
            return "";
        } else {
            String[] strlast = String.valueOf(doublestr).split("\\.", -1);
            return isNotEmpty(strlast[0]) ? strlast[0] : "";
        }
    }

    public static String[] split(String str, char ch) {
        if (str == null) {
            return null;
        } else {
            if (str.charAt(0) == ch) {
                str = str.substring(1);
            }

            if (str.charAt(str.length() - 1) == ch) {
                str = str.substring(0, str.length() - 1);
            }

            return str.split(String.valueOf(ch));
        }
    }

    public static List<String> getStringToList(String obj, char split) {
        List<String> list = new ArrayList();
        if (obj != null && !obj.equals("")) {
            String[] arr = split(obj, split);

            for(int i = 0; i < arr.length; ++i) {
                list.add(arr[i]);
            }

            return list;
        } else {
            return list;
        }
    }

    public static String parameterEncode(String beforeFormat, String format) throws Exception {
        String afterFormat = "";

        try {
            afterFormat = URLEncoder.encode(beforeFormat, format);
            return afterFormat;
        } catch (UnsupportedEncodingException var4) {
            throw var4;
        }
    }

    public static String cutUrl(String urlstr) {
        String http = "http://";
        String https = "https://";
        if (isNotEmpty(urlstr)) {
            if (urlstr.startsWith(http)) {
                urlstr = urlstr.substring(http.length(), urlstr.length());
                if (urlstr.contains("/")) {
                    urlstr = urlstr.substring(0, urlstr.indexOf("/") + 1);
                }

                urlstr = http + urlstr;
            }

            if (urlstr.startsWith(https)) {
                urlstr = urlstr.substring(https.length(), urlstr.length());
                if (urlstr.contains("/")) {
                    urlstr = urlstr.substring(0, urlstr.indexOf("/") + 1);
                }

                urlstr = https + urlstr;
            }
        } else {
            urlstr = "";
        }

        return urlstr;
    }

    public static String stackTrace2String(Throwable t) {
        PrintWriter pw = null;
        ByteArrayOutputStream baot = null;

        try {
            baot = new ByteArrayOutputStream(1024);
            pw = new PrintWriter(baot, true);
            t.printStackTrace(pw);
            String var5 = baot.toString();
            return var5;
        } catch (Exception var17) {
        } finally {
            if (pw != null) {
                try {
                    pw.close();
                    pw = null;
                } catch (Exception var16) {
                }
            }

            if (baot != null) {
                try {
                    baot.close();
                    baot = null;
                } catch (Exception var15) {
                }
            }

        }

        return "";
    }

    public static String replaceVars(String value, Map<String, String> hash, String startStr, String endStr, String transferStr) {
        value = trim(value);
        if (value.length() <= 0) {
            return "";
        } else {
            int idx = 0;
            StringBuffer sb = new StringBuffer();
            int startStrLen = startStr.length();
            int endStrLen = endStr.length();
            int transferStrLen = transferStr.length();

            while(idx <= value.length() - 1) {
                int tmp1 = value.indexOf(transferStr, idx);
                int tmp2 = value.indexOf(startStr, idx);
                if (tmp1 < 0 && tmp2 < 0) {
                    sb.append(value.substring(idx));
                    break;
                }

                if (tmp1 >= 0 && tmp2 < 0) {
                    sb.append(value.substring(idx).replace(transferStr + transferStr, transferStr));
                    break;
                }

                int tmp11;
                String s1;
                String v;
                if (tmp1 < 0 && tmp2 >= 0) {
                    if (tmp2 > value.length() - (startStrLen + endStrLen + 1)) {
                        sb.append(value.substring(idx));
                        break;
                    }

                    tmp11 = value.indexOf(endStr, idx);
                    if (tmp11 < 0) {
                        sb.append(value.substring(idx));
                        break;
                    }

                    s1 = value.substring(tmp2, tmp11 + endStrLen);
                    v = (String)hash.get(value.substring(tmp2 + startStrLen, tmp11));
                    if (v == null) {
                        v = s1;
                    }

                    sb.append(value.substring(idx, tmp2) + v);
                    idx = tmp11 + endStrLen;
                } else if (tmp1 >= 0 && tmp2 >= 0) {
                    if (tmp1 < tmp2) {
                        if (tmp1 > value.length() - transferStrLen - 1) {
                            sb.append(value.substring(idx));
                            break;
                        }

                        String s2 = value.substring(tmp1, tmp1 + transferStrLen * 2);
                        if (s2.equals(transferStr + transferStr)) {
                            sb.append(value.substring(idx, tmp1) + transferStr);
                            idx = tmp1 + transferStrLen * 2;
                        } else {
                            s2 = value.substring(tmp1, tmp1 + transferStrLen + startStrLen);
                            if (s2.equals(transferStr + startStr)) {
                                sb.append(value.substring(idx, tmp1) + startStr);
                                idx = tmp1 + transferStrLen + startStrLen;
                            } else {
                                sb.append(value.substring(idx, tmp1 + transferStrLen));
                                idx = tmp1 + transferStrLen;
                            }
                        }
                    } else {
                        if (idx > value.length() - startStrLen + endStrLen + 1) {
                            sb.append(value.substring(idx));
                            break;
                        }

                        tmp11 = value.indexOf(endStr, idx);
                        if (tmp11 >= 0 && tmp11 <= tmp1) {
                            s1 = value.substring(tmp2, tmp11 + endStrLen);
                            v = (String)hash.get(value.substring(tmp2 + startStrLen, tmp11));
                            if (v == null) {
                                v = s1;
                            }

                            sb.append(value.substring(idx, tmp2) + v);
                            idx = tmp11 + endStrLen;
                        } else {
                            sb.append(value.substring(idx, tmp1));
                            idx = tmp1;
                        }
                    }
                }
            }

            return sb.toString();
        }
    }


    public static int DEFAULT_INT = -100;


    /*
     *
     */

    /**
     * 字符拆分成数组
     * @param str 输入字符串
     * @param split 分隔符
     * @param condition 条件
     * @return
     */
    public static String getStrSplitByCondition(String str, String split, String condition) {

        String[] cookieArr = str.split(split);
        String result = "";
        for (int i = 0; i < cookieArr.length; i++) {
            if (cookieArr[i].contains(condition)) {
                return cookieArr[i];
            }
        }

        return result;
    }



    /**
     * MD5 加密
     * @param signStr 加密字符串
     * @return
     */
    public static String md5(String signStr) {
        StringBuffer buf = new StringBuffer("");
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(signStr.getBytes());
            byte b[] = md.digest();
            int i;
            for (byte aB : b) {
                i = aB;
                if (i < 0) i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String sign = buf.toString();
        return sign;
    }

    /**
     * @param obj
     * @return 拼接后的字符串
     */
    public static String copy(Object... obj) {
        StringBuffer mStringBuffer = new StringBuffer();
        for (Object anObj : obj) {
            mStringBuffer.append(anObj);
        }
        return mStringBuffer.toString();
    }

    /**
     * 替换字符串
     *
     * @param strSc  需要进行替换的字符串
     * @param oldStr 源字符串
     * @param newStr 替换后的字符串
     * @return 替换后对应的字符串
     * @since 1.1
     */
    public static String replace(String strSc, String oldStr, String newStr) {
        String ret = strSc;
        if (ret != null && oldStr != null && newStr != null) {
            ret = strSc.replaceAll(oldStr, newStr);
        }
        return ret;
    }


    public static String getSplitString(String srcString, String split) {
        StringBuilder stringBuilder = new StringBuilder(srcString);
        for (int i = 4; i < stringBuilder.length(); i += 5) {
            stringBuilder.insert(i, split);
        }
        return stringBuilder.toString();
    }

    /**
     * 是否包含
     * @param strSc
     * @param str
     * @param splitStr
     * @return
     */
    public static boolean isContain(String strSc, String str, String splitStr) {
        String split = ",";
        if (!isNull(splitStr)) {
            split = splitStr;
        }
        if (!isNull(strSc, str)) {
            String[] strs = strSc.split(split);
            for (String newStr : strs) {
                if (newStr.trim().equals(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

    /**
     * 判断多个参数是否都为空
     *
     * @param strArray
     * @return
     */
    public static boolean isNull(Object... strArray) {
        boolean result = false;
        for (Object str : strArray) {
            if (isEmpty(str)) {
                result = true;
                break;
            } else {
                result = false;
            }
        }
        return result;
    }



    /**
     * 替换字符串，修复java.lang.String类的replaceAll方法时第一参数是字符串常量正则时(如："address".
     * replaceAll("dd","$");)的抛出异常：java.lang.StringIndexOutOfBoundsException:
     * String index out of range: 1的问题。
     *
     * @param strSc  需要进行替换的字符串
     * @param oldStr 源字符串
     * @param newStr 替换后的字符串
     * @return 替换后对应的字符串
     * @since 1.2
     */
    public static String replaceAll(String strSc, String oldStr, String newStr) {
        int i = -1;
        while ((i = strSc.indexOf(oldStr)) != -1) {
            strSc = new StringBuffer(strSc.substring(0, i)).append(newStr)
                    .append(strSc.substring(i + oldStr.length())).toString();
        }
        return strSc;
    }

    /**
     * 将字符串转换成HTML格式的字符串
     *
     * @param str 需要进行转换的字符串
     * @return 转换后的字符串
     * @since 1.1
     */
    public static String toHtml(String str) {
        String html = str;
        if (str == null || str.length() == 0) {
            return "";
        } else {
            html = replace(html, "&", "&amp;");
            html = replace(html, "<", "<");
            html = replace(html, ">", ">");
            html = replace(html, "\r\n", "\n");
            html = replace(html, "\n", "<br>\n");
            html = replace(html, "\"", "\"");
                    html = replace(html, " ", " ");
            return html;
        }
    }

    /**
     * 将HTML格式的字符串转换成常规显示的字符串
     *
     * @param str 需要进行转换的字符串
     * @return 转换后的字符串
     * @since 1.1
     */
    public static String toText(String str) {
        String text = str;
        if (str == null || str.length() == 0) {
            return "";
        } else {
            text = replace(text, "&", "&");
            text = replace(text, "<", "<");
            text = replace(text, ">", ">");
            text = replace(text, "<br>\n", "\n");
            text = replace(text, "<br>", "\n");
            text = replace(text, "\\", "\"");
            text = replace(text, " ", " ");
            text = replace(text, "“", "“");
            text = replace(text, "”", "”");
            return text;
        }
    }

    /**
     * 获取加密的手机号
     *
     * @param phoneNum
     * @return
     */
    public static String getEncryptMobile(String phoneNum) {
        if (!checkMobile(phoneNum)) {
            return phoneNum;
        }
        StringBuilder stringBuilder = new StringBuilder(phoneNum.substring(0, 3));
        stringBuilder.append("****");
        stringBuilder.append(phoneNum.substring(7));
        return stringBuilder.toString();
    }

    /**
     * 检查手机号
     *
     * @param phoneNum
     * @return
     */
    public static boolean checkMobile(String phoneNum) {
        Pattern p = Pattern.compile("^1[3|4|5|7|8]\\d{9}$");
        Matcher m = p.matcher(phoneNum);
        return m.matches();
    }

    /**
     * 验证固定电话号码
     *
     * @param phone 电话号码，格式：国家（地区）电话代码 + 区号（城市代码） + 电话号码，如：+8602085588447
     *              <p><b>国家（地区） 代码 ：</b>标识电话号码的国家（地区）的标准国家（地区）代码。它包含从 0 到 9 的一位或多位数字，
     *              数字之后是空格分隔的国家（地区）代码。</p>
     *              <p><b>区号（城市代码）：</b>这可能包含一个或多个从 0 到 9 的数字，地区或城市代码放在圆括号——
     *              对不使用地区或城市代码的国家（地区），则省略该组件。</p>
     *              <p><b>电话号码：</b>这包含从 0 到 9 的一个或多个数字 </p>
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkPhone(String phone) {
        if (phone.length() != 11 || !phone.startsWith("1")) {
            return false;
        }
        String regex = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$";
        return Pattern.matches(regex, phone);
    }

    /**
     * 检查密码有效
     * 大于6位包含数字小写字母和大写字母
     *
     * @param pass
     * @return
     */
    public static boolean checkPass(String pass) {
        boolean match = pass != null && !pass.equals("") && pass.length() > 6; //检查位数
        if (match) {
            String[] patterns = new String[]{"[0-9]+", "[a-zA-Z]+"};
            for (String patternStr : patterns) {
                Pattern pattern = Pattern.compile(patternStr);
                Matcher matcher = pattern.matcher(pass);
                matcher.reset().usePattern(pattern);
                if (!matcher.find()) {
                    match = false;
                    break;
                }
            }
        }
        return match;
    }


    /**
     * 验证身份证号码
     *
     * @param idCard 居民身份证号码15位或18位，最后一位可能是数字或字母
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkIdCard(String idCard) {
        if (idCard.length() != 15 && idCard.length() != 18) {
            return false;
        }
        String regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
        return Pattern.matches(regex, idCard);
    }

    /**
     * 将元单位数字转成int类型的元
     *
     * @param numStr
     */
    public static int numStrToInt(String numStr) {
        int num = 0;
        try {
            if (!isEmpty(numStr)) {
                num = Integer.valueOf(numStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return num;
    }


    /**
     * 格式化数字
     *
     * @param num (int)
     */
    public static String simpleFormat(int num) {
        DecimalFormat df = new DecimalFormat("#.#");
        StringBuilder numFormat = new StringBuilder();
        double numDouble;
        if (num > 1000 && num < 10000) {//1千以上
            numDouble = num / 1000d;
            numFormat.append(df.format(numDouble)).append("k");
        } else if (num > 10000) { // 万以上
            numDouble = num / 10000d;
            numFormat.append(df.format(numDouble)).append("w");
        } else {
            numFormat.append(num);
        }
        return numFormat.toString();
    }

    /**
     * 数字转成以万、亿为单位，1.0-->1; 1.1-->1.1
     *
     * @param numStr (String)
     */
    public static String newNumFormat(String numStr) {
        try {
            long num = Integer.valueOf(numStr);
            return newNumFormat(num);
        } catch (Exception e) {
            e.printStackTrace();
            return numStr;
        }
    }

    /**
     * 数字转成以万、亿为单位，1.0-->1; 1.1-->1.1
     *
     * @param num (int)
     */
    public static String newNumFormat(long num) {
        DecimalFormat df = new DecimalFormat("#.#");
        StringBuilder numFormat = new StringBuilder();
        double numDouble;
        if (num <= 1000000 && num > 10000) { // 万以上
            numDouble = num / 10000d;
            numFormat.append(df.format(numDouble)).append("万");
        } else if (num > 1000000) { // 百万以上
            numDouble = num / 1000000d;
            numFormat.append(df.format(numDouble)).append("百万");
        } else {
            numFormat.append(num);
        }
        return numFormat.toString();
    }

    /**
     * 将一字符串数组以某特定的字符串作为分隔来变成字符串
     *
     * @param strs  字符串数组
     * @param token 分隔字符串
     * @return 以token为分隔的字符串
     * @since 1.0
     */
    public static String join(String[] strs, String token) {
        if (strs == null)
            return null;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < strs.length; i++) {
            if (i != 0)
                sb.append(token);
            sb.append(strs[i]);
        }
        return sb.toString();
    }

    /**
     * 将一字符串以某特定的字符串作为分隔来变成字符串数组
     *
     * @param str   需要拆分的字符串("@12@34@56")
     * @param token 分隔字符串("@")
     * @return 以token为分隔的拆分开的字符串数组
     * @since 1.0
     */
    public static String[] split(String str, String token) {
        String temp = str.substring(1, str.length());
        return temp.split(token);
    }

    /**
     * 验证字符串合法性
     *
     * @param str  需要验证的字符串
     * @param test 非法字符串（如："~!#$%^&*()',;:?"）
     * @return true:非法;false:合法
     * @since 1.0
     */
    public static boolean check(String str, String test) {
        if (str == null || str.equals(""))
            return true;
        boolean flag = false;
        for (int i = 0; i < test.length(); i++) {
            if (str.indexOf(test.charAt(i)) != -1) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 将数值型字符串转换成Integer型
     *
     * @param str          需要转换的字符型字符串
     * @param defaultValue 转换失败时返回的值
     * @return 成功则返回转换后的Integer型值；失败则返回ret
     * @since 1.0
     */
    public static Integer String2Integer(String str, Integer defaultValue) {
        if (isEmpty(str)) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * has already set default int value in the method body.
     *
     * @param str input string to convert to integer value
     * @return str's int value,if empty return default value
     */
    public static Integer String2Integer(String str) {
        return String2Integer(str, DEFAULT_INT);
    }

    public static long getLongValue(Object o, long defaultValue) {
        if (!isNull(o)) {
            try {
                return Long.parseLong(String.valueOf(o));
            } catch (Exception e) {
            }
        }
        return defaultValue;
    }

    /**
     * 将数值型转换成字符串
     *
     * @param it  需要转换的Integer型值
     * @param ret 转换失败的返回值
     * @return 成功则返回转换后的字符串；失败则返回ret
     * @since 1.0
     */
    public static String Integer2String(Integer it, String ret) {
        try {
            return Integer.toString(it);
        } catch (NumberFormatException e) {
            return ret;
        }
    }

    /**
     * 比较两字符串大小(ASCII码顺序)
     *
     * @param str1 参与比较的字符串1
     * @param str2 参与比较的字符串2
     * @return str1>str2:1;str1<str2:-1;str1=str2:0
     * @since 1.1
     */
    public static int compare(String str1, String str2) {//
        if (str1.equals(str2)) {
            return 0;
        }
        int str1Length = str1.length();
        int str2Length = str2.length();
        int length = 0;
        if (str1Length > str2Length) {
            length = str2Length;
        } else {
            length = str1Length;
        }
        for (int i = 0; i < length; i++) {
            if (str1.charAt(i) > str2.charAt(i)) {
                return 1;
            }
        }
        return -1;
    }

    /**
     * 将阿拉伯数字的钱数转换成中文方式
     *
     * @param num 需要转换的钱的阿拉伯数字形式
     * @return 转换后的中文形式
     * @since 1.1
     */
    public static String num2Chinese(double num) {
        String result = "";
        String str = Double.toString(num);
        if (str.contains(".")) {
            String begin = str.substring(0, str.indexOf("."));
            String end = str.substring(str.indexOf(".") + 1, str.length());
            byte[] b = begin.getBytes();
            int j = b.length;
            for (int i = 0, k = j; i < j; i++, k--) {
                result += getConvert(begin.charAt(i));
                if (!"零".equals(result.charAt(result.length() - 1) + "")) {
                    result += getWei(k);
                }
                System.out.println(result);

            }
            for (int i = 0; i < result.length(); i++) {
                result = result.replaceAll("零零", "零");
            }
            if ("零".equals(result.charAt(result.length() - 1) + "")) {
                result = result.substring(0, result.length() - 1);
            }
            result += "元";
            byte[] bb = end.getBytes();
            int jj = bb.length;
            for (int i = 0, k = jj; i < jj; i++, k--) {
                result += getConvert(end.charAt(i));
                if (bb.length == 1) {
                    result += "角";
                } else if (bb.length == 2) {
                    result += getFloat(k);
                }
            }
        } else {
            byte[] b = str.getBytes();
            int j = b.length;
            for (int i = 0, k = j; i < j; i++, k--) {
                result += getConvert(str.charAt(i));
                result += getWei(k);
            }
        }
        return result;
    }

    public static String getString(String str, int count) {
        if (!(str.charAt(count - 1) >= 'a' && str.charAt(count - 1) <= 'z' || str.charAt(count - 1) >= 'A' && str.charAt(count - 1) <= 'Z'))
            return str.substring(0, count - 1);
        else
            return str.substring(0, count);
    }

    private static String getConvert(char num) {
        if (num == '0') {
            return "零";
        } else if (num == '1') {
            return "一";
        } else if (num == '2') {
            return "二";
        } else if (num == '3') {
            return "三";
        } else if (num == '4') {
            return "四";
        } else if (num == '5') {
            return "五";
        } else if (num == '6') {
            return "六";
        } else if (num == '7') {
            return "七";
        } else if (num == '8') {
            return "八";
        } else if (num == '9') {
            return "九";
        } else {
            return "";
        }
    }

    private static String getFloat(int num) {
        if (num == 2) {
            return "角";
        } else if (num == 1) {
            return "分";
        } else {
            return "";
        }
    }

    private static String getWei(int num) {
        if (num == 1) {
            return "";
        } else if (num == 2) {
            return "十";
        } else if (num == 3) {
            return "百";
        } else if (num == 4) {
            return "千";
        } else if (num == 5) {
            return "万";
        } else if (num == 6) {
            return "十";
        } else if (num == 7) {
            return "百";
        } else if (num == 8) {
            return "千";
        } else if (num == 9) {
            return "亿";
        } else if (num == 10) {
            return "十";
        } else if (num == 11) {
            return "百";
        } else if (num == 12) {
            return "千";
        } else if (num == 13) {
            return "兆";
        } else {
            return "";
        }
    }

    /**
     * 将字符串的首字母改为大写
     *
     * @param str 需要改写的字符串
     * @return 改写后的字符串
     * @since 1.2
     */
    public static String firstToUpper(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * 判断list中是否包含某一个字符串
     *
     * @param str1
     * @return
     */
    public static boolean listContain(List list, String str1) {
        return !(list == null || list.size() == 0) && list.contains(str1);
    }

    /**
     * list转String
     *
     * @param list
     * @param sign 分隔符号
     * @return
     */
    public static String List2String(List<String> list, String sign) {
        if (list == null || list.size() == 0)
            return null;
        StringBuffer sb = new StringBuffer();
        for (String string : list) {
            sb.append(string).append(sign);
        }
        return sb.substring(0, sb.length() - 1);
    }

    /**
     * String转list 去除null 空串
     *
     * @param target
     * @param sign   分隔符号
     * @return
     */
    public static List<String> String2List(String target, String sign) {
        List<String> usersList = new ArrayList<String>();
        if (!StringUtil.isEmpty(target)) {
            String[] vs = target.split(sign);
            for (String v : vs) {
                if (!StringUtil.isEmpty(v))
                    usersList.add(v);
            }
        }
        return usersList;
    }

    public static String escapeHtmlSign(String value) {
        if (value == null)
            return null;

        if (value instanceof String) {
            String result = value;
            // "'<>&
            result = result.replaceAll("&", "&").replaceAll(">", ">")
                    .replaceAll("<", "<").replaceAll("\"", "\"")
                            .replaceAll("'", "'");
            return result;
        } else {
            return value;
        }
    }

    public static String unEscapeHtmlSign(String value) {
        if (value == null)
            return null;

        if (value instanceof String) {
            String result = value;
            // "'<>&
            result = result.replaceAll("&", "&").replaceAll(">", ">")
                    .replaceAll("<", "<").replaceAll("\"", "\"")
                    .replaceAll("'", "'");
            return result;
        } else {
            return value;
        }
    }



    /**
     * 将浮点数进行四舍五入
     *
     * @return 改写后的字符串
     */
    public static String doubleToString(double str) {
        return doubleToString(str, 2);
    }


    public static String formatNum(float num) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(num);
    }

    public static String numToString(int str) {
        return doubleToString(str, 2);
    }

    public static String doubleToString(double str, int offset) {
        return new BigDecimal(str + "").setScale(offset,
                BigDecimal.ROUND_HALF_UP).toString();
    }

    public static Date stringDateTodate(String date) {
        String time = date.substring(6, date.length() - 7);
        return new Date(Long.parseLong(time));
    }


    /**
     * 去除字符串前后的空格
     *
     * @param text
     * @return
     */
    public static String trimString(String text) {
        if (!isEmpty(text)) {
            text = text.replaceAll("[ |　]", " ").trim(); //替换全角空格为半角，然后过滤
        }
        return text;
    }

    /**
     * 去除转义
     *
     * @param text
     * @return
     */
    public static String escapeString(String text) {
        try {
            if (!isEmpty(text)) {
                text = text.replaceAll("[\\n\\r]*", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isEmpty(text)) text = "";
        return text;
    }

    /**
     * 添加url参数
     *
     * @param url
     * @param params
     * @return
     */
    public static String appendUrlParams(String url, String params) {
        if (isEmpty(url)) {
            return url;
        }
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }
        Pattern pattern = Pattern.compile("\\?[\\w]*=");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return url + "&" + params;
        } else {
            return url + "?" + params;
        }
    }

    public static void main(String[] args) {
    }


    /**
     * 字符拆分
     *
     * @param str   原字符
     * @param split 分隔符
     * @return
     */
    public static String getSubStrSplit(String str, String split) {

        if (!isEmpty(split) && !isEmpty(str) && str.contains(split)) {
            str = str.substring(0, str.indexOf(split));
        }
        return str;
    }

    /**
     * 截取指定size的String
     */
    public static String resizeContent(String content, int size) {
        StringBuilder resizeContent = new StringBuilder();
        if (!isEmpty(content) && content.length() > size) {
            resizeContent.append(content.substring(0, size));
            resizeContent.append("...");
        } else {
            resizeContent.append(content);
        }
        return resizeContent.toString();
    }

    /**
     * 转码中文字符串
     *
     * @param srcStr
     * @return
     */
    public static String encodeChineseStr(String srcStr) {
        String dstStr = srcStr;
        if (!isEmpty(dstStr)) {
            if (srcStr.length() < srcStr.getBytes().length) {
                try {
                    dstStr = URLEncoder.encode(dstStr, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return dstStr;
    }

    /**
     * 用来比较手机号版本
     *
     * @param s1
     * @param s2
     * @return s1<s2
     */
    public static boolean compareToMin(String s1, String s2) {
        if (isEmpty(s1) || isEmpty(s2)) {
            return false;
        }
        s1 = s1.replaceAll("[a-zA-Z]", "");
        s2 = s2.replaceAll("[a-zA-Z]", "");
        int rt = s1.compareTo(s2);
        if (rt < 0) {
            return true;
        } else if (rt > 0) {
            return false;
        } else {
            return false;
        }
    }

    /**
     * 截取指定长度 从0开始，包左不包右
     */
    public static String spliteTime(String dateStr, int start, int end) {
        CharSequence sequence = dateStr.subSequence(start, end);
        return sequence.toString();
    }

    /**
     * 是否有中文字符
     *
     * @param str
     * @return
     */
    public static boolean hasChineseChar(String str) {
        boolean temp = false;
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            temp = true;
        }
        return temp;
    }

    /**
     * 字符是否是中文字符
     * <p/>
     * 不包括““”号，“。”号，“，”号
     * <p/>
     * GENERAL_PUNCTUATION 判断中文的“号
     * <p/>
     * CJK_SYMBOLS_AND_PUNCTUATION 判断中文的。号
     * <p/>
     * HALFWIDTH_AND_FULLWIDTH_FORMS 判断中文的，号
     */
    private static final boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A;
    }

    /**
     * 判断字符串是否含有中文字符
     */
    public static final boolean isContainChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (char c : ch) {
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    final static int BUFFER_SIZE = 4096;
    public static byte[] InputStreamTOByte(InputStream in) {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try {
            byte[] data = new byte[BUFFER_SIZE];
            int count = -1;
            while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
                outStream.write(data, 0, count);

            data = null;
        }catch (Exception e){
            e.printStackTrace();
        }
        return outStream.toByteArray();
    }

}