package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.RoleDTO;
import com.mickc0.gtac.entity.Role;
import com.mickc0.gtac.mapper.RoleMapper;
import com.mickc0.gtac.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }


    @Override
    public List<RoleDTO> findAllRoles() {
        return roleRepository.findAll().stream()
                .map(roleMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Rôle non trouvé: " + name));
    }
}
