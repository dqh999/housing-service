package com.example.housing_service.presentation.authentication;

import com.example.housing_service.presentation.dataTransferObject.UserDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationApp extends OncePerRequestFilter {
    @Value("${api.prefix}")
    private String apiPrefix;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getHeader("UserId") == null ||
                request.getHeader("UserId").isEmpty() ||
                request.getHeader("Roles") == null ||
                request.getHeader("Roles").isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        Long userId = Long.parseLong(request.getHeader("UserId"));
        String rolesHeader = request.getHeader("Roles");
        rolesHeader = rolesHeader.replace("[", "").replace("]", "");

        List<String> roles = new ArrayList<>();
        if (rolesHeader != null && !rolesHeader.isEmpty()) {
            roles = Arrays.asList(rolesHeader.split(","));
        }
        System.out.println(roles);
        UserDTO userRequest =  UserDTO.builder()
                .build();
        userRequest.setUserId(userId);
//        userRequest.setUserName(String.valueOf(userId));
        userRequest.setRoles(roles);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userRequest,
                null,
                userRequest.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info(String.format("User Id %s auth successfully %s",userRequest.getUserId(),userRequest));
        filterChain.doFilter(request, response);

    }

}
