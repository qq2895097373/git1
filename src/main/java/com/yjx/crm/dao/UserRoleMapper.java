package com.yjx.crm.dao;

import com.yjx.crm.base.BaseMapper;
import com.yjx.crm.vo.UserRole;

public interface UserRoleMapper extends BaseMapper<UserRole,Integer> {
    Integer deleteByPrimaryKey(Integer id);

    Integer insert(UserRole record);

    Integer insertSelective(UserRole record);

    UserRole selectByPrimaryKey(Integer id);

    Integer updateByPrimaryKeySelective(UserRole record);

    Integer updateByPrimaryKey(UserRole record);

    Integer countUserRoleByUserId(Integer useId);

    Integer deleteUserRoleByUserId(Integer useId);
}