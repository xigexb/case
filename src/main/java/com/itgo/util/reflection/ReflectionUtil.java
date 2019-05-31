package com.itgo.util.reflection;

import com.itgo.exception.ObjectNotNullException;

/**
 * Create by xb
 * The file is [ ReflectionUtil] on [ case ] project
 * The file path is com.itgo.util.reflection.ReflectionUtil
 *
 * @versio 1.0.o
 * @Author he ming xi
 * @date 2019/4/5 17:42
 * @description
 *      反射工具类
 */
public class ReflectionUtil {

    /**
     * 获取类名
     * @param t Class对象
     * @param <T> 泛型
     * @return 包名+类名
     */
    public static  <T> String getClassName(Class<T> t){
        String name = null;
        if(isEmpty(t)){
            name = t.getName();
        }
        return name;
    }

    /**
     * 获取超类类名
     * @param t Class对象
     * @param <T> 泛型
     * @return 包名+类名
     */
    public static  <T> String getSuperClassName(Class<T> t){
        String name = null;
        if(isEmpty(t)){
            name = t.getSuperclass().getName();
        }
        return name;
    }

    /**
     * 获取类名
     * @param t Class对象
     * @param <T> 泛型
     * @return 类名
     */
    public static  <T> String getSimpleClassName(Class<T> t){
        String name = null;
        if(isEmpty(t)){
            name = t.getSimpleName();
        }else {
            try {
                throw  new ObjectNotNullException();
            } catch (ObjectNotNullException e) {
                e.printStackTrace();
            }
        }
        return name;
    }

    /**
     * 获取超类类名
     * @param t Class对象
     * @param <T> 泛型
     * @return 类名
     */
    public static  <T> String getSuperSimpleClassName(Class<T> t){
        String name = null;
        if(isEmpty(t)){
            name = t.getSuperclass().getSimpleName();
        }
        return name;
    }


    /**
     * 根据className获取一个Class对象
     * @param className 类路径
     * @return 返回描述类型为className的Class对象
     */
    public static Class getForName(String className){
        if(isEmpty(className)){
            try {
                return Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取Class类型对应的实例对象
     * @param t Class
     * @param <T> 泛型
     * @return T类型的实例对象
     */
    public static <T> T  getInstance(Class<T> t){
        if(isEmpty(t)){
            try {
                return t.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 判断对象是否为空
     *      为空就抛出异常
     * @param t 操作对象
     * @param <T> 泛型
     * @return
     */
    private static <T> Boolean isEmpty(T t){
        try {
            if(t==null){
                throw new  ObjectNotNullException();
            }else {
                return true;
            }
        }catch (ObjectNotNullException e) {
            e.printStackTrace();
            return false;
        }
    }

    

}
