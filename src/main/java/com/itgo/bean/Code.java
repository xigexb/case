package com.itgo.bean;

import java.io.Serializable;

/**
 * Create by xigexb
 *
 * @versio 1.0.0
 * @Author xigexb
 * @date 2019/5/1 11:25
 * @description desc:
 */
public enum  Code  implements Serializable{

    /**
     * system code/msg
     */
    SUCCESS(1,"success","操作成功:)"),
    FAILURE(2,"failure","操作失败"),
    TIMEOUT(3,"timeout","超时"),

    /**
     * HTTP code/msg
     */
    HTTP_CONTINUE(100,"Continue","继续"),
    HTTP_SUCCESS(200,"success","成功"),
    HTTP_NO_AUTH(203,"Non-Authoritative Information","非授权信息"),
    HTTP_RESET(205,"Reset Content","重置内容"),
    HTTP_TIMEOUT(408,"timeout","请求超时"),
    HTTP_BAD_REQUEST(400,"Bad Request","错误请求"),
    HTTP_FORBIDDEN(403,"Forbidden","禁止"),
    HTTP_ERROR(500,"error","服务器错误"),
    HTTP_BAD_GATEWAY(502,"Bad Gateway","错误的网关"),
    HTTP_SERVICE_UNAVAILABLE(503,"Service Unavailable","服务不可用"),
    HTTP_GATEWAY_TIMEOUT(504,"Gateway Timeout","网关超时"),
    HTTP_NOT_FOUND(404,"not found","路径找不到"),

    /**
     * business code/msg
     */
    B_ENTRY(10,"entry","已录入"),
    B_COMMIT(20,"commit","已提交"),
    B_CONFIRM(30,"effective","已生效"),
    B_OBSOLETE(40,"obsolete","已作废"),
    B_DELETEED(50,"delete","已删除");



    private Integer code;

    private String msg;

    private String msgCn;

    Code(int code, String msg,String msgCn) {
        this.code = code;
        this.msg = msg;
        this.msgCn = msgCn;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * @param code 代码
     * @return  msg
     */
    public static Code getCode(Integer code){
        for (Code c : Code.values()) {
            if (c.code == code) {
                return c;
            }
        }
        return null;
    }

    /**
     * @param code 代码
     * @return  msg
     */
    public static String getMsg(Integer code){
        for (Code c : Code.values()) {
            if (c.code == code) {
                return c.msg;
            }
        }
        return null;
    }

    /**
     * @param code 代码
     * @return  msg
     */
    public static String getMsgCn(Integer code){
        for (Code c : Code.values()) {
            if (c.code == code) {
                return c.msgCn;
            }
        }
        return null;
    }

    /**
     * @param msg 消息
     * @return  msg
     */
    public static Code getCode(String  msg){
        for (Code c : Code.values()) {
            if (c.msg.equals(msg)) {
                return c;
            }
        }
        return null;
    }







}
