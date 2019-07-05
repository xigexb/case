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
 * @date 2019/5/28 11:59
 * @description
 *      desc:excel属性
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
public @interface ExcelField {

    /**
     * Excel列名
     */
    String value() default "field";

    /**
     * 是否需要导出或解析该属性
     * @return
     */
    boolean isNeed() default true;

    /**
     * 是否处理科学计数法
     * @return
     */
    boolean isParseScientificNotation() default false;





}
