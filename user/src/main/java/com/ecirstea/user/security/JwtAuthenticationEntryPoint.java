package com.ecirstea.user.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
    private static final long serialVersionUID = -7858869558953243875L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object > rsp = new LinkedHashMap<>() {
            {
                put("timestamp", new Date().getTime());
                put("status", HttpStatus.UNAUTHORIZED.value());
                put("error", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            }
        };
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(rsp));
        response.getWriter().flush();
        response.getWriter().close();
        // response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}