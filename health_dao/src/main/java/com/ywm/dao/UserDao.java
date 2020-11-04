package com.ywm.dao;

import com.ywm.pojo.User;

/**
 * @author: YEWENMAO
 * @data: 2020/11/1 14:07
 */
public interface UserDao {
    User findUserByUserName(String username);
}
