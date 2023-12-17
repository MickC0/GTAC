package com.mickc0.gtac.service;

import com.mickc0.gtac.dto.RoleDTO;
import com.mickc0.gtac.entity.Role;
import com.mickc0.gtac.mapper.RoleMapper;
import com.mickc0.gtac.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class RoleServiceImplTest {

    private RoleServiceImpl roleService;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private RoleMapper roleMapper;

    @BeforeEach
    void initService() {
        roleService = new RoleServiceImpl(roleRepository,roleMapper);
    }

    @Test
    void findAllRoles() {
        List<Role> roles = Arrays.asList(new Role(), new Role());
        when(roleRepository.findAll()).thenReturn(roles);
        when(roleMapper.mapToDto(any(Role.class))).thenAnswer(invocation -> {
            Role role = invocation.getArgument(0);
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setName(role.getName());
            return roleDTO;
        });

        List<RoleDTO> result = roleService.findAllRoles();

        assertEquals(roles.size(), result.size());
        verify(roleRepository).findAll();
        verify(roleMapper, times(roles.size())).mapToDto(any(Role.class));
    }
    @Test
    void findByName_RoleExists() {
        String roleName = "admin";
        Role role = new Role();
        role.setName(roleName);

        when(roleRepository.findByName(roleName)).thenReturn(Optional.of(role));

        Role result = roleService.findByName(roleName);

        assertNotNull(result);
        assertEquals(roleName, result.getName());
        verify(roleRepository).findByName(roleName);
    }

    @Test
    void findByName_RoleDoesNotExist_ThrowsEntityNotFoundException() {
        String roleName = "unknown";
        when(roleRepository.findByName(roleName)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> roleService.findByName(roleName));
        verify(roleRepository).findByName(roleName);
    }


}
