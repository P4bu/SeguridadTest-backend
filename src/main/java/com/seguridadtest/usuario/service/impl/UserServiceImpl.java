package com.seguridadtest.usuario.service.impl;

import com.seguridadtest.usuario.dto.request.UserDTO;
import com.seguridadtest.usuario.model.Erole;
import com.seguridadtest.usuario.model.Role;
import com.seguridadtest.usuario.model.User;
import com.seguridadtest.usuario.repository.RoleRepository;
import com.seguridadtest.usuario.repository.UserRepository;

import com.seguridadtest.usuario.service.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.seguridadtest.sanitizador.SanitizationUtil;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(UserDTO userDTO) {
        if(userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("EL CORREO YA EXISTE EN LOS REGISTROS");
        }

        Role roleUser = roleRepository.findByName(Erole.USER)
        .orElseThrow(() -> new IllegalArgumentException("ROL no existe en la base de datos"));


        User user = new User();

        //Utilizo el sanitizador
        user.setName(SanitizationUtil.sanitize(userDTO.getLastname()));
        user.setLastName(SanitizationUtil.sanitize(userDTO.getLastname()));
        user.setEmail(userDTO.getEmail());

        if(userDTO.getPassword() == null || userDTO.getPassword().isBlank()) {
            throw new IllegalArgumentException("LA CONTRASEÑA NO PUEDE ESTAR VACIA");
        }

        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        user.getRoles().add(roleUser);

        return userRepository.save(user);
    }

    @Override
    public List<User> allUser() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        if(email == null || email.isEmpty()){
            throw new IllegalArgumentException("Email no puede estar vacio ");
        }
        return userRepository.findByEmail(email);
    }

}
