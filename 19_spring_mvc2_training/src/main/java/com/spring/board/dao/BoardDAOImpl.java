package com.spring.board.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.board.dto.BoardDTO;

@Repository //DAO는 @Repository를 명시하여야 한다.
public class BoardDAOImpl implements BoardDAO {
	
	@Autowired
	private SqlSession sqlSession; // Mybatis를 사용하기 위한 객체 생성
	
	@Override
	public void insert(BoardDTO bdto) throws Exception {
		sqlSession.insert("mapper.board.insertBoard", bdto); // insert(namespace명.id명 , 매게변수)
	}

	@Override
	public List<BoardDTO> selectAll() throws Exception {
		return sqlSession.selectList("mapper.board.getAllBoard");
	}

}
