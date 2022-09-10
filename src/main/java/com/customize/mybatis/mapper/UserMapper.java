package com.customize.mybatis.mapper;

import com.customize.mybatis.entity.User;

import java.util.List;

public interface UserMapper {
    User selectById(Integer id);

    List<User> selectAll();
}
