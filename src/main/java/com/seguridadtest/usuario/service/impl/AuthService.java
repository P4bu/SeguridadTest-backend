package com.seguridadtest.usuario.service.impl;

import com.seguridadtest.otp.model.OtpToken;
import com.seguridadtest.otp.repository.OtpTokenRepository;
import com.seguridadtest.otp.service.EmailService;
import com.seguridadtest.security.jwt.JwtTokenProvider;
import com.seguridadtest.security.model.UserDetailsImpl;
import com.seguridadtest.security.service.UserDetailsServiceImpl;
import com.seguridadtest.usuario.dto.request.LoginDTO;
import com.seguridadtest.usuario.dto.response.LoginResponse;
import com.seguridadtest.usuario.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsServiceImpl userDetailsService;
    private final OtpTokenRepository otpTokenRepository;
    private final EmailService emailService;
    private final UserRepository userRepository;

    public AuthService(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserDetailsServiceImpl userDetailsService, OtpTokenRepository otpTokenRepository, EmailService emailService, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.otpTokenRepository = otpTokenRepository;
        this.emailService = emailService;
        this.userRepository = userRepository;
    }

    @Transactional
    public void login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
        );

        String otp = String.format("%06d", new Random().nextInt(999999));
        LocalDateTime expiration = LocalDateTime.now().plusMinutes(5);

        otpTokenRepository.deleteByEmail(loginDTO.getEmail());

        OtpToken token = new OtpToken();
        token.setEmail(loginDTO.getEmail());
        token.setOtp(otp);
        token.setExpiration(expiration);
        otpTokenRepository.save(token);

        emailService.sendOtpEmail(loginDTO.getEmail(), otp);


    }

    public LoginResponse verificarOtp(String email, String otp) {

        System.out.println("==> Verificando OTP para email: " + email + " con OTP: " + otp);

        OtpToken token = otpTokenRepository.findByEmailAndOtp(email, otp)
                .orElseThrow(() -> new RuntimeException("OTP inválido"));

        if (token.getExpiration().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expirado");
        }


        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(email);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        String jwt = jwtTokenProvider.generarToken(authentication);

        Set<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toSet());

        String nameComplete = userDetails.getUsername() + " " + userDetails.getLastname();

        otpTokenRepository.deleteByEmail(email);

        return new LoginResponse(
                jwt,
                nameComplete,
                "Bearer",
                userDetails.getEmail(),
                roles
        );
    }
}
