package com.study.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing //엔티티 객체가 생성되고 변경되는걸 감지하는 리스너를 활성화시키기위해 설정 추가
public class BoardIiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoardIiApplication.class, args);
    }

}
