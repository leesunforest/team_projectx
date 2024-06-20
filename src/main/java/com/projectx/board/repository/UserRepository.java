package com.projectx.board.repository;

import com.projectx.board.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 사용자 이메일로 사용자 찾기
    Optional<User> findByEmail(String email);
}
