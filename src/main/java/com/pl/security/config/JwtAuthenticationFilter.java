package com.pl.security.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import com.pl.security.exception.InvalidJwtTokenException;
import com.pl.security.service.IJWTService;
import com.pl.security.service.IUserMasterService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private IJWTService jwtService;
    
    @Autowired
    private PathMatcher matcher;

    @Autowired
    private IUserMasterService userService;
    

    
//    private static final List<String> EXCLUDED_PATHS = Arrays.asList(
//        
//    		"/*/oauth/**",
//            "/*/actuator/**",
//            "/g*/facility/**",
//            "/*/keystore/**"
//        );

    private static final List<String> EXCLUDED_PATHS = Arrays.asList("/**");
    
	@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
    	
    	String requestURI = request.getRequestURI();
    
    	if (isExcludedPath(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;
        
        try {
            if (StringUtils.isEmpty(authHeader) || !org.apache.commons.lang3.StringUtils.startsWith(authHeader, "Bearer ")) {
                filterChain.doFilter(request, response);
             
                try
                {
                authHeader.length();
                }
                catch(Exception e)
                {
                	
                	handleException(response, e, HttpServletResponse.SC_UNAUTHORIZED);
                }

               return;
            }

            jwt = authHeader.split(" ")[1].trim();
            
            username = jwtService.extractUsername(jwt);

            if (StringUtils.isNotEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userService.findByUsername(username);

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    securityContext.setAuthentication(token);
                    SecurityContextHolder.setContext(securityContext);
                } else {
                    throw new InvalidJwtTokenException("JWT token is expired or invalid.");
                }
            }

            filterChain.doFilter(request, response);

        } catch (InvalidJwtTokenException e) {
        
            handleException(response, e, HttpServletResponse.SC_UNAUTHORIZED);
        }
        
    	 catch (AccessDeniedException e) {
       
    	handleException1(response, e, HttpServletResponse.SC_UNAUTHORIZED);
        }
        
        catch (Exception e) {
          
            handleException(response, e, HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    protected void handleException(HttpServletResponse response, Exception e, int statusCode) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        response.getWriter().write("{\"status\": 401,\n" +
                "    \"success\": false,\n" +
                "    \"message\": \"Unauthorized: Invalid JWT Token\" \n" +
                "}");
    }
    
    protected void handleException1(HttpServletResponse response, AccessDeniedException e, int statusCode) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        response.getWriter().write("{\"status\": 401,\n" +
                "    \"success\": false,\n" +
                "    \"message\": \"Unauthorized: Invalid JWT Token\" \n" +
                "}");
    }
    
    protected boolean isExcludedPath(String requestURI) {
        for (String path : EXCLUDED_PATHS) {
            try {
				if (matcher.match(path, requestURI)) {
				    return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        return false;
    }
   
}
