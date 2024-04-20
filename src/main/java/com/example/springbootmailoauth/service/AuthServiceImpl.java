package com.example.springbootmailoauth.service;

import com.example.springbootmailoauth.common.CertificationNumber;
import com.example.springbootmailoauth.dto.req.auth.CheckCertificationRequestDto;
import com.example.springbootmailoauth.dto.req.auth.EmailCertificationRequestDto;
import com.example.springbootmailoauth.dto.req.auth.IdCheckRequestDto;
import com.example.springbootmailoauth.dto.req.auth.SignUpRequestDto;
import com.example.springbootmailoauth.dto.res.ResponseDto;
import com.example.springbootmailoauth.dto.res.auth.CheckCertificationResponseDto;
import com.example.springbootmailoauth.dto.res.auth.EmailCertificationResponseDto;
import com.example.springbootmailoauth.dto.res.auth.IdCheckResponseDto;
import com.example.springbootmailoauth.dto.res.auth.SignUpResponseDto;
import com.example.springbootmailoauth.entity.CertificationEntity;
import com.example.springbootmailoauth.entity.UserEntity;
import com.example.springbootmailoauth.provider.EmailProvider;
import com.example.springbootmailoauth.repository.CertificationRepository;
import com.example.springbootmailoauth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;


    private final EmailProvider emailProvider;

    private final CertificationRepository certificationRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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

    @Override
    public ResponseEntity<? super SignUpResponseDto> singUp(SignUpRequestDto dto) {

        try {

            String userId = dto.getId();
            boolean isExistId = userRepository.existsByUserId(userId);

            if (isExistId) return SignUpResponseDto.duplicated();

            // TODO: password에 계속 공백이 들어감 해결해야함
            // NOTE: postman에서는 json Key 값 오타였음
            String email = dto.getEmail();
            String certificationNumber = dto.getCertificationNumber();
            CertificationEntity certificationEntity = certificationRepository.findByUserId(userId);
            boolean isMatched = certificationEntity.getEmail().equals(email) && certificationEntity.getCertificationNumber().equals(certificationNumber);
            if (!isMatched) return SignUpResponseDto.certificationFail();

            String password = dto.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            dto.setPassword(encodedPassword);
            log.info("password={}", dto.getPassword());

            UserEntity userEntity = new UserEntity(dto);
            userRepository.save(userEntity);

//            certificationRepository.delete(certificationEntity);
            certificationRepository.deleteByUserId(userId);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return SignUpResponseDto.success();

    }
}
