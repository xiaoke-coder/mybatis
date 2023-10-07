package org.example.test.dao;

import org.example.test.po.User;

import java.util.List;

public interface IUserDao {
    User queryUserInfoById(Long id);

    List<String> queryUserList(User user);

    String queryUserName(String uId);

    Integer queryUserAge(String uId);
}
