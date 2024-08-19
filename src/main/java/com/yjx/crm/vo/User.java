package com.yjx.crm.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;

    private String userName;

    private String userPwd;

    private String trueName;

    private String email;

    private String phone;

    private Integer isValid;
    private String roleIds;

    //    @JsonFormat(pattern = "yyyy-MM-dd")
//    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Date createDate;
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Date updateDate;


}