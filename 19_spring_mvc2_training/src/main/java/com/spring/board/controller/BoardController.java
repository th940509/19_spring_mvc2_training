package com.spring.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.board.dto.BoardDTO;
import com.spring.board.service.BoardService;

@Controller // 컨트롤러는 @Controller를 명시해야 한다.
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	// value  : url 주소 명시
	// method : 요청방식을 명시 (생략시에 GET,POST를 모두 처리)
	@RequestMapping(value="/boardWrite", method=RequestMethod.GET)
	public String boardWrite() {
		return "board/bWrite"; // servlet-context.xml에 명시된 대로 포워딩 시킬 jsp파일을 작성해준다.
	}
	
	@RequestMapping(value="/boardWrite" , method=RequestMethod.POST)
	public String boardWrite(BoardDTO bdto) throws Exception {
		
		boardService.insertBoard(bdto);
		return "board/main";
	}
}
