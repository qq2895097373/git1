package com.yjx.crm.dao;

import com.yjx.crm.base.BaseMapper;
import com.yjx.crm.base.BaseQuery;
import com.yjx.crm.vo.CusDevPlan;
import com.yjx.crm.vo.SaleChance;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface CusDevPlanMapper extends BaseMapper<CusDevPlan,Integer> {
    List<CusDevPlan> selectByParams(BaseQuery baseQuery) throws DataAccessException;
    Integer deleteByPrimaryKey(Integer id);

    Integer insert(CusDevPlan record);

    Integer insertSelective(CusDevPlan record);

    CusDevPlan selectByPrimaryKey(Integer id);

    Integer updateByPrimaryKeySelective(CusDevPlan record);

    Integer updateByPrimaryKey(CusDevPlan record);
}