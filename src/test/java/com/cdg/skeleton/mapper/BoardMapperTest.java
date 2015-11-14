package com.cdg.skeleton.mapper;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.cdg.skeleton.domain.Board;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-root.xml")
@Transactional(transactionManager = "transactionManager")
@Rollback(true)
public class BoardMapperTest {
	
	@Autowired
	private BoardMapper mapper;

	@Test
	public void testInsertBoard() throws Exception {
		//Given
		Board board = new Board();
		board.setBoardNo(0);
		board.setTitle("hello");
		board.setContents("hello");
		board.setUserNo(0);

		//When
		mapper.insertBoard(board);
	}
	
	@Test
	public void testSelectBoardList() throws Exception {
		//Given

		//When
		List<Board> actual = mapper.selectBoardList();
		
		//Then
		assertTrue(CollectionUtils.isNotEmpty(actual));
	}
	
	@Test
	public void testUpdateBoard() throws Exception {
		//Given
		Board board = new Board();
		board.setBoardNo(0);
		board.setTitle("test update");
		board.setContents("1234");

		//When
		mapper.updateBoard(board);

		//Then
	}
	
	@Test
	public void testDeleteBoard() throws Exception {
		mapper.deleteBoard(0);
	}
	
}
