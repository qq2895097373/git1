package com.yjx.crm.dao;

import com.yjx.crm.base.BaseMapper;
import com.yjx.crm.vo.Permission;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission,Integer> {
    List<String> queryUserHasRolesHasPermissions(Integer userId);

    Integer deleteByPrimaryKey(Integer id);

    Integer insert(Permission record);

    Integer insertSelective(Permission record);

    Permission selectByPrimaryKey(Integer id);

    Integer  updateByPrimaryKeySelective(Permission record);

    Integer updateByPrimaryKey(Permission record);

    Integer countPermissionByRoleId(Integer roleId);

    Integer deletePermissionsByRoleId(Integer roleId);

    List<Integer> queryRoleHasAllModuleIdsByRoleId(Integer roleId);

    Integer countPermissionsByModuleId(Integer mid);

    Integer deletePermissionsByModuleId(Integer mid);
}