package com.spring.board.service;

import java.util.List;

import com.spring.board.dto.BoardDTO;

public interface BoardService {

	public void insertBoard(BoardDTO bdto) throws Exception;
	public List<BoardDTO> getBoardList() throws Exception;
}
