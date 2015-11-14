/*
 * @(#)BoardServiceImpl.java $version 2015. 10. 31.
 *
 * Copyright 2015 NHN Ent. All rights Reserved. 
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.cdg.skeleton.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdg.skeleton.domain.Board;
import com.cdg.skeleton.mapper.BoardMapper;


/**
 * @author Kanghoon Choi
 */
@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardMapper boardMapper;

	@Override
	public void write(Board board) {
		
		long boardNo = makeBoardNo();
		board.setBoardNo(boardNo);
		board.setUserNo(0);
		
		boardMapper.insertBoard(board);
	}

	/**
	 * 게시판 글번호를 생성한다.
	 * 
	 * @return
	 */
	protected long makeBoardNo() {
		return Long.parseLong(DateFormatUtils.format(new Date(), "yyMMddhhmmss"));
	}

	@Override
	public List<Board> getList() {
		return boardMapper.selectBoardList();
	}

	@Override
	public Board getContent(Board board) {
		return boardMapper.selectBoard(board);
	}

	@Override
	public void modify(Board board) {
		boardMapper.updateBoard(board);
	}
}
