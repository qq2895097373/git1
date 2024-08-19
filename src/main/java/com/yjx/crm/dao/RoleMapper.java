package com.yjx.crm.dao;

import com.yjx.crm.base.BaseMapper;
import com.yjx.crm.vo.Role;

import java.util.List;
import java.util.Map;

public interface RoleMapper extends BaseMapper<Role,Integer> {
    // 查询⻆⾊列表
    public  List<Map<String,Object>> queryAllRoles(Integer userId);
    Integer deleteByPrimaryKey(Integer id);

    Integer insert(Role record);

    Integer insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    Integer updateByPrimaryKeySelective(Role record);

    Integer updateByPrimaryKey(Role record);

    Role queryRoleByRoleName(String roleName);
}