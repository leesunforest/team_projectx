package com.projectx.board.repository;

import com.projectx.board.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 사용자 정보를 조회하기 위한 메서드, 회원ID로 찾는다.
    Optional<User> findByUserId(String userId);
}
