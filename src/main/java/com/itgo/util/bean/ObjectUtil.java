package com.itgo.util.bean;

import com.itgo.annotation.BeanField;
import com.itgo.exception.ObjectNotNullException;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Create by xigexb
 *
 * @versio 1.0.0
 * @Author xigexb
 * @date 2019/5/30 16:44
 * @description
 *      desc:对象工具类
 */
public class ObjectUtil {


    /**
     * copy object
     * @param formObject
     * @param tClass
     * @param <T>
     * @return new instance
     */
    public static <T> T copy(Object formObject, Class<T> tClass) {
        try {
            if (formObject == null) {
                throw new ObjectNotNullException("form Object must not null");
            }
            //all field
            Field[] formFields = getAllFields(formObject);
            T t = tClass.newInstance();
            Field[] toFields = getAllFields(t);
            for (Field formF : formFields) {
                for (Field toF : toFields) {
                    copyValue(formObject, formF, t, toF);
                }
            }
            return t;
        } catch (ObjectNotNullException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * copy object  not return
     * @param formObject form
     * @param toObject to
     */
    public static void copy(Object formObject, Object toObject) {
        try {
            if (formObject == null || toObject == null) {
                throw new ObjectNotNullException("formObject must not null");
            }
            Field[] formFields = getAllFields(formObject);
            Field[] toFields = getAllFields(toObject);
            for (Field formF : formFields) {
                for (Field toF : toFields) {
                    copyValue(formObject, formF, toObject, toF);
                }
            }
        } catch (ObjectNotNullException e) {
            e.printStackTrace();
        }
    }


    /**
     * copy object for list
     * @param dataList data list
     * @param tClass class object
     * @param <T> t
     * @return T type data list
     */
    public static <T> List<T> copyList(List<?> dataList,Class<T> tClass) {
        List<T> data = null;
        try {
            if(!(dataList != null && !dataList.isEmpty())){
                throw  new ObjectNotNullException("data list must not null");
            }
            data = new ArrayList<>();
            for (Object form : dataList) {
                T t = copy(form, tClass);
                data.add(t);
            }
        }catch (ObjectNotNullException e){
            e.printStackTrace();
        }
        return data;
    }

    /**
     * copy value
     * @param formObj form
     * @param formF form field
     * @param toObj to
     * @param toF to filed
     */
    private static void copyValue(Object formObj, Field formF, Object toObj, Field toF) {
        formF.setAccessible(true);
        toF.setAccessible(true);
        String fType = formF.getGenericType().getTypeName();
        String tType = toF.getGenericType().getTypeName();
        try {
            boolean isPresent = toF.isAnnotationPresent(BeanField.class);
            if (isPresent) {
                BeanField beanField = toF.getAnnotation(BeanField.class);
                if (beanField.property().equals(formF.getName()) || formF.getName().equals(toF.getName())) {
                    Object val = formF.get(formObj);
                    if (val != null) {
                        if (fType.equals(tType)) {  //eq type
                            String pattern = beanField.pattern();
                            if ("null".equals(pattern)) {
                                toF.set(toObj, val);
                            } else {
                                //convert date
                                SimpleDateFormat format = new SimpleDateFormat(pattern);
                                toF.set(toObj, format.parse(format.format(val)));
                            }
                        } else {
                            Object convertVal = convert(val, tType);
                            toF.set(toObj, convertVal);
                        }
                    } else {
                        toF.set(toObj, null);
                    }
                }
            } else {
                if (formF.getName().equals(toF.getName())) { //form object property name eq to object property name
                    Object val = formF.get(formObj);
                    if (fType.equals(tType)) {
                        if (val != null) {
                            toF.set(toObj, val);
                        }
                    } else {
                        if(val !=  null){
                            Object convertVal = convert(val, tType);
                            toF.set(toObj, convertVal);
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    /**
     * convert value
     * @param val value
     * @param className type class
     * @return
     */
    private static Object convert(Object val, String className) {
        Object retVal = null;
        switch (className) {
            case "char":
            case "java.lang.Character":
            case "java.lang.String":
                retVal = String.valueOf(val);
                break;
            case "java.lang.Integer":
            case "int":
                retVal = Integer.valueOf(val.toString());
                break;
            case "float":
            case "java.lang.Float":
                retVal = Float.valueOf(val.toString());
                break;
            case "double":
            case "java.lang.Double":
                retVal = Double.valueOf(val.toString());
                break;
            case "java.lang.Long":
            case "long":
                retVal = Long.valueOf(val.toString());
                break;
            case "java.lang.Short":
            case "short":
                retVal = Short.valueOf(val.toString());
                break;
            case "java.math.BigDecimal":
                retVal = BigDecimal.valueOf(Double.valueOf(val.toString()));
                break;
            case "java.lang.Boolean":
            case "boolean":
                retVal = Boolean.valueOf(val.toString());
                break;
        }
        return retVal;
    }

    /**
     * get Object all fields
     *
     * @param object
     * @return
     */
    public static Field[] getAllFields(Object object) {
        Class clazz = object.getClass();
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
            if (clazz != null && "Object".equals(clazz.getSimpleName())) {
                clazz = null;
            }
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }


}
