package com.example.sharefekhair.advice;

import com.example.sharefekhair.DTO.ResponseAPI;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    Logger logger = LoggerFactory.getLogger(MyAuthenticationEntryPoint.class);

    private ObjectMapper objectMapper = new ObjectMapper();



    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        // 401

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Map<String, Object> data = new HashMap<>();
        data.put(
                "timestamp",
                Calendar.getInstance().getTime());
        data.put(
                "exception",
                authException.getMessage());

        response.getOutputStream()
                .println(objectMapper.writeValueAsString(data));

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed");

        logger.error("\nHttpServletResponse.SC_UNAUTHORIZED, Authentication Failed" +
                "\n "+data);
//        return ResponseEntity.status(401).body(new ResponseAPI<>(objectMapper.writeValueAsString(data),401));
    }

    @ExceptionHandler (value = {AccessDeniedException.class})
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AccessDeniedException accessDeniedException) throws IOException {
        // 403
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Authorization Failed : " + accessDeniedException.getMessage());
    }

    @ExceptionHandler(value = {Exception.class})
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         Exception exception) throws IOException {
        // 500
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error : " + exception.getMessage());
    }
}
