package com.yjx.crm.dao;

import com.yjx.crm.base.BaseMapper;
import com.yjx.crm.vo.SaleChance;

public interface SaleChanceMapper extends BaseMapper<SaleChance,Integer> {
    Integer deleteByPrimaryKey(Integer id);

    Integer insert(SaleChance record);

    Integer insertSelective(SaleChance record);

    SaleChance selectByPrimaryKey(Integer id);

    Integer updateByPrimaryKeySelective(SaleChance record);

    Integer updateByPrimaryKey(SaleChance record);
}