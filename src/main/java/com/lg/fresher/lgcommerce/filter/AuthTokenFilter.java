package com.lg.fresher.lgcommerce.filter;

import com.lg.fresher.lgcommerce.config.security.UserDetailsImpl;
import com.lg.fresher.lgcommerce.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Order(1)
@Component
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {

    private static final Logger authLogger = LoggerFactory.getLogger(AuthTokenFilter.class);
    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = jwtUtils.parseJwt(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                Claims claims = jwtUtils.getPayloadFromJwtToken(jwt);
                UserDetails userDetails = createUserDetails(claims);
                setAuthentication(userDetails, request);
            }
        } catch (Exception e) {
            authLogger.error("Cannot set user authentication: {}", e.getMessage());
            SecurityContextHolder.clearContext();
            throw e;
        }
        filterChain.doFilter(request, response);
    }

    private UserDetails createUserDetails(Claims claims) {
        String userId = (String) claims.get("userId");
        String userName = (String) claims.get("userName");
        String email = (String) claims.get("email");
        String role = (String) claims.get("role");
        String status = (String) claims.get("status");
        List<GrantedAuthority> roles = List.of(new SimpleGrantedAuthority(role));
        return new UserDetailsImpl(userId, userName, email, status, roles);
    }

    private void setAuthentication(UserDetails userDetails, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
