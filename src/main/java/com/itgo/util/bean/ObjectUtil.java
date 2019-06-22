package com.itgo.util.bean;

import com.itgo.annotation.BeanField;
import com.itgo.exception.ObjectNotNullException;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Create by xigexb
 *
 * @versio 1.0.0
 * @Author xigexb
 * @date 2019/5/30 16:44
 * @description desc:对象工具类
 */
public final class ObjectUtil {


    /**
     * Separator
     */
    private static final String DEF_SEPARATOR = "=";


    /**
     * copy object
     *
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
            Field[] formFields = getFields(formObject);
            T t = tClass.newInstance();
            Field[] toFields = getFields(t);
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
     *
     * @param formObject form
     * @param toObject   to
     */
    public static void copy(Object formObject, Object toObject) {
        try {
            if (formObject == null || toObject == null) {
                throw new ObjectNotNullException("formObject must not null");
            }
            Field[] formFields = getFields(formObject);
            Field[] toFields = getFields(toObject);
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
     *
     * @param dataList data list
     * @param tClass   class object
     * @param <T>      t
     * @return T type data list
     */
    public static <T> List<T> copyList(List<?> dataList, Class<T> tClass) {
        List<T> data = null;
        try {
            if (!(dataList != null && !dataList.isEmpty())) {
                throw new ObjectNotNullException("data list must not null");
            }
            data = new ArrayList<>();
            for (Object form : dataList) {
                T t = copy(form, tClass);
                data.add(t);
            }
        } catch (ObjectNotNullException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * not ignore null
     *
     * @param object object
     * @return
     */
    public static Map<String, Object> toMap(Object object) {
        return toMap(object, false);
    }

    /**
     * ignore null  true / false
     *
     * @param object     object
     * @param ignoreNull true / false
     * @return
     */
    public static Map<String, Object> toMap(Object object, Boolean ignoreNull) {
        Map<String, Object> map = null;
        ignoreNull = ignoreNull == null ? true : false;
        try {
            if (object == null) {
                throw new ObjectNotNullException("object must not null");
            }
            Field[] fields = getFields(object);
            for (Field field : fields) {
                if (field != null) {
                    if (map == null) {
                        map = new HashMap<>();
                    }
                    field.setAccessible(true);
                    Object val = field.get(object);
                    if (ignoreNull) {
                        if (val != null) {
                            map.put(field.getName(), val);
                        }
                    } else {
                        map.put(field.getName(), val);
                    }
                }
            }
        } catch (ObjectNotNullException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return map;
    }


    /**
     * @param formMap form map
     * @param tClass  tclass
     * @param <T>     T
     * @return
     */
    public static <T> T toObject(Map<String, Object> formMap, Class<T> tClass) {
        return toObject(formMap, tClass, false);

    }

    /**
     * @param formMap    form map
     * @param tClass     tclass
     * @param IgnoreCase true/false
     * @param <T>        T
     * @return
     */
    public static <T> T toObject(Map<String, Object> formMap, Class<T> tClass, Boolean IgnoreCase) {
        T instance = null;
        try {
            if (formMap == null) {
                throw new ObjectNotNullException("data map must not null");
            }
            instance = tClass.newInstance();
            Field[] fields = getFields(instance);
            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                String typeName = field.getGenericType().getTypeName();
                if (IgnoreCase) {
                    fieldName = fieldName.toLowerCase();
                    typeName = typeName.toLowerCase();
                }
                Set<String> keySet = formMap.keySet();
                for (String key : keySet) {
                    String newKey = null;
                    if (IgnoreCase) {
                        newKey = key.toLowerCase();
                    }
                    if (fieldName.equals(newKey)) {
                        Object o = formMap.get(key);
                        if (o == null) {
                            continue;
                        }
                        String toTypeName = o.getClass().getTypeName();
                        if (IgnoreCase) {
                            toTypeName = toTypeName.toLowerCase();
                        }
                        if (typeName.equals(typeName)) {
                            field.set(instance, o);
                        }
                    }
                }
            }
        } catch (ObjectNotNullException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return instance;

    }


    /**
     * copy value
     *
     * @param formObj form
     * @param formF   form field
     * @param toObj   to
     * @param toF     to filed
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
                        if (val != null) {
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
     *
     * @param val       value
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
    public static Field[] getFields(Object object) {
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

    /**
     * get Object all fields Ignore Empty field
     *
     * @param object
     * @return
     */
    public static Field[] getFieldsIgnoreEmpty(Object object) {
        try {
            Class clazz = object.getClass();
            List<Field> fieldList = new ArrayList<>();
            while (clazz != null) {
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    Object o = field.get(object);
                    if (o != null && !"".equals(o.toString())) {
                        fieldList.add(field);
                    }
                }
                clazz = clazz.getSuperclass();
                if (clazz != null && "Object".equals(clazz.getSimpleName())) {
                    clazz = null;
                }
            }
            Field[] fields = new Field[fieldList.size()];
            fieldList.toArray(fields);
            return fields;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * toString Ignore Empty field
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> String toStringIgnoreEmpty(T t) {
        try {
            if (t == null) {
                throw new ObjectNotNullException("Object must not null");
            }
            Class aClass = t.getClass();
            String classSimpleName = aClass.getSimpleName();
            StringBuffer sb = new StringBuffer(classSimpleName);
            sb.append("{");
            //get fields
            Field[] fields = getFields(t);
            if (fields.length > 0) {
                Boolean flag = false;
                for (int i = 0; i < fields.length; i++) {
                    Field field = fields[i];
                    field.setAccessible(true);
                    Object o = field.get(t);
                    if (o != null) {
                        String value = o.toString();
                        if (value != null && !"".equals(value)) {
                            flag = true;
                            String fieldName = field.getName();
                            String typeName = field.getGenericType().getTypeName();
                            splice(sb, fieldName, value, typeName);
                        }
                    }
                    if (flag) {
                        if (i != fields.length - 1) {
                            sb.append(",");
                        }
                        flag = false;
                    }
                }
            }
            return sb.append("}").toString();
        } catch (ObjectNotNullException e) {

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *toString
     * @param t
     * @param <T>
     * @return
     */
    public static <T> String toString(T t) {
        try {
            if (t == null) {
                throw new ObjectNotNullException("Object must not null");
            }
            Class aClass = t.getClass();
            String classSimpleName = aClass.getSimpleName();
            StringBuffer sb = new StringBuffer(classSimpleName);
            sb.append("{");
            //get fields
            Field[] fields = getFields(t);
            if (fields.length > 0) {
                for (int i = 0; i < fields.length; i++) {
                    Field field = fields[i];
                    field.setAccessible(true);
                    Object o = field.get(t);
                    String fieldName = field.getName();
                    String typeName = field.getGenericType().getTypeName();
                    if (o != null) {
                        String value = o.toString();
                        if (value != null && !"".equals(value)) {
                            splice(sb, fieldName, value, typeName);
                        }
                    } else {
                        sb.append(fieldName);
                        sb.append(DEF_SEPARATOR);
                        sb.append("null");
                    }
                    if (i != fields.length - 1) {
                        sb.append(",");
                    }
                }
            }
            return sb.append("}").toString();
        } catch (ObjectNotNullException e) {

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 拼接属性
     * @param sb
     * @param fieldName
     * @param value
     * @param typeClassName
     */
    private static void splice(StringBuffer sb, String fieldName, String value, String typeClassName) {
        switch (typeClassName) {
            case "char":
            case "java.lang.Character":
            case "java.lang.String":
                sb.append(fieldName);
                sb.append(DEF_SEPARATOR);
                sb.append("'" + value + "'");
                break;
            case "java.lang.Integer":
            case "int":
                sb.append(fieldName);
                sb.append(DEF_SEPARATOR);
                sb.append(Integer.valueOf(value));
                break;
            case "float":
            case "java.lang.Float":
                sb.append(fieldName);
                sb.append(DEF_SEPARATOR);
                sb.append(Float.valueOf(value));
                break;
            case "double":
            case "java.lang.Double":
                sb.append(fieldName);
                sb.append(DEF_SEPARATOR);
                sb.append(Double.valueOf(value));
                break;
            case "java.lang.Long":
            case "long":
                sb.append(fieldName);
                sb.append(DEF_SEPARATOR);
                sb.append(Long.valueOf(value));
                break;
            case "java.lang.Short":
            case "short":
                sb.append(fieldName);
                sb.append(DEF_SEPARATOR);
                sb.append(Short.valueOf(value));
                break;
            case "java.math.BigDecimal":
                sb.append(fieldName);
                sb.append(DEF_SEPARATOR);
                sb.append(BigDecimal.valueOf(Double.valueOf(value)));
                break;
            case "java.lang.Boolean":
            case "boolean":
                sb.append(fieldName);
                sb.append(DEF_SEPARATOR);
                sb.append(Boolean.valueOf(value));
                break;
        }
    }


}
