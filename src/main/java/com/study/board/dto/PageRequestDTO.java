package com.study.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {
    //뷰 단에서 전달되는 page, size 파라미터를 수집하는 역할
    //JPA에서 사용하는 Pageable 타입의 객체 생성이 찐목적
    private int page;
    private int size;

    public PageRequestDTO() {
        this.page = 1;
        this.size = 10;
    }

    public Pageable getPageable(Sort sort){
        //이전 프로젝트에선 direction을 썼던것같은데 pageable은 처음 써봄.

        return PageRequest.of(page - 1, size, sort);

    }
}
