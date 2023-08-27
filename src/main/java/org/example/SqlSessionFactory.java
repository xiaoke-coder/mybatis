package org.example;

public interface SqlSessionFactory {
    // 当需要操作数据库时，都会开启一个会话
    SqlSession openSession();
}
