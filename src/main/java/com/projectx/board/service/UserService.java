package com.projectx.board.service;

import com.projectx.board.dto.UserLoginDTO;
import com.projectx.board.dto.UserSignupDTO;
import com.projectx.board.entity.UserEntity;
import com.projectx.board.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public void signup(UserSignupDTO requestDTO) {
        // 아이디가 중복인지 체크
        UserEntity findUser = userRepository.findByUserId(requestDTO.getUserId());

        // 아이디가 중복이라면
        if (findUser != null) {
            throw new IllegalArgumentException("중복된 아이디가 존재합니다.");
        }
        // 정상적인 회원가입이라면 디비에 저장
        UserEntity newUser = new UserEntity(requestDTO.getUserId(), requestDTO.getUserPw(), requestDTO.getUserEmail(), LocalDateTime.now());
        userRepository.save(newUser);
    }

    public String login(UserLoginDTO requestDTO, HttpServletRequest httpRequest) {
        UserEntity loginUser = userRepository.findByUserIdAndUserPw(requestDTO.getUserId(), requestDTO.getUserPw());
        if (loginUser == null) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }
        HttpSession session = httpRequest.getSession();
        session.setAttribute("userId", loginUser.getUserId());

        return loginUser.getUserId();
    }
    public UserEntity findUserByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }
}

