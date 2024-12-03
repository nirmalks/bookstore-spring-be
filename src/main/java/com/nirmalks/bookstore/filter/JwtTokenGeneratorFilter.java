package com.nirmalks.bookstore.filter;

import com.nirmalks.bookstore.constants.AppConstants;
import com.nirmalks.bookstore.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Order()
public class JwtTokenGeneratorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("inside token gen filter");
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null) {
            var env = getEnvironment();
            if(env != null) {
                var token = JwtUtils.generateToken(authentication.getName(), authentication.getAuthorities());
                response.setHeader(AppConstants.JWT_HEADER, token);
            }

        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/api/login");
    }
}
