package com.upc.biblioteca.config;

import com.upc.biblioteca.dto.Header;
import com.upc.biblioteca.negocio.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Permitir pasar login sin validar token
        String path = request.getRequestURI();
        if (path.equals("/api/login")) {
            filterChain.doFilter(request, response);
            return;
        }


        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7);

            try {
                // validar el token (lanza excepción si es inválido)
                jwtUtil.validateToken(token);

                // EXTRAER USERNAME DEL TOKEN
                String username = jwtUtil.getCorreoFromToken(token);

                // AUTENTICAR EN EL CONTEXTO
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write(
                        "{\"resultado\":false, \"mensaje\":\"Token inválido o expirado\"}"
                );
                return;
            }
        } else {
            // No envió token → bloquear
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            response.setContentType("application/json");
            response.getWriter().write(
                    "{\"resultado\":false, \"mensaje\":\"Token requerido\"}"
            );
            return;
        }

        filterChain.doFilter(request, response);
    }
}
