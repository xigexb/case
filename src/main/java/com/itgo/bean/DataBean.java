package com.itgo.bean;

import java.util.List;

/**
 * Create by xigexb
 *
 * @versio 1.0.0
 * @Author xigexb
 * @date 2019/5/1 11:20
 * @description
 *      desc:返回数据统一包装对象
 */
public class DataBean<T> {

    /**
     * 数据
     */
    private T data;

    /**
     * 提示消息
     */
    private String msg = "ok";

    /**
     * 返回代码
     */
    private String code = "1";

    /**
     * 返回结果
     */
    private String result;

    /**
     * 数据集合
     */
    private List<T> dataList;

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean() {
    }

    public DataBean(String msg, String code) {
        this.msg = msg;
        this.code = code;
    }

    public DataBean(T data, String msg, String code) {
        this.data = data;
        this.msg = msg;
        this.code = code;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public DataBean(T data) {
        this.data = data;
    }
}
