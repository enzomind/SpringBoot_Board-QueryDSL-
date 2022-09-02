package com.study.board.model;


import com.study.board.dto.BoardDTO;
import com.study.board.dto.PageRequestDTO;
import com.study.board.dto.PageResultDTO;
import com.study.board.entity.Board;
import org.springframework.data.domain.PageRequest;

public interface BoardService {
    Long register(BoardDTO dto);

    PageResultDTO<BoardDTO, Board> getList(PageRequestDTO requestDTO);

    //자바8부터 인터페이스 실제 내용을 가지는 코드를 default라는 키워드로 생성할 수 있음.
    //기존에 추상 클래스를 통해 전달해야하는 실제 코드를 인터페이스에 선언할 수 있음.

    default Board dtoToEntity(BoardDTO dto){
        Board entity = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();

        return entity;

    }

    default BoardDTO entityToDto(Board entity) {
        BoardDTO dto = BoardDTO.builder()
                .bno(entity.getBno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .build();

        return dto;
    }
}
