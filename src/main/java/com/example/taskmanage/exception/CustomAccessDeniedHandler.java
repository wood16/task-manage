package com.example.taskmanage.exception;

import com.example.taskmanage.utils.BaseResponseDto;
import com.example.taskmanage.utils.HelperUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        BaseResponseDto responseDto = new BaseResponseDto();

        responseDto.setMessage("You don't have permission to access this resource");
        responseDto.setCode(String.valueOf(HttpStatus.FORBIDDEN.value()));

        String json = HelperUtils.JSON_WRITER.writeValueAsString(responseDto);

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
