package org.session.defaults;

import org.binding.MapperRegistry;
import org.mapping.BoundSql;
import org.mapping.Environment;
import org.mapping.MappedStatement;
import org.session.Configuration;
import org.session.SqlSession;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DefaultSqlSession implements SqlSession {
    private final Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }
    @Override
    public <T> T selectOne(String statement) {
//        try{
//            XNode xNode = mapperElement.get(statement);
//            PreparedStatement preparedStatement = connection.prepareStatement(xNode.getSql());
//            ResultSet resultSet = preparedStatement.executeQuery();
//            List<T> objects = resultSet2Obj(resultSet, Class.forName(xNode.getResultType()));
//            return objects.get(0);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
        return (T)("你的操作被代理了" + statement);
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        try{
            MappedStatement mappedStatement = configuration.getMappedStatement(statement);
            Environment environment = configuration.getEnvironment();

            Connection connection = environment.getDataSource().getConnection();
            BoundSql boundSql = mappedStatement.getBoundSql();
            PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSql());
            preparedStatement.setLong(1, Long.parseLong(((Object[]) parameter)[0].toString()));
            ResultSet resultSet = preparedStatement.executeQuery();

            List<T> objList = resultSet2Obj(resultSet, Class.forName(boundSql.getResultType()));
            return objList.get(0);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private <T> List<T> resultSet2Obj(ResultSet resultSet, Class<?> clazz) {
        List<T> list = new ArrayList<>();
        try{
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                T obj = (T) clazz.newInstance();
                for (int i = 1; i <= columnCount; i++) {
                    Object value = resultSet.getObject(i);
                    String columnName = metaData.getColumnName(i);
                    String setMethod = "set" + columnName.substring(0, 1).toUpperCase() + columnName.substring(1);
                    Method method;
                    if (value instanceof Timestamp) {
                        method = clazz.getMethod(setMethod, Date.class);
                    } else {
                        method = clazz.getMethod(setMethod, value.getClass());
                    }
                    method.invoke(obj, value);
                }
                list.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


//    @Override
//    public <T> T selectOne(String statement, Object parameter) {
//        XNode xNode = mapperElement.get(statement);
//        Map<Integer, String> parameterMap = xNode.getParameter();
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(xNode.getSql());
//            buildParameter(preparedStatement, parameter, parameterMap);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            List<T> objects = resultSet2Obj(resultSet, Class.forName(xNode.getResultType()));
//            return objects.get(0);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @Override
//    public <T> List<T> selectList(String statement) {
//        XNode xNode = mapperElement.get(statement);
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(xNode.getSql());
//            ResultSet resultSet = preparedStatement.executeQuery();
//            return resultSet2Obj(resultSet, Class.forName(xNode.getResultType()));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @Override
//    public <T> List<T> selectList(String statement, Object parameter) {
//        XNode xNode = mapperElement.get(statement);
//        Map<Integer, String> parameterMap = xNode.getParameter();
//        try {
//            // 创建预编译语句
//            PreparedStatement preparedStatement = connection.prepareStatement(xNode.getSql());
//            // 给sqi中参数赋值 map<索引，值>
//            buildParameter(preparedStatement, parameter, parameterMap);
//            // 执行查询
//            ResultSet resultSet = preparedStatement.executeQuery();
//            // 读取查询结果并解析
//            return resultSet2Obj(resultSet, Class.forName(xNode.getResultType()));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @Override
//    public void close() {
//        if (connection == null) {
//            return;
//        }
//        try {
//            connection.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

//    private <T> List<T> resultSet2Obj(ResultSet resultSet, Class<?> clazz) {
//        List<T> list = new ArrayList<>();
//        try{
//            ResultSetMetaData metaData = resultSet.getMetaData();
//            int columnCount = metaData.getColumnCount();
//            while (resultSet.next()) {
//                T obj = (T) clazz.newInstance();
//                for (int i = 1; i <= columnCount; i++) {
//                    Object value = resultSet.getObject(i);
//                    String columnName = metaData.getColumnName(i);
//                    String setMethod = "set" + columnName.substring(0,1).toUpperCase() + columnName.substring(1);
//                    Method method;
//                    if (value instanceof Timestamp) {
//                        method = clazz.getMethod(setMethod, Date.class);
//                    } else {
//                        method =clazz.getMethod(setMethod, value.getClass());
//                    }
//                    method.invoke(obj, value);
//
//                }
//                list.add(obj);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
//
//    private void buildParameter(PreparedStatement preparedStatement, Object parameter, Map<Integer, String> parameterMap)
//    throws SQLException, IllegalAccessException{
//        int size = parameterMap.size();
//        if (parameter instanceof Long) {
//            for (int i = 1; i <= size; i++) {
//                preparedStatement.setLong(i, Long.parseLong(parameter.toString()));
//            }
//            return;
//        }
//        if (parameter instanceof Integer) {
//            for (int i = 1; i <= size; i++) {
//                preparedStatement.setInt(i, Integer.parseInt(parameter.toString()));
//            }
//            return;
//        }
//
//        if (parameter instanceof String) {
//            for (int i = 1; i <= size; i++) {
//                preparedStatement.setString(i, parameter.toString());
//            }
//            return;
//        }
//
//        Map<String, Object> fieldMap = new HashMap<>();
//        // 获取参数名字到参数值的映射
//        Field[] declaredFields = parameter.getClass().getDeclaredFields();
//        for (Field field : declaredFields) {
//            String name = field.getName();
//            field.setAccessible(true);
//            Object obj =  field.get(parameter);
//            fieldMap.put(name, obj);
//        }
//
//        for (int i = 1; i <= size; i++) {
//            String parameterDefine = parameterMap.get(i);
//            Object obj = fieldMap.get(parameterDefine);
//
//            if (obj instanceof Short) {
//                preparedStatement.setShort(i, Short.parseShort(obj.toString()));
//                continue;
//            }
//
//            if (obj instanceof Integer) {
//                preparedStatement.setInt(i, Integer.parseInt(obj.toString()));
//                continue;
//            }
//
//            if (obj instanceof Long) {
//                preparedStatement.setLong(i, Long.parseLong(obj.toString()));
//                continue;
//            }
//
//            if (obj instanceof String) {
//                preparedStatement.setString(i, obj.toString());
//                continue;
//            }
//
//            if (obj instanceof java.util.Date) {
//                preparedStatement.setDate(i, (java.sql.Date) obj);
//            }
//
//        }
//
//    }


    @Override
    public <T> T getMapper(Class<T> type) {
        return configuration.getMapper(type, this);
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

}
