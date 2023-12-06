package com.soon.Ex02;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.soon.Ex02.entity.Board;


@SpringBootTest

class Ex02ApplicationTests {
	
	@Autowired
	private BoardRepository boardRepository;
	
	// 테스트 코드를 실행할 때는 서버를 닫아줘야함
	// 테스트 코드를 실핼할 때는 같은 패키지에 있어야 함
	@Test
	void contextLoads() {
		Board b1 = new Board();
		b1.setBoardTitle("스프링부트가 뭐냐?");
		b1.setBoardCon("그게 뭔데?");
		b1.setBoardDate(LocalDateTime.now());
		this.boardRepository.save(b1); // insert문
		
		Board b2 = new Board();
		b2.setBoardTitle("boardRepository");
		b2.setBoardCon("그게 DB메소드 저장하고 있는 인터페이스");
		b2.setBoardDate(LocalDateTime.now());
		this.boardRepository.save(b2); // insert문
	}
	

}
