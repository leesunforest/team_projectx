package com.projectx.board.controller;

import com.projectx.board.dto.BoardDTO;
import com.projectx.board.dto.BoardItemDTO;
import com.projectx.board.dto.CommentDTO;
import com.projectx.board.entity.UserEntity;
import com.projectx.board.service.BoardService;
import com.projectx.board.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("/save")
    public String saveForm() {
        return "boardSave";
    }

    //게시판->글작성->저장시 호출
    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO, HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession(false);

        if (session != null) {
            String userId = (String) session.getAttribute("userId");
            System.out.println("boardDTO = " + boardDTO);
            System.out.println("userId = " + userId);
            boardService.save(boardDTO, userId);
        }
        else{
            return "redirect:/login";
        }

        return "redirect:/board/list";
    }

    @GetMapping("/list")
    public String findAll(Model model, Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        Page<BoardItemDTO> boardList = boardService.getBoardItemPage(pageable);
        model.addAttribute("boardList", boardList);
        model.addAttribute("maxPage", 5);
        return "boardList";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model,
                           @PageableDefault(page=1) Pageable pageable) {
        /*
            해당 게시글의 조회수를 하나 올리고
            게시글 데이터를 가져와서 detail.html에 출력
         */
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);
        /* 댓글 목록 가져오기 */
        List<CommentDTO> commentDTOList = commentService.findAll(id);
        model.addAttribute("commentList", commentDTOList);
        model.addAttribute("board", boardDTO);
        model.addAttribute("page", pageable.getPageNumber());
        return "boardDetail";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("boardUpdate", boardDTO);
        return "boardUpdate";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute BoardDTO boardDTO, HttpServletRequest request, Model model) throws IOException {

        HttpSession session = request.getSession(false);

        if (session != null) {
            String userId = (String) session.getAttribute("userId");
            System.out.println("boardDTO = " + boardDTO);
            boardService.update(boardDTO, userId);
        }
        else{
            return "redirect:/login";
        }

        return "redirect:/board/" + boardDTO.getId();
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        boardService.delete(id);
        return "redirect:/board/list";
    }

    @GetMapping("/userBoard")
    public ResponseEntity findUserBoard(HttpServletRequest request){
        HttpSession session = request.getSession(false);

        if (session != null) {
            String userId = (String) session.getAttribute("userId");
            List<BoardItemDTO> userBoardList = boardService.findUserBoard(userId);

            return new ResponseEntity<>(userBoardList, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("인증된 유저가 아닙니다", HttpStatus.BAD_REQUEST);
        }
    }
}