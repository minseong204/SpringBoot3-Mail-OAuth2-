package com.example.springbootmailoauth.repository;

import com.example.springbootmailoauth.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    UserEntity findByUserId(String userId);

    boolean existsByUserId(String userId);


}
