package com.ywm;

import com.ywm.pojo.User;

/**
 * @author: YEWENMAO
 * @data: 2020/11/1 8:29
 */
public interface UserService {


    User findUserByUserName(String username);
}
