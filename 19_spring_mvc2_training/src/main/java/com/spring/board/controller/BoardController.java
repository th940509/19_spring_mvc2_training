package com.spring.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.board.dto.BoardDTO;
import com.spring.board.service.BoardService;

@Controller // 컨트롤러는 @Controller를 명시해야 한다.
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	// value  : url 주소 명시
	// method : 요청방식을 명시 (생략시에 GET,POST를 모두 처리)
	@RequestMapping(value="/" , method=RequestMethod.GET)
	public String main() {
		return "board/main";
	}
	
	@RequestMapping(value="/boardWrite", method=RequestMethod.GET)
	public String boardWrite() {
		return "board/bWrite"; // servlet-context.xml에 명시된 대로 포워딩 시킬 jsp파일을 작성해준다.
	}
	
	@RequestMapping(value="/boardWrite" , method=RequestMethod.POST)
	public String boardWrite(BoardDTO bdto) throws Exception {
		
		boardService.insertBoard(bdto);
		return "board/main";
	}
	
	@RequestMapping(value="/boardList", method=RequestMethod.GET)
	public String boardList(Model model) throws Exception {
		
		List<BoardDTO> boardList = boardService.getBoardList();
		
		// model.addAttribute(attribute name, attribute value);
		model.addAttribute("boardList", boardList);
		
		return "board/bList";
	}
	
	@RequestMapping(value="/boardInfo", method=RequestMethod.GET)
	public String boardInfo(@RequestParam("num") int num, Model model) throws Exception {
		
		BoardDTO bdto = boardService.getOneBoard(num);
		
		model.addAttribute("bdto", bdto);
		
		return "board/bInfo";
	}
	
	@RequestMapping(value="/boardUpdate", method=RequestMethod.GET)
	public String boardUpdate(@RequestParam("num") int num, Model model) throws Exception {
		model.addAttribute("bdto",boardService.getOneBoard(num));
		return "board/bUpdate";
	}
	
	@RequestMapping(value="/boardUpdate", method=RequestMethod.POST)
	public String boardUpdate(BoardDTO bdto, Model model) throws Exception {
		
		if(boardService.updateBoard(bdto)) model.addAttribute("success",true);
		else                               model.addAttribute("success",false);
		
		return "board/bUpdatePro";
	}           // model 객체 사용 시 controller에서 data를 view로 뿌린다 (jsp : requaest.setAttribute 와 비슷한 기능)
	            // 반환이 된 곳에서 model.addAttribute의 data를 ${}형태로 사용
	
	@RequestMapping(value="/boardDelete", method=RequestMethod.GET)
	public String boardDelete(@RequestParam("num") int num, Model model) throws Exception {
		model.addAttribute("bdto" , boardService.getOneBoard(num));
		return "board/bDelete";
	}
	
	@RequestMapping(value="/boardDelete" , method=RequestMethod.POST)
	public String boardDelete(BoardDTO bdto, Model model) throws Exception {
		if(boardService.deleteBoard(bdto)) model.addAttribute("success", true);
		else                               model.addAttribute("success", false);
		
		return "board/bDeletePro";
	}
}
