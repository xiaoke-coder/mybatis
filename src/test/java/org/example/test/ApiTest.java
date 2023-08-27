package org.example.test;

import com.alibaba.fastjson.JSON;
import org.binding.MapperProxyFactory;
import org.binding.MapperRegistry;
import org.example.*;
import org.example.test.dao.IUserDao;
import org.example.test.po.User;
import org.junit.Test;
import org.session.SqlSession;
import org.session.SqlSessionFactory;
import org.session.defaults.DefaultSqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ApiTest {

    private Logger logger = LoggerFactory.getLogger(ApiTest.class);
//    @Test
//    public void test_queryUserInfoById() {
//        String resource = "mybatis-config-datasource.xml";
//        Reader reader;
//        try{
//            reader = Resources.getResourceAsReader(resource);
//            SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);
//            SqlSession sqlSession = sqlMapper.openSession();
//            try {
//                User user = sqlSession.selectOne("org.example.test.dao.IUserDao.queryUserInfoById", 1L);
//                System.out.println(JSON.toJSONString(user));
//            } finally {
//                sqlSession.close();
//                reader.close();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void test_queryUserList() {
//        String resource = "mybatis-config-datasource.xml";
//        Reader reader;
//        try {
//            reader = Resources.getResourceAsReader(resource);
//            SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);
//
//            SqlSession session = sqlMapper.openSession();
//            try {
//                User req = new User();
//                req.setUserId("10001");
//                List<User> userList = session.selectList("org.example.test.dao.IUserDao.queryUserList", req);
//                System.out.println(JSON.toJSONString(userList));
//            } finally {
//                session.close();
//                reader.close();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void test_MapperProxyFactory() {
//        MapperProxyFactory<IUserDao> factory = new MapperProxyFactory<>(IUserDao.class);
//        Map<String, String> sqlSession = new HashMap<>();
//        sqlSession.put("org.example.test.dao.IUserDao.queryUserName", "模拟执行 Mapper.xml 中 SQL 语句的操作：查询用户姓名");
//        sqlSession.put("org.example.dao.IUserDao.queryUserAge", "模拟执行 Mapper.xml 中 SQL 语句的操作：查询用户年龄");
//        IUserDao userDao = factory.newInstance(sqlSession);
//        String res = userDao.queryUserName("10001");
//        logger.info("测试结果：{}", res);
//    }

    @Test
    public void test_MapperProxyFactory() {
        // 1. 注册 Mapper
        MapperRegistry registry = new MapperRegistry();
        registry.addMappers("org.example.test.dao");

        // 2. 从 SqlSession 工厂获取 Session
        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(registry);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 3. 获取映射器对象
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);

        // 4. 测试验证
        String res = userDao.queryUserName("10001");
        logger.info("测试结果：{}", res);
    }

}


