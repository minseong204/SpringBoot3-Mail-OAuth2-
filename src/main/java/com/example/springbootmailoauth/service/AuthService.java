package com.example.springbootmailoauth.service;

import com.example.springbootmailoauth.dto.req.auth.CheckCertificationRequestDto;
import com.example.springbootmailoauth.dto.req.auth.EmailCertificationRequestDto;
import com.example.springbootmailoauth.dto.req.auth.IdCheckRequestDto;
import com.example.springbootmailoauth.dto.req.auth.SignUpRequestDto;
import com.example.springbootmailoauth.dto.res.auth.CheckCertificationResponseDto;
import com.example.springbootmailoauth.dto.res.auth.EmailCertificationResponseDto;
import com.example.springbootmailoauth.dto.res.auth.IdCheckResponseDto;
import com.example.springbootmailoauth.dto.res.auth.SignUpResponseDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<? super IdCheckResponseDto> idCheck(IdCheckRequestDto dto);

    ResponseEntity<? super EmailCertificationResponseDto> emailCertification(EmailCertificationRequestDto dto);

    ResponseEntity<? super CheckCertificationResponseDto> checkCertification(CheckCertificationRequestDto dto);

    ResponseEntity<? super SignUpResponseDto> singUp(SignUpRequestDto dto);
}
