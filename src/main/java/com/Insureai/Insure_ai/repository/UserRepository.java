package com.Insureai.Insure_ai.repository;

import com.Insureai.Insure_ai.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {

    Boolean existsByUsername(String username);
    UserEntity findByUsername(String username);
}
