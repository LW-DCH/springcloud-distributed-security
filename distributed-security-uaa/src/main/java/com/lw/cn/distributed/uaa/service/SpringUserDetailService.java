package com.lw.cn.distributed.uaa.service;


import com.lw.cn.distributed.uaa.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author:刘伟
 * @date:2021/7/12 22:55
 * @description:
 */
@Service
public class SpringUserDetailService implements UserDetailsService {

    @Autowired
    UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        com.lw.cn.distributed.uaa.model.User userByUserName = userDao.getUserByUserName(s);
        if (userByUserName == null) {
            return null;
        }
        List<String> tPermission = userDao.getTPermission(userByUserName.getId());
        String[] strings = new String[tPermission.size()];

        UserDetails build = User.withUsername(userByUserName.getUsername()).password(userByUserName.getPassword())
                .authorities(tPermission.toArray(strings)).build();
        return build;
    }
}
