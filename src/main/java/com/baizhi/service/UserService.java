package com.baizhi.service;

import com.baizhi.entity.EchartsMap;
import com.baizhi.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserService {

    public Map<Object, Object> findAllUser(Integer page, Integer rows);

    public void updateUser(User user);

    public User login(String phone, String password);

    public void regist(User user);

    public List<User> findAll();

    public Set<EchartsMap> findAllByProvince();

    public List<EchartsMap> findAllByDate();

    public void exportUser(HttpServletRequest request, HttpServletResponse response);
}
