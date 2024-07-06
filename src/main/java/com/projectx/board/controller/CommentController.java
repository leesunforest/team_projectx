package com.projectx.board.controller;

import com.projectx.board.dto.BoardItemDTO;
import com.projectx.board.dto.CommentDTO;
import com.projectx.board.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    @PostMapping("/save")
    public ResponseEntity save(@ModelAttribute CommentDTO commentDTO, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            String userId = (String) session.getAttribute("userId");
            Long saveResult = commentService.save(commentDTO, userId);
            if (saveResult != null) {
                List<CommentDTO> commentDTOList = commentService.findAll(commentDTO.getBoardId());
                return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("해당 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
            }
        }
        else{
            return new ResponseEntity<>("인증된 유저가 아닙니다", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/userComment")
    public ResponseEntity findUserComment(HttpServletRequest request){
        HttpSession session = request.getSession(false);

        if (session != null) {
            String userId = (String) session.getAttribute("userId");
            List<CommentDTO> userCommentList = commentService.findUserComment(userId);

            return new ResponseEntity<>(userCommentList, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("인증된 유저가 아닙니다", HttpStatus.BAD_REQUEST);
        }
    }

}