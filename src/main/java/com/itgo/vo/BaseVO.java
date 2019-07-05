package com.itgo.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Create by xb
 * The file is [ BaseBean] on [ s-file-system ] project
 * The file path is com.itgo.bean.BaseBean
 *
 * @versio 1.0.0
 * @Author he ming xi
 * @date 2019/4/5 23:59
 * @description
 */
public class BaseVO implements Serializable {
    /**
     * ID
     */
    private Long id;

    /**
     * 创建用户
     */
    private Long createUser;

    /**
     * 创建用户名称
     */
    private String createName;

    /**
     * 创建日期
     */
    private Date createDate;

    /**
     * 创建IP
     */
    private String createIp;

    /**
     * 修改用户
     */
    private Long updateUser;

    /**
     * 修改用户名称
     */
    private String updateName;

    /**
     * 修改日期
     */
    private Date updateDate;

    /**
     * 修改IP
     */
    private String updateIp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateIp() {
        return createIp;
    }

    public void setCreateIp(String createIp) {
        this.createIp = createIp;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateIp() {
        return updateIp;
    }

    public void setUpdateIp(String updateIp) {
        this.updateIp = updateIp;
    }
}
