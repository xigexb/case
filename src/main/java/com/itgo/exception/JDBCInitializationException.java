package com.itgo.exception;

/**
 * Create by xigexb
 *
 * @versio 1.0.0
 * @Author xigexb
 * @date 2019/5/26 23:20
 * @description desc:
 */
public class JDBCInitializationException extends RuntimeException {
    public JDBCInitializationException(String message){
        super(message);
    }

    public JDBCInitializationException(){
        super("Initialization exception");
    }
}
