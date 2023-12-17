package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.RoleDTO;
import com.mickc0.gtac.entity.Role;

import java.util.List;

public interface RoleService {

    List<RoleDTO> findAllRoles();

    Role findByName(String name);
}
