package com.projectx.board.controller;

import com.projectx.board.dto.BoardDTO;
import com.projectx.board.dto.BoardItemDTO;
import com.projectx.board.dto.CommentDTO;
import com.projectx.board.service.BoardService;
import com.projectx.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    private final CommentService commentService;

    //게시글 작성 페이지로 이동 (게시판->글작성 버튼 클릭시 호출)
    @GetMapping("/save")
    public String saveForm() {
        return "boardSave";
    }

    //게시판->글작성->저장시 호출
    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO, Principal principal) throws IOException {
        String userId = principal.getName();

        System.out.println("boardDTO = " + boardDTO);
        boardService.save(boardDTO, userId);
        return "index";
    }

    //게시판에서 게시글 list 나타내기
    @GetMapping("/")
    public String findAll(String searchQuery, Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
        Page<BoardItemDTO> boardList = boardService.getBoardItemPage(searchQuery, pageable);
        model.addAttribute("boardList", boardList);
        //model.addAttribute("maxPage", 5);
        return "boardList";
    }

    //게시판에서 게시글 클릭시 상세 페이지
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
        return "detail";
    }

    //특정 id를 가진 게시글의 수정 폼을 보여주는 함수 (수정 버튼 클릭시 호출)
    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("boardUpdate", boardDTO);
        return "update";
    }

    //게시글 수정을 처리하는 함수(수정 폼에서 입력된 데이터를 받아 게시글을 실제로 수정)
    @PostMapping("/update")
    public String update(@ModelAttribute BoardDTO boardDTO, Model model) {
        BoardDTO board = boardService.update(boardDTO);
        model.addAttribute("board", board);
        return "detail";
//        return "redirect:/board/" + boardDTO.getId();
    }

    //게시글 삭제
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        boardService.delete(id);
        return "redirect:/board/";
    }
}









