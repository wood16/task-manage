package com.example.taskmanage.config.filter;

import com.example.taskmanage.dto.response.UserContextResponse;
import com.example.taskmanage.exception.BaseException;
import com.example.taskmanage.exception.ErrorCode;
import com.example.taskmanage.jwt.JwtConfig;
import com.example.taskmanage.jwt.JwtService;
import com.example.taskmanage.dto.response.BaseResponse;
import com.example.taskmanage.utils.HelperUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private final JwtConfig jwtConfig;

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String accessToken = request.getHeader(jwtConfig.getHeader());

        log.info("Start do filter once per request, {}", request.getRequestURI());

        if (Objects.nonNull(accessToken) &&
                accessToken.startsWith(jwtConfig.getPrefix() + " ")) {

            accessToken = accessToken.substring((jwtConfig.getPrefix() + " ").length());

            try {

                if (jwtService.isValidToken(accessToken)) {
                    Claims claims = jwtService.extractClaims(accessToken);

                    Long userId = claims.get("userId", Long.class);

                    String username = claims.getSubject();

                    List<String> authorities = claims.get("authorities", List.class);

                    if (Objects.nonNull(userId)) {
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(
                                        new UserContextResponse(userId, username),
                                        null,
                                        authorities
                                                .stream()
                                                .map(SimpleGrantedAuthority::new)
                                                .toList());

                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            } catch (BaseException e) {
                log.error("Error on filter once per request, path {}, error{}", request.getRequestURI(), e.getMessage());

//                get ErrorCode throw
                ErrorCode errorCode = e.getErrorCode();

                BaseResponse responseDto = new BaseResponse();
                responseDto.setCode(errorCode.getCode());
                responseDto.setMessage(errorCode.getMessage());

                String json = HelperUtils.JSON_WRITER.writeValueAsString(responseDto);

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json; charset=UTF-8");
                response.getWriter().write(json);

                return;
            }
        }

        log.info("End do filter; {}", request.getRequestURI());

        filterChain.doFilter(request, response);
    }
}
