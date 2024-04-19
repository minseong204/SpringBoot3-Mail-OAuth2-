package com.example.springbootmailoauth.service;

import com.example.springbootmailoauth.common.CertificationNumber;
import com.example.springbootmailoauth.dto.req.auth.CheckCertificationRequestDto;
import com.example.springbootmailoauth.dto.req.auth.EmailCertificationRequestDto;
import com.example.springbootmailoauth.dto.req.auth.IdCheckRequestDto;
import com.example.springbootmailoauth.dto.res.ResponseDto;
import com.example.springbootmailoauth.dto.res.auth.CheckCertificationResponseDto;
import com.example.springbootmailoauth.dto.res.auth.EmailCertificationResponseDto;
import com.example.springbootmailoauth.dto.res.auth.IdCheckResponseDto;
import com.example.springbootmailoauth.entity.CertificationEntity;
import com.example.springbootmailoauth.provider.EmailProvider;
import com.example.springbootmailoauth.repository.CertificationRepository;
import com.example.springbootmailoauth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;


    private final EmailProvider emailProvider;

    private final CertificationRepository certificationRepository;

    @Override
    public ResponseEntity<? super IdCheckResponseDto> idCheck(IdCheckRequestDto dto) {
        try {
            String userId = dto.getId();
            boolean isExistId = userRepository.existsByUserId(userId);
            if (isExistId) return IdCheckResponseDto.duplicateId();
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return IdCheckResponseDto.success();
    }

    @Override
    public ResponseEntity<? super EmailCertificationResponseDto> emailCertification(EmailCertificationRequestDto dto) {

        try {
            String userId = dto.getId();
            String email = dto.getEmail();

            boolean isExistId = userRepository.existsByUserId(userId);
            if (isExistId) return EmailCertificationResponseDto.duplicateId();


            String certificationNumber = CertificationNumber.getCertificationNumber();

            boolean isSucceed = emailProvider.sendCertificationMail(email, certificationNumber);
            if (!isSucceed) return EmailCertificationResponseDto.mailSendFail();

            CertificationEntity certificationEntity = new CertificationEntity(userId, email, certificationNumber);
            certificationRepository.save(certificationEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return EmailCertificationResponseDto.success();
    }

    @Override
    public ResponseEntity<? super CheckCertificationResponseDto> checkCertification(CheckCertificationRequestDto dto) {

        try {

            String userId = dto.getId();
            String email = dto.getEmail();
            String certificationNumber = dto.getCertificationNumber();

            CertificationEntity certificationEntity = certificationRepository.findByUserId(userId);

            if (certificationEntity == null) return CheckCertificationResponseDto.certificationFail();

            boolean isMatched = certificationEntity.getEmail().equals(email) && certificationEntity.getCertificationNumber().equals(certificationNumber);

            if (!isMatched) return CheckCertificationResponseDto.certificationFail();

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return CheckCertificationResponseDto.success();
    }
}
