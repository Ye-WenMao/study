package com.ywm.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ywm.UserService;
import com.ywm.dao.UserDao;
import com.ywm.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: YEWENMAO
 * @data: 2020/11/1 14:05
 */
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Override
    public User findUserByUserName(String username) {
        return userDao.findUserByUserName(username);
    }
}
