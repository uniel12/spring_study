package com.soon.Ex02.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.soon.Ex02.BoardRepository;
import com.soon.Ex02.entity.Board;

import lombok.RequiredArgsConstructor;
// @RequiredArgsConstructor - final이 붙은 속성을 포함하는 생성자를 자동으로 생성
@RequiredArgsConstructor
@Controller
public class BoardController {
	
	private final BoardRepository boardRepository;
	
	@GetMapping("/board")
	public String boardHome(Model model){
		// findAll - 테이블의 모든 데이터를 조회 (select *문)
		List<Board> boardList = this.boardRepository.findAll(); 
		model.addAttribute(boardList);
		return "board_home";
	}
}
