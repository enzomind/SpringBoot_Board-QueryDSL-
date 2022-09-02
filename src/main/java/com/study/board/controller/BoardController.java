package com.study.board.controller;

import com.study.board.dto.BoardDTO;
import com.study.board.dto.PageRequestDTO;
import com.study.board.model.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/")
    public String index() {
        return "redirect:/board/list";
    }


    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {

        log.info("List...................." + pageRequestDTO);
        model.addAttribute("result", boardService.getList(pageRequestDTO));
    }

    @GetMapping("/register")
    public void register() {
        log.info("register get...");
    }

    @PostMapping("/register")
    public String resiterPost(BoardDTO boardDTO, RedirectAttributes redirectAttributes) {
        log.info("dto..." + boardDTO);

        Long bno = boardService.register(boardDTO);

        redirectAttributes.addFlashAttribute("msg", bno);
        //addFlashAttribute : 한번만 데이터 전달하는 용도
        //msg : 등록 시, 확인 모달 창


        return "redirect:/board/list";
    }
}
