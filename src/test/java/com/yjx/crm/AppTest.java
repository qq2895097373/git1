package com.yjx.crm;

import com.yjx.crm.dao.UserRoleMapper;
import com.yjx.crm.service.RoleService;
import com.yjx.crm.service.UserService;
import com.yjx.crm.vo.User;
import com.yjx.crm.vo.UserRole;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@SpringBootTest
public class AppTest extends TestCase {
    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;

    @Test
    public void test01(){
        Integer a =12;
        Integer i = userRoleMapper.countUserRoleByUserId(12);
        System.out.println(i);
        Integer i1 = userRoleMapper.deleteUserRoleByUserId(12);
        System.out.println(i1);
    }

    @Test
    public void test02(){
        List<UserRole> userRoles = new ArrayList<>();
        UserRole userRole = new UserRole();
        userRole.setRoleId(3);
        userRole.setUserId(12);
        userRole.setCreateDate(new Date());
        userRole.setUpdateDate(new Date());
        UserRole userRole1 = new UserRole();
        userRole1.setRoleId(1);
        userRole1.setUserId(12);
        userRole1.setCreateDate(new Date());
        userRole1.setUpdateDate(new Date());
        userRoles.add(userRole);
        userRoles.add(userRole1);


        userRoleMapper.insertBatch(userRoles);
    }
    @Test
    public void test03(){

        userService.relationUserRole(10,"1,2,3");
    }
    @Test
    public void test04(){
        User user = new User();
        user.setId(11);
        user.setUserName("r");
        user.setTrueName("r");
        user.setEmail("126@126.com");
        user.setPhone("13327792152");
        user.setRoleIds("1,2,3");
        userService.updateUser(user);
    }
    @Test
    public void test05() {
        Integer[] s = {1,2};
        roleService.addGrant(s,18);
    }


    public void test06(){
        System.out.println("测试!");
    }

}
