package com.seguridadtest.usuario.controller;

import com.seguridadtest.otp.dto.OtpVerificationDTO;
import com.seguridadtest.usuario.dto.request.LoginDTO;
import com.seguridadtest.usuario.dto.request.UserDTO;
import com.seguridadtest.usuario.dto.response.LoginResponse;
import com.seguridadtest.usuario.model.User;
import com.seguridadtest.usuario.service.impl.AuthService;
import com.seguridadtest.usuario.service.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserServiceImpl userServiceImpl;
    private final AuthService authService;

    public UserController(UserServiceImpl userServiceImpl, AuthService authService) {
        this.userServiceImpl = userServiceImpl;
        this.authService = authService;
    }

    @PostMapping("/register-user")
    public ResponseEntity<User> registerUser(@RequestBody UserDTO userDTO){
        User user = userServiceImpl.registerUser(userDTO);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/all-user")
    public ResponseEntity<List<User>> allUser() {
        return new ResponseEntity<>(userServiceImpl.allUser(), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        authService.login(loginDTO);
        return ResponseEntity.ok("OTP enviado a tu correo.");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<LoginResponse> verifyOtp(@RequestBody OtpVerificationDTO dto) {
        LoginResponse response = authService.verificarOtp(dto.getEmail(), dto.getOtp());
        return ResponseEntity.ok(response);
    }
}
