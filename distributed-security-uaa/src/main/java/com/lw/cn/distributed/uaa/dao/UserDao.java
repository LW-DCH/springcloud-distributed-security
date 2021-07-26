package com.lw.cn.distributed.uaa.dao;

import com.lw.cn.distributed.uaa.model.TPermission;

import com.lw.cn.distributed.uaa.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:刘伟
 * @date:2021/7/11 21:36
 * @description:
 */
@Component
public class UserDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public User getUserByUserName(String username) {
        String sql = "SELECT * FROM t_user WHERE t_user.username=? ";
        List<User> list = jdbcTemplate.query(sql, new Object[]{username}, new BeanPropertyRowMapper<>(User.class));
        if (!list.isEmpty() && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 获取用户权限
     * @param id
     * @return
     */
    public List<String> getTPermission(String id) {
        String sql = "SELECT * FROM t_permission WHERE\n" +
                "t_permission.id IN(\n" +
                "SELECT t_role_permission.permission_id FROM t_role_permission WHERE\n" +
                "t_role_permission.role_id IN (\n" +
                "SELECT role_id FROM t_user_role WHERE t_user_role.user_id=?\n" +
                ")\n" +
                ") ";
        List<TPermission> t = jdbcTemplate.query(sql, new Object[]{id}, new BeanPropertyRowMapper<>(TPermission.class));
        List<String> code =new ArrayList<>();
        for (TPermission tPermission : t) {
            code.add(tPermission.getCode());
        }
        return code;
    }
}
