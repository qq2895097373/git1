package com.yjx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yjx.crm.base.BaseService;
import com.yjx.crm.dao.SaleChanceMapper;
import com.yjx.crm.query.SaleChanceQuery;
import com.yjx.crm.utils.AssertUtil;
import com.yjx.crm.utils.PhoneUtil;
import com.yjx.crm.vo.SaleChance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class SaleChanceService  extends BaseService<SaleChance, Integer> {
    @Resource
    private SaleChanceMapper saleChangeMapper;

    /**
     * 多条件分⻚查询营销机会 (BaseService 中有对应的⽅法)
     *
     * @param query
     * @return
     */
    public Map<String, Object> querySaleChanceByParams(SaleChanceQuery query) {
        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(query.getPage(), query.getLimit());
        PageInfo<SaleChance> pageInfo = new PageInfo<>(saleChangeMapper.selectByParams(query));
        map.put("code", 0);
        map.put("msg", "success");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }

    /**
     * 营销机会数据添加
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveSaleChance(SaleChance saleChance) {
        // 1.参数校验
        checkParams(saleChance.getCustomerName(), saleChance.getLinkMan(), saleChance.getLinkPhone());
        // 2.设置相关参数默认值

        saleChance.setIsValid(1);
        saleChance.setCreateDate(new Date());
        saleChance.setUpdateDate(new Date());

        // 选择分配⼈
//        if (StringUtils.isNotBlank(saleChance.getAssignMan())) {
        if (!"0".equals(saleChance.getAssignMan())){
            saleChance.setState(1);//
            saleChance.setAssignTime(new Date());
            saleChance.setDevResult(1);//

        }
        // 未选择分配⼈
        else {
            saleChance.setState(0);//
            saleChance.setAssignTime(null);
            saleChance.setDevResult(0);//
        }
        // 3.执⾏添加 判断结果
        AssertUtil.isTrue(saleChangeMapper.insertSelective(saleChance) < 1, "营销机会数据添加失败！");

    }

    /**
     * 基本参数校验
     *
     * @param customerName
     * @param linkMan
     * @param linkPhone
     */
    private void checkParams(String customerName, String linkMan, String linkPhone) {
        AssertUtil.isTrue(StringUtils.isBlank(customerName), "请输⼊客户名！");
        AssertUtil.isTrue(StringUtils.isBlank(linkMan), "请输⼊联系⼈！");
        AssertUtil.isTrue(StringUtils.isBlank(linkPhone), "请输⼊⼿机号！");
        AssertUtil.isTrue(!PhoneUtil.isMobile(linkPhone), "⼿机号格式不正确！");
    }

    /**
     * 营销机会数据更新
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateSaleChance(SaleChance saleChance) {
    // 1.参数校验
    // 通过id查询记录
        SaleChance temp = saleChangeMapper.selectByPrimaryKey(saleChance.getId());
        // 判断是否为空
        AssertUtil.isTrue(null == temp, "待更新记录不存在！");
        // 校验基础参数
        checkParams(saleChance.getCustomerName(), saleChance.getLinkMan(),
                saleChance.getLinkPhone());
        // 2. 设置相关参数值
        saleChance.setUpdateDate(new Date());
        if ("0".equals(temp.getAssignMan())
                && !"0".equals(saleChance.getAssignMan())) {
            // 如果原始记录未分配，修改后改为已分配
//            saleChance.setState(StateStatus.STATED.getType());
            saleChance.setState(1);
            saleChance.setAssignTime(new Date());
            saleChance.setDevResult(1);
        } else if (!"0".equals(temp.getAssignMan())
                && "0".equals(saleChance.getAssignMan())) {
            // 如果原始记录已分配，修改后改为未分配
            saleChance.setAssignMan("0");
            saleChance.setState(0);
            saleChance.setAssignTime(null);
            saleChance.setDevResult(0);
        }
        // 3.执⾏更新 判断结果
        AssertUtil.isTrue(saleChangeMapper.updateByPrimaryKeySelective(saleChance) < 1, "营销机会数据更新失败！");
    }

    /**
     * 营销机会数据删除
     * @param ids
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteSaleChance (Integer[] ids) {
        // 判断要删除的id是否为空
        AssertUtil.isTrue(null == ids || ids.length == 0, "请选择需要删除的数据！");
        // 删除数据
        AssertUtil.isTrue(saleChangeMapper.deleteBatch(ids) < 0, "营销机会数据删除失败！");
    }

    /**
     * 更新营销机会的状态
     *
     成功 = 2
     *
     失败 = 3
     * @param id
     * @param devResult
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateSaleChanceDevResult(Integer id, Integer devResult) {
        AssertUtil.isTrue( null ==id,"待更新记录不存在!");
        SaleChance temp =selectByPrimaryKey(id);
        AssertUtil.isTrue( null ==temp,"待更新记录不存在!");
        temp.setDevResult(devResult);
        AssertUtil.isTrue(updateByPrimaryKeySelective(temp)<1,"机会数据更新失败!");
    }
}