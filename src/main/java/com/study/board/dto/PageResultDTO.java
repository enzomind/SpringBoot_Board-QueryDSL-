package com.study.board.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDTO<DTO, EN> { //DTO타입의 객체는 알겠는데 EN은 뭐지; Entity?
    //제네릭 타입으로 DTO, Entity 타입 지정
    //Service 작성하다보니 의도를 알겠다.. 객체 2개를 가져와분다는거구만?

    private List<DTO> dtoList;

    private int totalPage;
    private int page;
    private int size;

    private int start, end;

    private boolean prev, next;

    //페이지 번호 목록
    private List<Integer> pageList;





    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn){
        //Function<EN, DTO> <- Entity 객체들을 DTO로 변환
        //Page 형태의 엔티티 result와 Function fn을 매개값으로 받아옴

        dtoList = result.stream().map(fn).collect(Collectors.toList());
        //이렇게 하면 나중에 new Page<E> 타입 생성 시, 해당 메서드를 이용해 처리 가능

        totalPage = result.getTotalPages();
        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable) {

        this.page = pageable.getPageNumber() + 1; //0이 아닌 1부터 시작하도록 1 추가
        this.size = pageable.getPageSize();

        int tempEnd = (int)(Math.ceil(page/10.0)) * 10;

        start = tempEnd - 9;
        prev = start > 1;
        end = totalPage > tempEnd ? tempEnd : totalPage;

        next = totalPage > tempEnd;

        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());

    }

}
