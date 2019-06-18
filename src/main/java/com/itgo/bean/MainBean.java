package com.itgo.bean;

import java.util.List;

/**
 * Create by xigexb
 *
 * @versio 1.0.0
 * @Author xigexb
 * @date 2019/6/18 15:54
 * @Description desc:
 */
public class MainBean {

    /**
     * 收件人
     */
    private String toMail;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 发送标题
     */
    private String title;

    /**
     * 主题Subject
     */
    private String Subject;

    /**
     * 内容
     */
    private String content;

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getToMail() {
        return toMail;
    }

    public void setToMail(String toMail) {
        this.toMail = toMail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
