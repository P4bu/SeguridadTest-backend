package com.seguridadtest.usuario.service.service;

import com.seguridadtest.usuario.dto.request.UserDTO;
import com.seguridadtest.usuario.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User registerUser(UserDTO userDTO);
    Optional<User> findByEmail(String email);
    List<User> allUser();
}
