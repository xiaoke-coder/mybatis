package org.session;

import java.util.List;

/**
 * 对数据库进行定义和处理的类
 */
public interface SqlSession {
    //todo 学习如何参数化类型
    <T> T selectOne(String statement);

    <T> T selectOne(String statement, Object parameter);

//    <T> List<T> selectList(String statement);
//
//    <T> List<T> selectList(String statement, Object parameter);
//
//    void close();

    <T>T getMapper(Class<T> type);
}
