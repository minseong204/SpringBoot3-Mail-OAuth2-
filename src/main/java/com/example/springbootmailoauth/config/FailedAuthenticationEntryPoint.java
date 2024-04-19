package com.example.springbootmailoauth.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class FailedAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        // {"code" : "NP", "message": "No Permission"}
        // 위 를 복사해서 붙여넣기 하면 아래처럼 붙여넣기 해줌 신기함..
        response.getWriter().write("{{\"code\" : \"NP\", \"message\": \"No Permission\"}}");
    }
}
