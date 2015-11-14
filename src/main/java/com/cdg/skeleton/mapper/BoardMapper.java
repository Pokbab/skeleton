/*
 * @(#)BoardMapper.java $version 2015. 10. 24.
 *
 * Copyright 2015 NHN Ent. All rights Reserved. 
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.cdg.skeleton.mapper;

import java.util.List;

import com.cdg.skeleton.domain.Board;


/**
 * @author Kanghoon Choi
 */
public interface BoardMapper {

	public void insertBoard(Board board);
	
	public List<Board> selectBoardList();
	
	public void updateBoard(Board board);
	
	public void deleteBoard(int boardNo);

	public Board selectBoard(Board board);
}
