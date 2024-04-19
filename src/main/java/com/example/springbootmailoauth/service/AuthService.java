package com.example.springbootmailoauth.service;

import com.example.springbootmailoauth.dto.req.auth.EmailCertificationRequestDto;
import com.example.springbootmailoauth.dto.req.auth.IdCheckRequestDto;
import com.example.springbootmailoauth.dto.res.auth.EmailCertificationResponseDto;
import com.example.springbootmailoauth.dto.res.auth.IdCheckResponseDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<? super IdCheckResponseDto> idCheck(IdCheckRequestDto dto);

    ResponseEntity<? super EmailCertificationResponseDto> emailCertification(EmailCertificationRequestDto dto);
}
