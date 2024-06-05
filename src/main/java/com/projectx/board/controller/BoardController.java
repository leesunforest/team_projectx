package com.projectx.board.controller;

import com.projectx.board.dto.BoardDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class BoardController {
    @GetMapping("/save")
    public String saveForm() {return "save";}

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO) {return "null";}
}
