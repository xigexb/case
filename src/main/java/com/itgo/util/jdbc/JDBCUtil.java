package com.itgo.util.jdbc;


import com.itgo.annotation.BeanField;
import com.itgo.annotation.BeanMeta;
import com.itgo.bean.DataBean;
import com.itgo.enums.JDBCExecutionType;
import com.itgo.exception.JDBCInitializationException;
import com.itgo.exception.NoSuchAnnotationException;
import com.itgo.util.date.DateUtil;
import com.itgo.util.file.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;
import java.util.Date;

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
     * 获取一个数据库连接
     * @return Connection
     */
    public static Connection getConn() {
        return getConn(url,userName,password);
    }

    /**
     * 获取一个数据库连接
     * @return Connection
     */
    public static Connection getConn(String url ,String userName,String password) {
        try {
            String msg = checkParams(url, userName, password);
            if(!"ok".equals(msg)){
                logger.error("数据库配置项参数为空，请提供数据库连接参数信息:)");
                throw new IllegalArgumentException(msg);
            }else {
                Connection connection = DriverManager.getConnection(url, userName, password);
                if(connection != null){
                    return connection;
                }else {
                    throw new SQLException("获取连接失败，请检查数据库连接配置:)");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }


    /**
     * 关闭资源
     * @param ps
     * @param rs
     */
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

    /**
     * 获取所有表名
     * @param url
     * @param userName
     * @param password
     * @return
     */
    public static List<String> getTableName(String url ,String userName,String password) {
        List<String> result = new ArrayList<>();
        Connection connection = null;
        try{
            connection = getConn(url,userName,password);
            ResultSet rs = connection.getMetaData().getTables(null, null, null, new String[]{"TABLE"});
            while(rs.next()){
                String tableName = rs.getString("TABLE_NAME");
                System.out.println(tableName);
                result.add(tableName);
            }
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    /**
     * 获取所有表名
     * @return
     */
    public static List<String> getTableName() {
        return getTableName(url,userName,password);
    }

    private static String getCatalogByUrl(String url){
        String[] arrsys = url.split("/");
        if(null != arrsys && arrsys.length > 0){
            String cataLogUrl = arrsys[arrsys.length-1];
            String[] cataArr = cataLogUrl.split("\\?");
            return cataArr[0];
        }
        return null;

    }

    /**
     * 获取所有字段名
     * @param url
     * @param userName
     * @param password
     * @param tableName
     * @return
     */
    public static List<Map<String,String>> getColumn(String url , String userName, String password, String tableName){
        List<Map<String,String>> result = new ArrayList<>();
        Connection connection = null;
        try{
            connection = getConn(url,userName,password);
            ResultSet columnsResult = connection.getMetaData().getColumns(null, "%", tableName, "%");

            String catalog = getCatalogByUrl(url);
            String pkName = "";
            ResultSet primaryKeyResultSet = connection.getMetaData().getPrimaryKeys(catalog,null,tableName);
            while(primaryKeyResultSet.next()){
                pkName = primaryKeyResultSet.getString("COLUMN_NAME");
            }
            while(columnsResult.next()){
                Map<String,String> resultMap = new HashMap();
                String columnName = columnsResult.getString("COLUMN_NAME");
                String typeName = columnsResult.getString("TYPE_NAME");
                String columnRemarks = columnsResult.getString("REMARKS");

                resultMap.put("column",columnName);
                resultMap.put("type",typeName);
                resultMap.put("remark",columnRemarks);
                if(pkName.equals(columnName)){
                    resultMap.put("isPk","1");
                }
//                else {
//                    resultMap.put("isPk",false);
//                }
                result.add(resultMap);
            }
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    /**
     * 获取所有字段名
     * @param tableName 表名
     * @return
     */
    public static List<Map<String,String>> getColumn(String tableName){
        return getColumn(url,userName,password,tableName);
    }

    /**
     * 结果集-->map
     * @param url
     * @param userName
     * @param password
     * @param sql
     * @return
     */
    public static List<Map<String,String>> getResultListMap(String url , String userName, String password, String sql){
        List<Map<String,String>> result = new ArrayList<>();
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
        try{
            connection = getConn(url,userName,password);
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData  rsmd = rs.getMetaData();
            while(rs.next()){
                Map<String,Object> map = new HashMap<String,Object>();
                for(int i = 0 ; i < rsmd.getColumnCount() ; i++){
                    String col_name = rsmd.getColumnName(i+1);
                    Object col_value = rs.getObject(col_name);
                    if(col_value == null){
                        col_value = "";
                    }
                    map.put(col_name, col_value);
                }
                mapList.add(map);
            }

            return result;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(rs != null ) {
                    rs.close();
                    ps.close();
                }
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;

    }


    /**
     * 结果集-->list<map>
     * @param sql sql语句
     * @return
     */
    public static List<Map<String,String>> getResultListMap(String sql){
        return getResultListMap(url ,userName,password,sql);
    }


    /**
     * 结果集-->map
     * @param url
     * @param userName
     * @param password
     * @param sql
     * @return
     */
    public static Map<String,Object> getResultMap(String url , String userName, String password, String sql){
        List<Map<String,String>> result = new ArrayList<>();
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
        try{
            connection = getConn(url,userName,password);
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData  rsmd = rs.getMetaData();
            int count=0;
            while(rs.next()){
                if(count>0) {
                    break;
                }
                count = count +1;
                Map<String,Object> map = new HashMap<String,Object>();
                for(int i = 0 ; i < rsmd.getColumnCount() ; i++){
                    String col_name = rsmd.getColumnName(i+1);
                    Object col_value = rs.getObject(col_name);
                    Optional<Object> col_value1Opt = Optional.ofNullable(col_value);
                    if(col_value1Opt.orElse("") instanceof java.util.Date){
                        map.put(col_name, DateUtil.date2String((Date) col_value1Opt.get(),"yyyy-MM-dd"));
                    }else{
                        map.put(col_name,col_value1Opt.orElse("") );
                    }
                }
                mapList.add(map);
            }
            if(mapList.size()>0) {
                return mapList.get(0);
            }else {
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(rs != null ) {
                    rs.close();
                    ps.close();
                }
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    /**
     * 结果集-->map
     * @param sql
     * @return
     */
    public static Map<String,Object> getResultMap(String sql){
        return getResultMap(url ,userName,password,sql);
    }

    /**
     * 检查数据库连接参数
     * @param url url
     * @param userName username
     * @param password password
     * @return msg
     */
    private static String checkParams(String url,String userName,String password){
        StringBuffer sb = new StringBuffer("为空:)");
        if(url == null || "".equals(url)){
            return sb.insert(0,"数据库连接URL").toString();
        }
        if(userName == null || "".equals(userName)){
            return sb.insert(0,"数据库连接用户名").toString();
        }
        if(password == null || "".equals(password)){
            return sb.insert(0,"数据库连接密码").toString();
        }
        return "ok";
    }

}

