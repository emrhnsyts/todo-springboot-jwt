package com.emrhnsyts.todo.controller;

import com.emrhnsyts.todo.entity.AppUser;
import com.emrhnsyts.todo.request.AppUserRequest;
import com.emrhnsyts.todo.response.AppUserResponse;
import com.emrhnsyts.todo.service.AppUserService;
import com.emrhnsyts.todo.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AppUserService appUserService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;


    @PostMapping("/register")
    public AppUserResponse register(@Valid @RequestBody AppUserRequest appUserRequest) {
        return appUserService.register(appUserRequest);
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody AppUserRequest appUserRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    appUserRequest.getUsername(), appUserRequest.getPassword()
            ));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password");
        }

        final AppUser appUser = appUserService.loadUserByUsername(appUserRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(appUser);
        return jwt;
    }
}
