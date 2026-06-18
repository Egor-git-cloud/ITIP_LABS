package org.example.security;

import lombok.RequiredArgsConstructor;
import org.example.model.entity.User;
import org.example.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        org.example.model.entity.User user = userRepository.findByEmail(username.trim())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));


        String roleName = user.getRole() != null ? user.getRole().toString() : "ROLE_USER";


        UserDetails springUser = org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(roleName)
                .build();



        return springUser;
    }
}