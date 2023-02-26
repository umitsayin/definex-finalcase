package com.definex.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.definex.config.SecurityConstant;
import com.definex.service.CustomUserDetailService;
import com.definex.util.helper.JwtHelper;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class CustomAuthorizationFilter extends OncePerRequestFilter {
    private final CustomUserDetailService customUserDetailService;

    public CustomAuthorizationFilter(CustomUserDetailService customUserDetailService) {
        this.customUserDetailService = customUserDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().equals("/api/admin/login")){
            filterChain.doFilter(request, response);
        }else{

            String authorizationToken = getJWTFromRequest(request);

            if(authorizationToken != null && authorizationToken.startsWith(SecurityConstant.AUTHORIZATION_TOKEN_PREFIX)){
                authorizationToken = authorizationToken.substring(SecurityConstant.AUTHORIZATION_TOKEN_PREFIX.length());

                try {
                    final DecodedJWT decodedJWT = JwtHelper.decodeJwtToken(authorizationToken);
                    final String username = decodedJWT.getSubject();
                    final UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
                    final UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails.getUsername(),null,
                                    userDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request,response);
                }catch (JWTVerificationException e){
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.setStatus(401);

                    final Map<String,String> errorData = new HashMap<>();
                    errorData.put("error", e.getMessage());
                    errorData.put("stackTrace", Arrays.toString(e.getStackTrace()));
                    errorData.put("path", request.getPathInfo());
                    errorData.put("time", LocalDateTime.now().toString());

                    new ObjectMapper().writeValue(response.getOutputStream(),errorData);
                }
            }else{
                filterChain.doFilter(request,response);
            }
        }

    }

    private String getJWTFromCookie(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();

        for(Cookie cookie : cookies){
            if(SecurityConstant.COOKIE_TOKEN.equals(cookie.getName())){
                return cookie.getValue();
            }
        }

        return "";
    }

    private String getJWTFromRequest(HttpServletRequest request){
        return request.getHeader(HttpHeaders.AUTHORIZATION);
    }

    private String getJWT(HttpServletRequest request, boolean fromCookie){
        if(fromCookie) return getJWTFromCookie(request);

        return getJWTFromRequest(request);
    }
}
