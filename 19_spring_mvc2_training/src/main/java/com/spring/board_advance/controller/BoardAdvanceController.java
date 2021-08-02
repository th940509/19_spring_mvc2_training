package com.spring.board_advance.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.board_advance.dto.BoardAdvanceDTO;
import com.spring.board_advance.service.BoardAdvanceService;


@Controller
@RequestMapping("/boardAdvance")
public class BoardAdvanceController {

	@Autowired								
	private BoardAdvanceService boardService;		
	
	
	@RequestMapping(value = "/" , method = RequestMethod.GET)		
	public String main() {
		return "board_advance/main";
	}
	
	@RequestMapping(value = "/boardList") // 특정값 입력 받기
	public String boardList(@RequestParam(name = "onePageViewCount"  , defaultValue = "10")    int onePageViewCount,
							@RequestParam(name = "currentPageNumber" , defaultValue = "1")     int currentPageNumber,
							@RequestParam(name = "searchKeyword"     , defaultValue = "total") String searchKeyword,
							@RequestParam(name = "searchWord"        , defaultValue = "")      String searchWord,
							Model model) throws Exception {
		
		// 페이지의 시작 게시글 인덱스
		int startBoardIdx =  (currentPageNumber -1) * onePageViewCount;
		
		// 관련 정보 MAP 생성 ( 한페이지에 보여줄 게시글 숫자 , 시작페이지의 인덱스 , 검색 키워드 , 검색어 ) 
		Map<String, Object> searchInfo = new HashMap<String, Object>();
		searchInfo.put("onePageViewCount", onePageViewCount);
		searchInfo.put("startBoardIdx", startBoardIdx);
		searchInfo.put("searchKeyword", searchKeyword);
		searchInfo.put("searchWord", searchWord);
		List<BoardAdvanceDTO> boardList = boardService.getSearchBoard(searchInfo);
		
		// 게시글의 전체 개수를 반환하는 관련정보 MAP 생성 ( 검색 키워드 , 검색어 ) 
		Map<String, String> searchCountInfo = new HashMap<String, String>();
		searchCountInfo.put("searchKeyword", searchKeyword);
		searchCountInfo.put("searchWord", searchWord);
		
		// 전체페이지 개수 = 전체게시글 수 / 한페이지에서 보여지는 글수
		int totalBoardCount = boardService.getAllBoardCount(searchCountInfo);
		int addPage = totalBoardCount % onePageViewCount == 0 ? 0 : 1; 		// 나머지가 0이면 추가 x , 나머지가 0이 아니면 +1 페이지 처리
		int totalPageCount = totalBoardCount / onePageViewCount + addPage;
		
		
		// 시작페이지
		int startPage = 1;
		
		if (currentPageNumber % 10 == 0) startPage = (currentPageNumber / 10 - 1) * 10 + 1;
		else 							 startPage = (currentPageNumber / 10) * 10 + 1;							
		
		/*
		 
			[ 예시 ]  
			
			currentPage가 10페이면 시작페이지는 1  		<>		currentPage가 2페이지면  시작페이지는 1  
			currentPage가 20페이면 시작페이지는 11  	<>		currentPage가 12페이지면 시작페이지는 11  
			currentPage가 30페이면 시작페이지는 21 		<>		currentPage가 22페이지면 시작페이지는 21  
			
		*/
		
	
		
		// 끝페이지
		int endPage = startPage + 9;
			
		// 끝페이지가 전체 페이지 개수보다 크다면 
		if (endPage > totalPageCount) {
			endPage = totalPageCount;
		}
		
		// 게시물이 한페이지에 보여지는 것보다 작다면
		if (onePageViewCount > totalBoardCount) {
			startPage = 1;
			endPage = 0;
		}
		
				
		model.addAttribute("startPage" , startPage);
		model.addAttribute("endPage" , endPage);
		model.addAttribute("totalBoardCount" , totalBoardCount);
		model.addAttribute("onePageViewCount" , onePageViewCount);
		model.addAttribute("currentPageNumber" , currentPageNumber);
		model.addAttribute("searchKeyword" , searchKeyword);
		model.addAttribute("searchWord" , searchWord);
		model.addAttribute("boardList",boardList);		
		
		System.out.println("====================================");
		System.out.println("startPage : " + startPage);
		System.out.println("endPage : " + endPage);
		System.out.println("totalBoardCount : " + totalBoardCount);
		System.out.println("onePageViewCount : " + onePageViewCount);
		System.out.println("currentPageNumber : " + currentPageNumber);
		System.out.println("searchKeyword : " + searchKeyword);
		System.out.println("searchWord : " + searchWord);
		System.out.println("====================================\n");
		
		return "board_advance/bList";
		
	}
	
	
	
