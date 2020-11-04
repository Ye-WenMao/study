package com.ywm.controller;

import com.ywm.entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: YEWENMAO
 * @data: 2020/11/1 14:20
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/getUsername")
    public Result getUsername() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();
        return new Result(true, "",username);
    }

}
