package com.course.cars.api.security.jwt.handler;

import com.course.cars.api.security.jwt.ServletUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UnauthorizedHandler implements AuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(UnauthorizedHandler.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        logger.warn("UnauthorizedHandler, exception: " + authException);

        // Call if token is error ou away
        String json = ServletUtil.getJson("error", "Not authorized.");
        ServletUtil.write(response, HttpStatus.FORBIDDEN, json);
    }
}