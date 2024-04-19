package com.example.springbootmailoauth.repository;

import com.example.springbootmailoauth.entity.CertificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificationRepository extends JpaRepository<CertificationEntity, String> {

    CertificationEntity findByUserId(String userId);

}
