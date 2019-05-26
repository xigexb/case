package com.itgo.exception;

/**
 * Create by xigexb
 *
 * @versio 1.0.0
 * @Author xigexb
 * @date 2019/5/26 14:10
 * @description desc:
 */
public class NoSuchAnnotationException extends RuntimeException {

    public NoSuchAnnotationException(String message){
        super(message);
    }

    public NoSuchAnnotationException(){
        super("Not found Annotation");
    }

}
