package com.itgo.util.json;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Create by xb
 * The file is [ GosnUtil] on [ case ] project
 * The file path is com.itgo.util.GosnUtil
 *
 * @versio 1.0.o
 * @Author he ming xi
 * @date 2019/3/23 13:26
 * @description
 */
public class GsonUtil {
    private static Gson gson = null;
    static {
        if (gson == null) {
            /** setFieldNamingPolicy():gson序列化属性命名策略
             *      FieldNamingPolicy.IDENTITY 引用属性字段名称，不会做任何更改
             *      FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES 全部小写，使用下划线(_)分隔属性单词
             *      FieldNamingPolicy.LOWER_CASE_WITH_DASHES      全部小写，使用中划线(-)分隔属性单词
             *      FieldNamingPolicy.LOWER_CASE_WITH_DOTS      全部小写，使用点(.)分隔属性单词
             *      FieldNamingPolicy.UPPER_CAMEL_CASE   属性第一个单词首字母大写
             *      FieldNamingPolicy.UPPER_CAMEL_CASE_WITH_SPACES  属性单词首字母大写,每个单词使用空格隔开
             * @SerializedName  标有此注解的属性，gson将不会应用任何策略
             * setFieldNamingStrategy() 自定义gson序列化属性命名策略
             * excludeFieldsWithoutExposeAnnotation() //不导出实体中没有用@Expose注解的属性
             * enableComplexMapKeySerialization() //支持Map的key为复杂对象的形式
             * serializeNulls()
             * setDateFormat("yyyy-MM-dd HH:mm:ss:SSS")//时间转化为特定格式
             * setPrettyPrinting() //对json结果格式化
             * setVersion() 版本号
             *
             */
            //gson = new Gson();
            //当使用GsonBuilder方式时属性为空的时候输出来的json字符串是有键值key的,显示形式是"key":null，而直接new出来的就没有"key":null的
            gson= new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                    .enableComplexMapKeySerialization()
                    .serializeNulls()
                    .setPrettyPrinting()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create();
        }
    }

    /**
     * 私有构造方法
     */
    private GsonUtil() {
    }


    /**
     * 转成json
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        if(object == null){
            return null;
        }
        String output = null;
        try {
            if (gson != null) {
                output = gson.toJson(object);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return output;
    }


    /**
     * 把json字符串解析成一个对象
     * @param input 输入的json字符串
     * @param cls   class
     * @param <T    泛型
     * @return      被解析的对象
     */
    public static <T> T gsonToBean(String input, Class<T> cls) {
        if(input == null || "".equals(input.trim())){
            return null;
        }
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(input, cls);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Exception JSON string is "+input);
            return null;
        }
        return t;
    }

    /**
     * 把json字符串解析成一个T类型的list
     * @param input
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> gsonToList(String input, Class<T> cls) {
        if(input == null || "".equals(input.trim())){
            return null;
        }
        List<T> list = null;
        try {
            list = new ArrayList();
            Gson gson = new Gson();
            list = gson.fromJson(input, new TypeToken<T>() {
            }.getType());
        } catch (Exception e) {
        }
        return list;
    }

    /**
     * 转成list中有map的
     * @param input 输入json字符串
     * @return
     */
    public static <T> List<Map<String, T>> gsonToListMap(String input) {
        if(input == null || "".equals(input.trim())){
            return null;
        }
        List<Map<String, T>> list = null;
        try {
            if (gson != null) {
                list = gson.fromJson(input,
                        new TypeToken<List<Map<String, T>>>() {
                        }.getType());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 转成map的
     * @param input
     * @return
     */
    public static <T> Map<String, T> gsonToMap(String input) {
        if(input == null || "".equals(input.trim())){
            return null;
        }
        Map<String, T> map = null;
        try {
            if (gson != null) {
                map = gson.fromJson(input, new TypeToken<Map<String, T>>() {
                }.getType());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }
}
