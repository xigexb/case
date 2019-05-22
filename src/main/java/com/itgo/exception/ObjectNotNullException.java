package com.itgo.exception;

/**
 * Create by xb
 * The file is [ ClassIsNull] on [ case ] project
 * The file path is com.itgo.exception.ClassIsNull
 *
 * @versio 1.0.o
 * @Author he ming xi
 * @date 2019/4/5 17:53
 * @description
 */
public class ObjectNotNullException extends Exception {

    public ObjectNotNullException(String message){
        super(message);
    }

    public ObjectNotNullException(){
        super("Object is null");
    }


}
