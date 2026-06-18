package org.example.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        
        String path = request.getRequestURI();
        
        // Пропускаем запросы на /auth/** (регистрация, логин) без проверки
        if (path.startsWith("/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        String authHeader = request.getHeader("Authorization");
        
        // Если нет заголовка Authorization - запрос не авторизован
        if (authHeader == null || authHeader.isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Требуется авторизация");
            return;
        }
        
        // Если это Bearer токен - проверяем
        if (authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String username = jwtService.extractUsername(token);
            
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                
                // ПРОВЕРКА СРОКА ДЕЙСТВИЯ ТОКЕНА
                if (jwtService.isTokenValid(token)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Токен истек");
                    return;
                }
            }
            filterChain.doFilter(request, response);
            return;
        }
        
        // Если это не Bearer токен (например Basic Auth) - пропускаем
        filterChain.doFilter(request, response);
    }
}