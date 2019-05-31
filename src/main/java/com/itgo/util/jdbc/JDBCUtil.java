package com.itgo.util.jdbc;


import com.itgo.annotation.BeanField;
import com.itgo.annotation.BeanMeta;
import com.itgo.bean.DataBean;
import com.itgo.enums.JDBCExecutionType;
import com.itgo.exception.JDBCInitializationException;
import com.itgo.exception.NoSuchAnnotationException;
import com.itgo.util.file.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Create by xigexb
 *
 * @versio 1.0.0
 * @Author xigexb
 * @date 2019/5/15 12:37
 * @description
 *      desc:jdbc工具类
 */
public class JDBCUtil {

    private static  final Logger logger = LoggerFactory.getLogger(JDBCUtil.class);
    /**
     * 数据库连接驱动
     */
    private static String driver;

    /**
     * 数据库连接uril
     */
    private static String url;


    /**
     * 数据库连接用户名
     */
    private static String userName;


    /**
     * 数据库连接密码
     */
    private static String password;


//    /**
//     * 使用静态代码块，加载数据库一些连接配置信息
//     */
//    static {
//        Properties properties = PropertiesUtil.loadProps("db.properties");
//        driver = PropertiesUtil.getString(properties, "driver");
//        url = PropertiesUtil.getString(properties, "url");
//        userName = PropertiesUtil.getString(properties, "userName");
//        password = PropertiesUtil.getString(properties, "password");
//        //加载jar文件中的驱动类
//        try {
//            Class.forName(driver);
//            logger.info("initialization jdbc config file{} success",);
//        } catch (ClassNotFoundException e) {
//            logger.info("initialization jdbc config file{} failure");
//            e.printStackTrace();
//        }
//    }


    public static void init(String configPath){
        Properties properties = PropertiesUtil.loadProps(configPath);
        driver = PropertiesUtil.getString(properties, "driver");
        url = PropertiesUtil.getString(properties, "url");
        userName = PropertiesUtil.getString(properties, "userName");
        password = PropertiesUtil.getString(properties, "password");
        //加载jar文件中的驱动类
        try {
            Class.forName(driver);
            logger.info("initialization jdbc config file {} success",configPath);
        } catch (ClassNotFoundException e) {
            logger.info("initialization jdbc config file {} failure",configPath);
            e.printStackTrace();
        }
    }


    /**
     * 获取一个数据库连接 Connection
     *
     * @return
     */
    public static Connection getConn() {
        try {
            if(url == null ||"".equals(url) || userName == null ||"".equals(userName) || password == null ||"".equals(password)){
                logger.error("数据库配置项参数为空，请先调用 init 方法进行数据库配置项初始化:)");
                throw new JDBCInitializationException("数据库配置项参数为空:)");
            }
            // DriverManager 数据库驱动管家，可以用它来获取一个数据库连接 参数：连接地址 用户名 密码
            return DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    // 2.关闭所有数据库连接,要考虑的问题,方法有没有参数,有没有返回值
    public static void closeAll(PreparedStatement ps, ResultSet rs) {
        try {
            // 1.先关闭rs
            if (rs != null) {
                rs.close();
            }
            // 2.关闭ps
            if (ps != null) {
                ps.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 增删改通用方法 增加返回对象 修改和删除返回受影响的行数
     * @param sql SQL语句
     * @param execution SQL执行类型  insert  update  delete
     * @param clazz  返回对象范型class
     * @param primaryKeyName  主键字段名称
     * @param params 参数列表
     * @param <T> 范型
     * @return DataBean<T>
     */
    public static <T> DataBean<T> execution(String sql, JDBCExecutionType execution, Class<T> clazz, String primaryKeyName, Object... params) {
        DataBean<T> dataBean = new DataBean<>();
        try (Connection conn = JDBCUtil.getConn()) {
            if (sql == null || "".equals(sql)) {
                return null;
            }
            PreparedStatement ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            if (params != null && params.length > 0) {
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(i + 1, params[i]);
                }
            }
            Integer count;
            switch (execution) {
                case INSERT:
                    int i1 = ps.executeUpdate();
                    if(i1>0){
                        T t = clazz.newInstance();
                        ResultSet rs = ps.getGeneratedKeys();
                        if(rs.next()){
                            String id = rs.getString(1);
                            boolean present = t.getClass().isAnnotationPresent(BeanMeta.class);
                            if(!present){
                                throw new NoSuchAnnotationException("在["+t.getClass().getName()+"]类，没有找到[@"+BeanMeta.class.getSimpleName()+"]");
                            }
                            BeanMeta meta = t.getClass().getAnnotation(BeanMeta.class);
                            sql = "select * from  " + meta.value() + " WHERE " + primaryKeyName + " = " + "\'" + id + "\'";
                            PreparedStatement preparedStatement = conn.prepareStatement(sql);
                            ResultSet rs1 = preparedStatement.executeQuery();
                            if (rs1.next()) {
                                dataBean.setMsg("insert");
                                dataBean.setData(parseData(rs1, clazz));
                                return dataBean;
                            }
                            closeAll(preparedStatement, rs1);
                            closeAll(ps, rs);
                        }
                    }
                    break;
                case UPDATE:
                    dataBean.setMsg("update");
                case DELETE:
                    count = ps.executeUpdate();
                    dataBean.setMsg("delete");
                    dataBean.setResult(count.toString());
                    break;
                case SELECT:
                    ResultSet resultSet = ps.executeQuery();
                    List<T> dataList = null;
                    while (resultSet.next()){
                        if(dataList == null ){
                            dataList = new ArrayList<>();
                        }
                        dataList.add(parseData(resultSet,clazz));
                    }
                    dataBean.setDataList(dataList);
                    dataBean.setMsg("select");
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }catch (NoSuchAnnotationException e){
            e.printStackTrace();
        }
        return dataBean;
    }


    public static  <T>  T parseData(ResultSet resultSet,Class<T> tClass) throws SQLException, IllegalAccessException, InstantiationException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        T t = tClass.newInstance();
        Field[] fields = t.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            boolean annotationPresent = fields[i].isAnnotationPresent(BeanField.class);
            if (annotationPresent) {
                fields[i].setAccessible(true);
                BeanField beanField = fields[i].getAnnotation(BeanField.class);
                String columnName = beanField.value();
                for (Integer j = 0; j < columnCount; j++) {
                    String columnRealName = metaData.getColumnName(j + 1);
                    if (columnRealName.equals(columnName)) {
                        fields[i].set(t, resultSet.getObject(columnName));
                    }
                }
            }
        }
        return t;
    }


}

