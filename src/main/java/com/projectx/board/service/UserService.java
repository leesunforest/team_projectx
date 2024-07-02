package com.projectx.board.service;
/*
임의로 작성된 UserService
 */

import com.projectx.board.entity.User;
import com.projectx.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    
    // 사용자 생성 메서드
    public User createUser(String userId, String userPw, String userEmail) {
        User user = User.builder()
                .userId(userId)
                .userPw(userPw)
                .userEmail(userEmail)
                .build();
        // 생성된 사용자 정보를 DB에 저장
        return userRepository.save(user); 
    }

    // 사용자 아이디로 사용자 조회 메서드
    public User findUserByUserId(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
