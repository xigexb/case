package com.itgo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Create by xigexb
 *
 * @versio 1.0.0
 * @Author xigexb
 * @date 2019/5/25 20:49
 * @description
 *      desc:属性注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BeanField {

    /**
     * 值
     * @return
     */
    String value() default "null";

    /**
     * 描述
     * @return
     */
    String desc() default "null";

    /**
     * 属性名称
     * @return
     */
    String property() default "null";

    /**
     * @return
     */
    String pattern() default "null";


    /**
     * @return
     */
    String column() default "null";
}