	@RequestMapping(value = "/boardWrite" , method = RequestMethod.GET)
	public String boardWrite() throws Exception{
		return "board_advance/bWrite";
	}
	
	
	
	@RequestMapping(value = "/boardWrite" , method = RequestMethod.POST)
	public String boardWrite(BoardAdvanceDTO bdto) throws Exception{
		boardService.insertBoard(bdto);
		return "redirect:/boardAdvance/boardList";	
	}
	
	
	
	@RequestMapping(value = "/boardReplyWrite" , method = RequestMethod.GET)
	public String boardReplyWrite(@RequestParam("num") int num , Model model) throws Exception{
		model.addAttribute("bdto", boardService.getOneBoard(num));
		return "board_advance/bReply";
	}
	
	
	
	@RequestMapping(value = "/boardReplyWrite" , method = RequestMethod.POST)
	public String boardReplyWrite(BoardAdvanceDTO bdto) throws Exception{
		boardService.insertReplyBoard(bdto);
		return "redirect:/boardAdvance/boardList";	
	}
	
	
	
	@RequestMapping(value = "/boardInfo")
	public String boardInfo(@RequestParam("num") int num , Model model) throws Exception{
		
		BoardAdvanceDTO bdto = boardService.getOneBoard(num);
		model.addAttribute("bdto",bdto);
		
		return "board_advance/bInfo";
		
	}
	
	
	
	@RequestMapping(value = "/boardUpdate" , method = RequestMethod.GET)
	public String boardUpdate(@RequestParam("num") int num  , Model model) throws Exception{
		
		BoardAdvanceDTO bdto = boardService.getOneBoard(num);
		model.addAttribute("bdto", bdto);
		
		return "board_advance/bUpdate";
		
	}
	
	
	
	@RequestMapping(value = "/boardUpdate" , method = RequestMethod.POST)
	public ResponseEntity<Object> boardUpdate(BoardAdvanceDTO bdto , Model model , HttpServletRequest request) throws Exception{
		
		String message = "";
		
		if (boardService.updateBoard(bdto)) {
			message = "<script>";
			message += "alert('수정 되었습니다.');";
			message += "location.href='"+ request.getContextPath() +"/boardAdvance/boardList';"; // javascript의 ${pageContext.request.contextPath}의 역할과 같다.
			message += "</script>";
		}
		else {
		   message ="<script>"; 
		   message += "alert('비밀번호를 확인해주세요.');";
		   message += "history.go(-1);";
		   message += "</script>";
		}
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		
		return new ResponseEntity<Object>(message , responseHeaders , HttpStatus.OK);
		
	}
	
	
	
	@RequestMapping(value = "/boardDelete" , method = RequestMethod.GET)
	public String boardDelete(@RequestParam("num") int num , Model model ) throws Exception{
		
		BoardAdvanceDTO bdto = boardService.getOneBoard(num);
		model.addAttribute("bdto", bdto);
		
		return "board_advance/bDelete";
		
	}
	
	
	
	@RequestMapping(value = "/boardDelete" , method = RequestMethod.POST)
	public ResponseEntity<Object> boardDelete(Model model , BoardAdvanceDTO bdto , HttpServletRequest request) throws Exception{
		
		String message = "";
		
		if (boardService.deleteBoard(bdto)) {
			message = "<script>";
			message += "alert('삭제 되었습니다.');";
			message += "location.href='"+ request.getContextPath() +"/boardAdvance/boardList';"; // javascript의 ${pageContext.request.contextPath}의 역할과 같다.
			message += "</script>";
		}
		else {
		   message ="<script>"; 
		   message += "alert('비밀번호를 확인해주세요.');";
		   message += "history.go(-1);";
		   message += "</script>";
		}
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		
		return new ResponseEntity<Object>(message , responseHeaders , HttpStatus.OK);
		
	}
	
	
	
	@RequestMapping(value = "/makeDummyData")
	public String makeDummyData() throws Exception{

		boardService.makeDummyData();
		
		return "redirect:/boardAdvance/boardList";
		
	}
	
	
}
