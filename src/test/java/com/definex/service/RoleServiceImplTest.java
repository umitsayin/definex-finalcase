package com.definex.service;

import com.definex.exception.EntityNotFoundException;
import com.definex.model.Role;
import com.definex.repository.RoleRepository;
import com.definex.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RoleServiceImplTest {
    private RoleRepository repository;
    private RoleService roleService;

    @BeforeEach
    public void setUp(){
        repository = mock(RoleRepository.class);
        roleService = new RoleServiceImpl(repository);
    }

    @Test
    void testGetRoleByName_WithRoleName_thenReturnRole(){
        Role role = new Role("ADMIN");

        when(repository.findRoleByName("ADMIN")).thenReturn(Optional.of(role));

        Role result = roleService.getRoleByName("ADMIN");

        assertEquals(role,result);
    }

    @Test
    void testGetRoleByName_WithRoleName_thenThrowEntityNotFoundException(){
        when(repository.findRoleByName("ADMIN")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> roleService.getRoleByName("ADMIN"));
    }

}