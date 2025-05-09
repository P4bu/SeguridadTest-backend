package com.seguridadtest.security.service;

import com.seguridadtest.security.model.UserDetailsImpl;
import com.seguridadtest.usuario.model.User;
import com.seguridadtest.usuario.service.impl.UserServiceImpl;
import com.seguridadtest.usuario.service.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    public UserDetailsServiceImpl(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));
        user.getRoles().size();
        return new UserDetailsImpl(user);
    }
}
