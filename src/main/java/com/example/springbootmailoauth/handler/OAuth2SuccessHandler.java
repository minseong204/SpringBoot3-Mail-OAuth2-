package com.example.springbootmailoauth.handler;

import com.example.springbootmailoauth.entity.CustomOAuth2User;
import com.example.springbootmailoauth.provider.JwtProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        CustomOAuth2User oAuth2user = (CustomOAuth2User) authentication.getPrincipal();

        String userId = oAuth2user.getName();
        String token = jwtProvider.create(userId);

        response.sendRedirect("http://localhost:8080/auth/oauth2-response/" + token + "/3600");

    }
}
