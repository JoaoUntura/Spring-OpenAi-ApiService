package dev.joaountura.aihelper.security.filters;

import com.auth0.jwt.interfaces.DecodedJWT;
import dev.joaountura.aihelper.security.services.JWTServices;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTServices jwtServices;

    Logger logger = LoggerFactory.getLogger(JWTFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Cookie cookie = findCookie(request, "auth");
        if (cookie == null) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = cookie.getValue();

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }


        DecodedJWT decodedJWT = jwtServices.verifyAndDecode(token);

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(decodedJWT.getSubject(), null, Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));

        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().equals("/register")
                || request.getServletPath().equals("/login");
    }

    public Cookie findCookie(HttpServletRequest request, String cookieName) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {

            return null;
        }

        Optional<Cookie> cookie = Arrays.stream(cookies)
                .filter(cookie1 -> Objects.equals(cookie1.getName(), cookieName)).findFirst();

        return cookie.orElse(null);

    }


}
