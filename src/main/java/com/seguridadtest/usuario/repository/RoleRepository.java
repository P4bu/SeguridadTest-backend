package com.seguridadtest.usuario.repository;

import com.seguridadtest.usuario.model.Erole;
import com.seguridadtest.usuario.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
   Optional<Role> findByName(Erole name);
}
