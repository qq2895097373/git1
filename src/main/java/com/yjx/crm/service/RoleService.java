package com.yjx.crm.service;

import com.yjx.crm.base.BaseService;
import com.yjx.crm.dao.ModuleMapper;
import com.yjx.crm.dao.PermissionMapper;
import com.yjx.crm.dao.RoleMapper;
import com.yjx.crm.dao.UserRoleMapper;
import com.yjx.crm.utils.AssertUtil;
import com.yjx.crm.vo.Permission;
import com.yjx.crm.vo.Role;
import com.yjx.crm.vo.User;
import com.yjx.crm.vo.UserRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class RoleService extends BaseService<Role,Integer> {
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private PermissionMapper permissionMapper;
    @Resource
    private ModuleMapper moduleMapper;
    /**
     * 查询⻆⾊列表
     * @return
     */
    public List<Map<String, Object>> queryAllRoles(Integer userId){
        return roleMapper.queryAllRoles(userId);
    }



    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteUser(Integer userId) {
        Role role = selectByPrimaryKey(userId);
        AssertUtil.isTrue(null == userId || null == role, "待删除记录不存在!");
        // 判断⽤户是否绑定了⻆⾊信息
        int count = userRoleMapper.countUserRoleByUserId(userId);
        // 如果绑定了⻆⾊信息则删除对应的数据
        if (count > 0) {
            AssertUtil.isTrue(userRoleMapper.deleteUserRoleByUserId(userId) != count, "⽤户⻆⾊删除失败!");
        }
        role.setIsValid(0);
        AssertUtil.isTrue(roleMapper.updateByPrimaryKeySelective(role) < 1, "⽤户记录删除失败!");
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveRole(Role role){
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()),"请输⼊⻆⾊名!");
        Role temp = roleMapper.queryRoleByRoleName(role.getRoleName());
        AssertUtil.isTrue(null !=temp,"该⻆⾊已存在!");
        role.setIsValid(1);
        role.setCreateDate(new Date());
        role.setUpdateDate(new Date());
        AssertUtil.isTrue(roleMapper.insertSelective(role)<1,"⻆⾊记录添加失败!");
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void  updateRole(Role role){
        AssertUtil.isTrue(null==role.getId()||null==selectByPrimaryKey(role.getId()),"待修改的记录不存在!");
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()),"请输⼊⻆⾊名!");
        Role temp = roleMapper.queryRoleByRoleName(role.getRoleName());
        AssertUtil.isTrue(null !=temp && !(temp.getId().equals(role.getId())),"该⻆⾊已存在!");
        role.setUpdateDate(new Date());
        AssertUtil.isTrue(roleMapper.updateByPrimaryKeySelective(role)<1,"⻆⾊记录更新失败!");
    }

    public void deleteRole(Integer roleId){
        Role temp =selectByPrimaryKey(roleId);
        AssertUtil.isTrue(null==roleId||null==temp,"待删除的记录不存在!");
        temp.setIsValid(0);
        AssertUtil.isTrue(roleMapper.updateByPrimaryKeySelective(temp)<1,"⻆⾊记录删除失败!");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void addGrant(Integer[] mids, Integer roleId) {
        /**
         * 核⼼表-t_permission  t_role(校验⻆⾊存在)
         *   如果⻆⾊存在原始权限  删除⻆⾊原始权限
         *     然后添加⻆⾊新的权限 批量添加权限记录到t_permission
         */
        Role temp =selectByPrimaryKey(roleId);
        AssertUtil.isTrue(null==roleId||null==temp,"待授权的⻆⾊不存在!");
        Integer count = permissionMapper.countPermissionByRoleId(roleId);
        if(count>0){
            AssertUtil.isTrue(permissionMapper.deletePermissionsByRoleId(roleId)<count,"权限分配失败!");
        }
        if(null !=mids && mids.length>0){
            List<Permission> permissions=new ArrayList<>();
            for (Integer mid : mids) {
                Permission permission=new Permission();
                permission.setCreateDate(new Date());
                permission.setUpdateDate(new Date());
                permission.setModuleId(mid);
                permission.setRoleId(roleId);
                permission.setAclValue(moduleMapper.selectByPrimaryKey(mid).getOptValue());
                permissions.add(permission);
            }
            AssertUtil.isTrue(permissionMapper.insertBatch(permissions)!=permissions.size(),"失败！") ;
        }
    }
}
