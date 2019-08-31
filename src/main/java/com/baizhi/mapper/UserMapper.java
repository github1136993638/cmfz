package com.baizhi.mapper;

import com.baizhi.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface UserMapper {


    public List<User> findAllUser(@Param("start") Integer start, @Param("rows") Integer rows);

    public Integer selectCount();

    public void updateUser(User user);

    public List<User> findAll();

    public List<User> findAllByProvince(String province);

    public List<User> findAllByDate(Date create_date);

    public User login(@Param("phone") String phone, @Param("password") String password);

    public void regist(User user);
}
