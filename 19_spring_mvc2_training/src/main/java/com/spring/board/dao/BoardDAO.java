package com.spring.board.dao;

import java.util.List;

import com.spring.board.dto.BoardDTO;

public interface BoardDAO {
	
	public void insert(BoardDTO bdto) throws Exception;
	public List<BoardDTO> selectAll() throws Exception;
}
