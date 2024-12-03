package com.nirmalks.bookstore.filter;

import com.nirmalks.bookstore.constants.AppConstants;
import com.nirmalks.bookstore.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtTokenValidatorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = request.getHeader(AppConstants.JWT_HEADER);
        logger.debug("JWT token " + token);
        if(token != null) {
            logger.debug("No JWT token found in request headers.");
            if(JwtUtils.validateToken(token)) {
                var username = JwtUtils.extractUsername(token);
                var authorities = JwtUtils.extractAuthorities(token);
                var authentication = new UsernamePasswordAuthenticationToken(username, null,
                        AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                throw new BadCredentialsException("User not found");
            }
        } else {
            throw new BadCredentialsException("Token not found");
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return List.of("/api/login", "/api/register").contains(request.getServletPath());
    }
}
