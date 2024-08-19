package com.yjx.crm.service;

import com.yjx.crm.annotaions.RequirePermission;
import com.yjx.crm.base.BaseService;
import com.yjx.crm.dao.PermissionMapper;
import com.yjx.crm.query.SaleChanceQuery;
import com.yjx.crm.utils.LoginUserUtil;
import com.yjx.crm.vo.Permission;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class PermissionService extends BaseService<Permission,Integer> {
    @Resource
    private PermissionMapper permissionMapper;
    @Resource
    private SaleChanceService saleChanceService;
    public List<String> queryUserHasRolesHasPermissions(Integer userId) {
        return  permissionMapper.queryUserHasRolesHasPermissions(userId);
    }

    @RequestMapping("list")
    @ResponseBody
    @RequirePermission(code = "101001")
    public Map<String,Object> querySaleChancesByParams(Integer flag, HttpServletRequest
            request, SaleChanceQuery saleChanceQuery){
        if(null !=flag &&flag==1){
            // 查询分配给当前登录⽤户 营销记录
            saleChanceQuery.setAssignMan(LoginUserUtil.releaseUserIdFromCookie(request));
        }
        return  saleChanceService.queryByParamsForTable(saleChanceQuery);
    }
}
