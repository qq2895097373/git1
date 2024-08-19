package com.yjx.crm.query;

import com.yjx.crm.base.BaseQuery;
import lombok.Data;

@Data
public class SaleChanceQuery  extends BaseQuery {
    private String customerName; // 客户名称
    private String createMan; // 创建⼈
    private String state; // 分配状态
    private Integer devResult; // 开发状态
    private Integer assignMan;// 分配⼈

}
