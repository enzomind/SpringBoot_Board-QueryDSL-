package com.study.board.service;

import com.study.board.dto.BoardDTO;
import com.study.board.dto.PageRequestDTO;
import com.study.board.dto.PageResultDTO;
import com.study.board.entity.Board;
import com.study.board.model.BoardService;
import com.study.board.model.BoardServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BoardServiceTests {

    @Autowired
    private BoardServiceImpl boardService;


    @Test
    public void testList() {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(12)
                .size(10)
                .build();
        //PageRequestDTO 클래스 내 빌더로 세팅하고

        PageResultDTO<BoardDTO, Board> resultDTO = boardService.getList(pageRequestDTO);
        //PageResultDTO 생성자를 이용해 resultDTO에 서비스의 겟리스트 메서드(세팅한 빌더 파라미로 받아온)를 넣기

        System.out.println("PREV : " + resultDTO.isPrev());
        System.out.println("NEXT : " + resultDTO.isNext());
        System.out.println("TOTAL : " + resultDTO.getTotalPage());
        System.out.println("-----------------------------------");

        for(BoardDTO boardDto : resultDTO.getDtoList()) {
            System.out.println(boardDto);
        }

        System.out.println("===================================");
        resultDTO.getPageList().forEach(i -> System.out.println(i));
    }

    @Test
    public void testRegister() {

        BoardDTO boardDTO = BoardDTO.builder()
                .title("문의가 있어 급히 연락드립니다.")
                .content("실행이 되지않는데 이거 어떻게 해결하나요?")
                .writer("운영팀")
                .build();

        boardService.register(boardDTO);

    }


}
