package org.example.test;

import com.alibaba.fastjson.JSON;
import org.example.Resources;
import org.example.SqlSession;
import org.example.SqlSessionFactory;
import org.example.SqlSessionFactoryBuilder;
import org.example.test.po.User;
import org.junit.Test;

import java.io.Reader;
import java.util.List;

public class ApiTest {
    @Test
    public void test_queryUserInfoById() {
        String resource = "mybatis-config-datasource.xml";
        Reader reader;
        try{
            reader = Resources.getResourceAsReader(resource);
            SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlMapper.openSession();
            try {
                User user = sqlSession.selectOne("org.example.test.dao.IUserDao.queryUserInfoById", 1L);
                System.out.println(JSON.toJSONString(user));
            } finally {
                sqlSession.close();
                reader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_queryUserList() {
        String resource = "mybatis-config-datasource.xml";
        Reader reader;
        try {
            reader = Resources.getResourceAsReader(resource);
            SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);

            SqlSession session = sqlMapper.openSession();
            try {
                User req = new User();
                req.setUserId("10001");
                List<User> userList = session.selectList("cn.bugstack.mybatis.test.dao.IUserDao.queryUserList", req);
                System.out.println(JSON.toJSONString(userList));
            } finally {
                session.close();
                reader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


