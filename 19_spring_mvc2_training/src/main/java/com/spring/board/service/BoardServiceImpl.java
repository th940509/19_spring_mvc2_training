package com.spring.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.board.dao.BoardDAO;
import com.spring.board.dto.BoardDTO;

@Service //서비스(비지니스 로직)는 @Service를 명시해야 한다.
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDAO boardDAO;
	
	@Override
	public void insertBoard(BoardDTO bdto) throws Exception {
			boardDAO.insert(bdto);
	}

	@Override
	public List<BoardDTO> getBoardList() throws Exception {
		return boardDAO.selectAll();
	}

	@Override // 조회수 증가, 한개의 게시글 가져오기 2개의 기능
	public BoardDTO getOneBoard(int num) throws Exception {
		boardDAO.increaseReadCount(num);
		return boardDAO.selectOne(num);
	}

	@Override
	public boolean updateBoard(BoardDTO bdto) throws Exception {
		boolean isSucceed = false;
		
		if(boardDAO.validateUserCheck(bdto) != null) {
			boardDAO.update(bdto);
			isSucceed = true;
		}
		
		return isSucceed;
	}

	@Override
	public boolean deleteBoard(BoardDTO bdto) throws Exception {
		boolean isSucceed = false;
		
		if(boardDAO.validateUserCheck(bdto) != null) {
			boardDAO.delete(bdto.getNum());
			isSucceed = true;
		}
		
		return isSucceed;
	}

} 
// service           dao                mapper
//   1:n              1          :        1
//                    1          :        1
//                    1          :        1
// 여러개의 dao를 1개 메서드에서 사용할 수 있음.
