package com.projectx.board.repository;

import com.projectx.board.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUserId(String userId);
    UserEntity findByUserIdAndUserPw(String userId, String userPw);
}
