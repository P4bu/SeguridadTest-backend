package com.seguridadtest.usuario.service.impl;

import com.seguridadtest.usuario.model.Erole;
import com.seguridadtest.usuario.model.Role;
import com.seguridadtest.usuario.repository.RoleRepository;
import com.seguridadtest.usuario.service.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<Role> findByName(Erole name) {
        return roleRepository.findByName(name);
    }


}
