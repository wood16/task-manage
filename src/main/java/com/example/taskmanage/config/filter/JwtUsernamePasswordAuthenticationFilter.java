package com.example.taskmanage.config.filter;

import com.example.taskmanage.dto.request.LoginRequest;
import com.example.taskmanage.jwt.JwtConfig;
import com.example.taskmanage.jwt.JwtService;
import com.example.taskmanage.service.RefreshTokenService;
import com.example.taskmanage.service.security.UserDetailsCustom;
import com.example.taskmanage.dto.response.BaseResponse;
import com.example.taskmanage.utils.HelperUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final JwtService jwtService;

    private final ObjectMapper objectMapper;

    private final RefreshTokenService refreshTokenService;

    public JwtUsernamePasswordAuthenticationFilter(AuthenticationManager manager,
                                                   JwtConfig jwtConfig,
                                                   JwtService jwtService,
                                                   RefreshTokenService refreshTokenService) {

        super(new AntPathRequestMatcher(jwtConfig.getUrl(), "POST"));
        setAuthenticationManager(manager);
        this.objectMapper = new ObjectMapper();
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        log.info("Start attempt to authentication");

        LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);

        log.info("End attempt to authentication");

        return getAuthenticationManager()
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword(),
                        Collections.emptyList()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        UserDetailsCustom userDetailsCustom = (UserDetailsCustom) authResult.getPrincipal();

        String accessToken = jwtService.generateToken(userDetailsCustom);
        String refreshToken = refreshTokenService.createRefreshToken(userDetailsCustom.getUsername());

        Map<String, String> responseObject = new HashMap<>();

        responseObject.put("token", accessToken);
        responseObject.put("refresh_token", refreshToken);
        responseObject.put("message", "Login success!");

        String result = HelperUtils.JSON_WRITER.writeValueAsString(responseObject);

        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(result);
        log.info("End success authentication: {}", accessToken);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {

        BaseResponse responseDto = new BaseResponse();

        responseDto.setCode(HttpStatus.UNAUTHORIZED.value());
        responseDto.setMessage(failed.getLocalizedMessage());

        String json = HelperUtils.JSON_WRITER.writeValueAsString(responseDto);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(json);
    }
}
