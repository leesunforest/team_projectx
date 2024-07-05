package com.projectx.board.controller;

import com.projectx.board.dto.UserLoginDTO;
import com.projectx.board.dto.UserSignupDTO;
import com.projectx.board.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody UserSignupDTO requestDTO) {
        Map<String, String> response = userService.signup(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@ModelAttribute UserLoginDTO requestDTO, HttpServletRequest request) {
        Map<String, String> response = userService.login(requestDTO, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request) {
        Map<String, String> response = userService.logout(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
