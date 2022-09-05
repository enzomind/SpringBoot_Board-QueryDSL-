package com.study.board.controller;

import com.study.board.dto.BoardDTO;
import com.study.board.dto.PageRequestDTO;
import com.study.board.model.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public String registerPost(BoardDTO boardDTO, RedirectAttributes redirectAttributes) {
        log.info("dto..." + boardDTO);

        Long bno = boardService.register(boardDTO);

        redirectAttributes.addFlashAttribute("msg", bno);
        //addFlashAttribute : 한번만 데이터 전달하는 용도
        //msg : 등록 시, 확인 모달 창으로써 register.html이 아닌 list.html에서 구현



        return "redirect:/board/list";
    }

    @GetMapping("/read")
    public void read(Long bno, @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Model model) {
        //ModelAttiribute 안써도되지만 requestDTO로 명시하기위해서 사용

        log.info("bno : " + bno);

        BoardDTO dto = boardService.read(bno);
        model.addAttribute("dto", dto);

    }
}
