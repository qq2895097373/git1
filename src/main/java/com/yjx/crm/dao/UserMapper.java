package com.yjx.crm.dao;

import com.yjx.crm.base.BaseMapper;
import com.yjx.crm.vo.User;

import java.util.List;
import java.util.Map;

public interface UserMapper extends BaseMapper<User,Integer> {
    // 根据⽤户名查询⽤户对象
    User queryUserByUserName(String userName);
    // 查询所有的销售⼈员
    List<Map<String, Object>> queryAllSales();
    Integer deleteByPrimaryKey(Integer id);

    Integer insert(User record);

    Integer insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    Integer updateByPrimaryKeySelective(User record);

    Integer updateByPrimaryKey(User record);
}