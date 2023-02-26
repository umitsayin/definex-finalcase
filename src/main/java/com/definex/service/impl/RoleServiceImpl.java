package com.definex.service.impl;

import com.definex.constant.GlobalConstant;
import com.definex.exception.EntityNotFoundException;
import com.definex.model.Role;
import com.definex.repository.RoleRepository;
import com.definex.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;

    public RoleServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Role getRoleByName(String name) {
        return repository.findRoleByName(name)
                .orElseThrow(() -> new EntityNotFoundException(GlobalConstant.ROLE_NOT_FOUND));
    }
}
