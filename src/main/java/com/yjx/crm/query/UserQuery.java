package com.yjx.crm.query;

import com.yjx.crm.base.BaseQuery;
import lombok.Data;

@Data
public class UserQuery extends BaseQuery {
    // ⽤户名
    private String userName;
    // 邮箱
    private String email;
    // 电话
    private String phone;
}
