package com.study.board.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.study.board.entity.Board;
import com.study.board.entity.QBoard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositorytests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertDummies() {
        IntStream.rangeClosed(1,300).forEach(i -> {
            Board board = Board.builder()
                    .title("title : " + i)
                    .content("content : " + i)
                    .writer("writer : " + (i % 10))
                    .build();
        });
    }

    @Test
    public void updateTest() {
        Optional<Board> result = boardRepository.findById(300L); //findById() : 매개값으로 넘긴 엔티티가 있는지 여부 반환 / 있으면 true 없으면 false

        if(result.isPresent()) {
            Board board = result.get(); //get()을 통해 정보 가져옴
            System.out.println("리절트 값은 이거다!!! =>> " + result.get());
            board.changeTitle("타이틀 수정"); //메서드 호출하면서 매개값 넘겨서 엔티티의 값을 매개값으로 세팅
            board.changeContent("내용 수정해보자"); //여기도 넘기고 세팅

            boardRepository.save(board); //save 메서드(JPA가 제공하는 메서드) 호출하면서 엔티티 객체 넘기기
        }
    }

    //'제목/내용/작성자'와 같이 단 하나의 항목으로 검색하는 경우
    //'제목+내용'/'내용+작성자'/'제목+작성자'와 같이 2개의 항목으로 검색하는 경우
    //'제목+내용+작성자'와 같이 3개의 항목으로 검색하는 경우

    //테스트

    @Test
    public void testQuery1() {
        Pageable pageable = PageRequest.of(0, 10, Sort. by("bno").descending());

        QBoard qBoard = QBoard.board;
        String keyword = "수정";
        //동적 처리를 위해 Q도메인 클래스를 얻어옴. 이렇게하면 엔티티 클래스에 선언된 필드들을 변수로 활용할 수 있음.

        BooleanBuilder builder = new BooleanBuilder();
        //BooleanBuilder는 where 문에 들어가는 조건들을 넣어주는 컨테이너

        BooleanExpression expression = qBoard.title.contains(keyword); //키워드를 통한 검색
        builder.and(expression); //여기 통틀어 이해가안되는데 조건값을 만드는 과정이라고 함.
        //만들어진 조건을 where문에 and나 or같은 키워드와 결합

        Page<Board> result = boardRepository.findAll(builder, pageable);
        //검색과 페이징처리를 동시에 하기위한 방법

        result.stream().forEach(board -> {
            System.out.println(board);
        });

    }

    @Test
    public void testQuery2() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        QBoard qBoard = QBoard.board;

        String keyword = "해보자";

        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression exTitle = qBoard.title.contains(keyword);
        BooleanExpression exContent = qBoard.content.contains(keyword);
        BooleanExpression exAll = exTitle.or(exContent);

        builder.and(exAll); //만들어진 조건을 결합

        builder.and(qBoard.bno.gt(0L)); //gno가 0보다 크다라는 조건을 추가

        Page<Board> result = boardRepository.findAll(builder, pageable);

        result.stream().forEach(board -> {
            System.out.println(board);
        });

    }
}
