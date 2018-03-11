package com.YassineOnlineBank.dao;

import org.springframework.data.repository.CrudRepository;

import com.YassineOnlineBank.models.Role;

public interface RoleDao extends CrudRepository<Role, Integer> {
    Role findByName(String name);
}
