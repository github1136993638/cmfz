package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class User implements Serializable {
    @Excel(name = "id")
    private String id;
    @Excel(name = "phone")
    private String phone;
    @Excel(name = "password")
    private String password;
    @Excel(name = "head_picture", type = 2, width = 30, height = 20)
    private String head_picture;
    @Excel(name = "name")
    private String name;
    @Excel(name = "dharma")
    private String dharma;
    @Excel(name = "sex", suffix = "生")//replace={"男_1","女_2"}
    private String sex;
    @Excel(name = "province")
    private String province;
    @Excel(name = "city")
    private String city;
    @Excel(name = "sign")
    private String sign;
    @Excel(name = "status")
    private String status;
    @Excel(name = "create_date", format = "yyyy-MM-dd", width = 20)
    private Date create_date;
    @Excel(name = "guru")
    private String guru;
}
