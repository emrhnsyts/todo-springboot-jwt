package com.emrhnsyts.todo.service;

import com.emrhnsyts.todo.entity.AppUser;
import com.emrhnsyts.todo.repository.AppUserRepository;
import com.emrhnsyts.todo.request.AppUserRequest;
import com.emrhnsyts.todo.response.AppUserResponse;
import com.emrhnsyts.todo.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AppUser loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> optionalAppUser = appUserRepository.findByUsername(username);
        if (optionalAppUser.isPresent()) {
            return optionalAppUser.get();
        }
        throw new UsernameNotFoundException("User not found.");
    }

    public AppUserResponse register(AppUserRequest appUserRequest) {
        AppUser appUser = AppUser.builder()
                .password(passwordEncoder.encode(appUserRequest.getPassword()))
                .username(appUserRequest.getUsername())
                .build();

        return new AppUserResponse(appUserRepository.save(appUser));
    }

    public AppUser getAppUser(Long appUserId){
        Optional<AppUser> optionalAppUser = appUserRepository.findById(appUserId);
        if (optionalAppUser.isPresent()){
            return optionalAppUser.get();
        }
        throw new UsernameNotFoundException("User not found");
    }


}
