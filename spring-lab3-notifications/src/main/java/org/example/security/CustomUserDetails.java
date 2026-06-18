package org.example.security;

import lombok.AllArgsConstructor;
import org.example.model.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor  // Lombok создаст конструктор со всеми полями
public class CustomUserDetails implements UserDetails {
    
    private final User user;  // Наша сущность User
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Превращаем роль пользователя (например "ROLE_USER") в объект, понятный Spring Security
        return List.of(new SimpleGrantedAuthority(user.getRole().name()));
    }
    
    @Override
    public String getPassword() {
        return user.getPassword();  // Зашифрованный пароль из БД
    }
    
    @Override
    public String getUsername() {
        return user.getEmail();  // Используем email как логин
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;  // Аккаунт не просрочен
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true;  // Аккаунт не заблокирован
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Пароль не просрочен
    }
    
    @Override
    public boolean isEnabled() {
        return true;  // Аккаунт активен
    }
    
    // Дополнительный метод для получения исходного пользователя (если понадобится)
    public User getUser() {
        return user;
    }
}