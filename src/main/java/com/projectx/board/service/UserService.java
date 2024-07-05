package com.projectx.board.service;

import com.projectx.board.dto.UserLoginDTO;
import com.projectx.board.dto.UserSignupDTO;
import com.projectx.board.entity.UserEntity;
import com.projectx.board.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public void signup(UserSignupDTO requestDTO) {
        UserEntity findUser = userRepository.findByUserId(requestDTO.getUserId());
        if (findUser != null) {
            throw new IllegalArgumentException("중복된 아이디가 존재합니다.");
        }
        UserEntity newUser = requestDTO.toUserEntity();
        userRepository.save(newUser);
    }

    public void login(UserLoginDTO requestDTO, HttpServletRequest httpRequest) {
        UserEntity loginUser = userRepository.findByUserIdAndUserPw(requestDTO.getUserId(), requestDTO.getUserPw());
        if (loginUser == null) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }
        HttpSession session = httpRequest.getSession();
        session.setAttribute("userId", loginUser.getUserId());
    }

    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
