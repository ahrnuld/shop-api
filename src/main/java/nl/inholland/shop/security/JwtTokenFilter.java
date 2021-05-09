package nl.inholland.shop.security;

// based on https://github.com/murraco/spring-boot-jwt/blob/master/src/main/java/murraco/security/JwtTokenFilter.java

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

@Component
// We should use OncePerRequestFilter since we are doing a database call, there is no point in doing this more than once
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {

        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        // retrieve the token from the request
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        try {
            // check if the token is valid
            if (token != null && jwtTokenProvider.validateToken(token)) {
                // retrieve the user from the database
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                // apply the user to the security context of the current request
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (ResponseStatusException ex) {
            // if the token is invalid or anything else goes wrong, clear the security context of the request
            // this is very important, since it guarantees the user is not authenticated at all
            SecurityContextHolder.clearContext();
            // return an error
            httpServletResponse.sendError(ex.getRawStatusCode(), ex.getMessage());
            return;
        }

        // move on to the next filter
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}