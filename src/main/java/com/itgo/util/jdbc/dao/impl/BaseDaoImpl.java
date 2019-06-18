package com.itgo.util.jdbc.dao.impl;


import com.itgo.annotation.BeanField;
import com.itgo.annotation.BeanMeta;
import com.itgo.bean.DataBean;
import com.itgo.bean.SqlBean;
import com.itgo.enums.JDBCExecutionType;
import com.itgo.util.bean.ObjectUtil;
import com.itgo.util.jdbc.JDBCUtil;
import com.itgo.util.jdbc.dao.BaseDao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * Create by xigexb
 *
 * @versio 1.0.0
 * @Author xigexb
 * @date 2019/6/18 9:09
 * @Description
 *      desc:JDBC通用操作基类实现
 */
public class BaseDaoImpl<T> implements BaseDao<T> {

    /**
     * 新增用户
     *
     * @return 新增得对象
     */
    public T insert(T t) {
        try {
            Class tClass = t.getClass();
            boolean isMate = tClass.isAnnotationPresent(BeanMeta.class);
            String tableName = null;
            String tableDesc = null;
            if (isMate) {
                BeanMeta mate = (BeanMeta) tClass.getAnnotation(BeanMeta.class);
                String value = mate.value();
                String desc = mate.desc();
                if (value != null && !"".equals(value)) {
                    tableName = value;
                    tableDesc = desc;
                } else {
                    tableName = tClass.getSimpleName();
                    tableDesc = "table not desc";
                }
            } else {
                tableName = tClass.getSimpleName();
                tableDesc = "table not desc";
            }
            StringBuffer sql = new StringBuffer("INSERT INTO  ");
            //append table
            sql.append(tableName + " (");
            //get column
            List<SqlBean> sqlBeanList = new ArrayList<>();
            Field[] fields = ObjectUtil.getFields(t);
            for (Field field : fields) {
                field.setAccessible(true);
                boolean isField = field.isAnnotationPresent(BeanField.class);
                if (isField) {
                    BeanField beanField = field.getAnnotation(BeanField.class);
                    String column = beanField.column();
                    if (column != null && !"".equals(column)) {
                        sqlBeanList.add(new SqlBean(field.getName(), column + " ", field.get(t)));
                    }
                }
            }
            //make column
            for (int i = 0; i < sqlBeanList.size(); i++) {
                if (i == sqlBeanList.size() - 1) {
                    sql.append(sqlBeanList.get(i).getColumn() + " )");
                } else {
                    sql.append(sqlBeanList.get(i).getColumn() + ",");
                }
            }
            //append values()
            sql.append(" values (");

            //make value
            for (int i = 0; i < sqlBeanList.size(); i++) {
                if (i == sqlBeanList.size() - 1) {
                    sql.append("? )");
                } else {
                    sql.append("?,");
                }
            }
            //make  params
            List<Object> pL = new ArrayList<>();
            for (SqlBean sqlBean : sqlBeanList) {
                pL.add(sqlBean.getValue());
            }
            DataBean dataBean = JDBCUtil.execution(sql.toString(), JDBCExecutionType.INSERT, t.getClass(), "ID", pL.toArray());
            return (T) dataBean.getData();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        return null;
    }

    /**
     * 修改
     *
     * @param t
     * @return 受影响的行数
     */
    public String update(T t) {
        try {
            Class tClass = t.getClass();
            boolean isMate = tClass.isAnnotationPresent(BeanMeta.class);
            String tableName = null;
            String tableDesc = null;
            if (isMate) {
                BeanMeta mate = (BeanMeta) tClass.getAnnotation(BeanMeta.class);
                String value = mate.value();
                String desc = mate.desc();
                if (value != null && !"".equals(value)) {
                    tableName = value;
                    tableDesc = desc;
                } else {
                    tableName = tClass.getSimpleName();
                    tableDesc = "table not desc";
                }
            } else {
                tableName = tClass.getSimpleName();
                tableDesc = "table not desc";
            }
            StringBuffer sql = new StringBuffer("UPDATE ");
            //append table
            sql.append(tableName + " SET ");
            //get column
            List<SqlBean> sqlBeanList = new ArrayList<>();
            Field[] fields = ObjectUtil.getFields(t);
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                boolean isField = field.isAnnotationPresent(BeanField.class);
                if (isField) {
                    BeanField beanField = field.getAnnotation(BeanField.class);
                    String column = beanField.column();
                    if (column != null && !"".equals(column)) {
                        Object o = field.get(t);
                        if (i == 0) {
                            if (o == null) {
                                throw new IllegalArgumentException("主键为空");
                            } else {
                                sqlBeanList.add(new SqlBean(field.getName(), column + " ", field.get(t)));
                            }
                        } else {
                            if (o != null) {
                                sqlBeanList.add(new SqlBean(field.getName(), column + " ", field.get(t)));
                            }
                        }
                    }
                }
            }
            //make column = value
            for (int i = 1; i < sqlBeanList.size(); i++) {
                if (i == sqlBeanList.size() - 1) {
                    sql.append(sqlBeanList.get(sqlBeanList.size() - 1).getColumn() + "= ? ");
                } else {
                    sql.append(sqlBeanList.get(i).getColumn() + "= ? , ");
                }
            }
            //append values()
            sql.append(" WHERE " + sqlBeanList.get(0).getColumn() + "= ? ");

            //make  params
            List<Object> pL = new ArrayList<>();
            for (int i = 1; i < sqlBeanList.size(); i++) {
                if (i == sqlBeanList.size() - 1) {
                    pL.add(sqlBeanList.get(i).getValue());
                    pL.add(sqlBeanList.get(0).getValue());
                } else {
                    pL.add(sqlBeanList.get(i).getValue());
                }
            }
            DataBean dataBean = JDBCUtil.execution(sql.toString(), JDBCExecutionType.UPDATE, null, null, pL.toArray());
            return dataBean.getResult();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除
     *
     * @param t 用户
     * @return 受影响的行数
     */
    public String delete(T t) {
        try {
            Class tClass = t.getClass();
            boolean isMate = tClass.isAnnotationPresent(BeanMeta.class);
            String tableName = null;
            String tableDesc = null;
            if (isMate) {
                BeanMeta mate = (BeanMeta) tClass.getAnnotation(BeanMeta.class);
                String value = mate.value();
                String desc = mate.desc();
                if (value != null && !"".equals(value)) {
                    tableName = value;
                    tableDesc = desc;
                } else {
                    tableName = tClass.getSimpleName();
                    tableDesc = "table not desc";
                }
            } else {
                tableName = tClass.getSimpleName();
                tableDesc = "table not desc";
            }
            StringBuffer sql = new StringBuffer("DELETE FROM ");
            //append table
            sql.append(tableName + " ");
            //get column
            List<SqlBean> sqlBeanList = new ArrayList<>();
            Field[] fields = ObjectUtil.getFields(t);
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                boolean isField = field.isAnnotationPresent(BeanField.class);
                if (isField) {
                    BeanField beanField = field.getAnnotation(BeanField.class);
                    String column = beanField.column();
                    if (column != null && !"".equals(column)) {
                        Object o = field.get(t);
                        if (i == 0) {
                            if (o == null) {
                                throw new IllegalArgumentException("主键为空");
                            } else {
                                sqlBeanList.add(new SqlBean(field.getName(), column + " ", field.get(t)));
                                break;
                            }
                        }
                    }
                }
            }
            SqlBean sqlBean1 = sqlBeanList.get(0);
            sql.append("WHERE " + sqlBean1.getColumn() + " = ?");
            //make  params
            Object[] params = {sqlBean1.getValue()};
            DataBean dataBean = JDBCUtil.execution(sql.toString(), JDBCExecutionType.DELETE, null, null, params);
            return dataBean.getResult();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        return null;
    }

    /**
     * 根据对象属性值来查询数据
     *
     * @param t
     * @return List<T> list
     */
    public List<T> find(T t) {
        try {
            Class tClass = t.getClass();
            boolean isMate = tClass.isAnnotationPresent(BeanMeta.class);
            String tableName = null;
            String tableDesc = null;
            if (isMate) {
                BeanMeta mate = (BeanMeta) tClass.getAnnotation(BeanMeta.class);
                String value = mate.value();
                String desc = mate.desc();
                if (value != null && !"".equals(value)) {
                    tableName = value;
                    tableDesc = desc;
                } else {
                    tableName = tClass.getSimpleName();
                    tableDesc = "table not desc";
                }
            } else {
                tableName = tClass.getSimpleName();
                tableDesc = "table not desc";
            }
            StringBuffer sql = new StringBuffer("SELECT * FROM ");
            //append table
            sql.append(tableName + " ");
            //get column
            List<SqlBean> sqlBeanList = new ArrayList<>();
            Field[] fields = ObjectUtil.getFields(t);
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                boolean isField = field.isAnnotationPresent(BeanField.class);
                if (isField) {
                    BeanField beanField = field.getAnnotation(BeanField.class);
                    String column = beanField.column();
                    if (column != null && !"".equals(column)) {
                        Object o = field.get(t);
                        if (o != null) {
                            sqlBeanList.add(new SqlBean(field.getName(), column + " ", field.get(t)));
                        }

                    }
                }
            }
            if (!sqlBeanList.isEmpty()) {
                sql.append(" WHERE ");
            }
            //make column = value
            for (int i = 0; i < sqlBeanList.size(); i++) {
                if (i == sqlBeanList.size() - 1) {
                    sql.append(sqlBeanList.get(i).getColumn() + "= ? ");
                } else {
                    sql.append(sqlBeanList.get(i).getColumn() + "= ? AND ");
                }
            }
            //make  params
            List<Object> pL = new ArrayList<>();
            for (int i = 0; i < sqlBeanList.size(); i++) {
                pL.add(sqlBeanList.get(i).getValue());
            }
            DataBean dataBean = JDBCUtil.execution(sql.toString(), JDBCExecutionType.SELECT,  t.getClass(), null, pL.toArray());
            List<T> dataList = dataBean.getDataList();
            return dataList;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据ID查询数据
     *
     * @param t
     * @return
     */
    public T findById(T t) {
        List<T> ts = find(t);
        if(ts != null && !ts.isEmpty()){
            return ts.get(0);
        }
        return null;
    }

}
