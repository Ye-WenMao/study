package com.ywm.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ywm.UserService;
import com.ywm.pojo.Permission;
import com.ywm.pojo.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author: YEWENMAO
 * @data: 2020/10/31 20:27
 */
@Component
public class SpringSecurityUserService implements UserDetailsService {

    @Reference
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.ywm.pojo.User userInDB = userService.findUserByUserName(username);


        List<SimpleGrantedAuthority> list = new ArrayList<>();
        SimpleGrantedAuthority sga = null;
        if (null != userInDB) {
            String password = userInDB.getPassword();
            Set<Role> roles = userInDB.getRoles();
            if (null != roles) {
                for (Role role : roles) {
                    sga = new SimpleGrantedAuthority(role.getKeyword());
                    list.add(sga);
                    Set<Permission> permissions = role.getPermissions();
                    if (null!=permissions) {
                        for (Permission permission : permissions) {
                            sga = new SimpleGrantedAuthority(permission.getKeyword());
                             list.add(sga);
                        }
                    }
                }
            }

            return new User(username, password, list);
        }

        return null;
    }
}
