package com.study.board.model;

import com.study.board.dto.BoardDTO;
import com.study.board.dto.PageRequestDTO;
import com.study.board.dto.PageResultDTO;
import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository; //RequiredArgsConstructor의 의존성 자동주입 받기위해 final

    @Override
    public Long register(BoardDTO dto) {

        log.info("DTO -------------------");
        log.info(dto);

        Board entity = dtoToEntity(dto);
        //dto의 멤버 변수들을 불러와 dtoToEntity 메서드 내 빌드를 통해 대입한걸 entity에 대입

        log.info(entity); //변경된 값 확인위해 로그찍고 테스트해보자

        boardRepository.save(entity); //JPA save 메서드 사용해서 실쿼리 동작!

        return entity.getBno();
    }

    @Override
    public PageResultDTO<BoardDTO, Board> getList(PageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("bno").descending());

        Page<Board> result = boardRepository.findAll(pageable);

        Function<Board, BoardDTO> fn = (entity ->
                entityToDto(entity));
        //entityToDTO()를 이용해 펑션을 생성하고 pageResultDTO를 구성.
        //pageResultDto에는 JPA의 처리 결과인 page<Entity>와 function을 전달해 엔티티 객체들을 DTO 리스트로 변환 및
        //화면에 페이지 처리와 필요한 값들을 생성

        return new PageResultDTO<>(result, fn);

    }

    @Override
    public BoardDTO read(Long bno) {

        Optional<Board> result = boardRepository.findById(bno);
        //bno를 매개변수로 가져와 리포.findByID를 통해 가져온 bno를 엔티티 객체 result에 담아옴.

        return result.isPresent()? entityToDto(result.get()): null;
        //result가 존재하면 result를 entitydto로 변환해서 return
    }


}
