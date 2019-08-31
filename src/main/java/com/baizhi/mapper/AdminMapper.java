package com.baizhi.mapper;

import com.baizhi.entity.Admin;
import com.baizhi.entity.Authority;
import com.baizhi.entity.Role;

import java.util.List;
import java.util.Set;

public interface AdminMapper {
    public Admin login(String username);

    public List<Role> findRoleByAdmin(Admin admin);

    public Set<Authority> findAuthorityByRole(Role role);
}
