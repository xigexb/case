package com.itgo.bean;

/**
 * Create by xigexb
 *
 * @versio 1.0.0
 * @Author xigexb
 * @date 2019/6/18 11:45
 * @Description desc:
 */
public class SqlBean {

    /**
     * 属性
     */
    private String field;

    /**
     * 数据库字段名
     */
    private String column;

    /**
     * 值
     */
    private Object value;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public SqlBean(String field, String column, Object value) {
        this.field = field;
        this.column = column;
        this.value = value;
    }

    public SqlBean() {
    }

    @Override
    public String toString() {
        return "SqlBean{" +
                "field='" + field + '\'' +
                ", column='" + column + '\'' +
                ", value=" + value +
                '}';
    }
}
