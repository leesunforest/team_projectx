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

    public Map<String, String> signup(UserSignupDTO requestDTO) {
        // 아이디가 중복인지 체크
        UserEntity findUser = userRepository.findByUserId(requestDTO.getUserId());

        // 아이디가 중복이라면
        if (findUser != null) {
            throw new IllegalArgumentException("중복된 아이디가 존재합니다.");
        }
        // 정상적인 회원가입이라면 디비에 저장
        UserEntity newUser = requestDTO.toUserEntity();
        userRepository.save(newUser);

        return Collections.singletonMap("message", "회원가입 성공");
    }

    public Map<String, String> login(UserLoginDTO requestDTO, HttpServletRequest httpRequest) {
        UserEntity loginUser = userRepository.findByUserIdAndUserPw(requestDTO.getUserId(), requestDTO.getUserPw());

        if (loginUser == null) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        httpRequest.getSession().setAttribute("user", loginUser);

        return Collections.singletonMap("message", "로그인 성공");
    }

    public Map<String, String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return Collections.singletonMap("message", "로그아웃 성공");
    }
}
