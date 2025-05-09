package com.seguridadtest.usuario.service.service;

import com.seguridadtest.usuario.model.Erole;
import com.seguridadtest.usuario.model.Role;

import java.util.Optional;


public interface RoleService {
    Optional<Role> findByName(Erole name);
}
