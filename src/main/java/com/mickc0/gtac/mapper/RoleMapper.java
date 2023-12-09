package com.mickc0.gtac.mapper;

import com.mickc0.gtac.dto.RoleDTO;
import com.mickc0.gtac.entity.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public RoleDTO mapToDto(Role role){
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName(role.getName());
        return roleDTO;
    }
}
