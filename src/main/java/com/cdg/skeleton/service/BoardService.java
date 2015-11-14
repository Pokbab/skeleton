/*
 * @(#)BoardService.java $version 2015. 10. 31.
 *
 * Copyright 2015 NHN Ent. All rights Reserved. 
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.cdg.skeleton.service;

import java.util.List;

import com.cdg.skeleton.domain.Board;


/**
 * @author Kanghoon Choi
 */
public interface BoardService {

	/**
	 * 게시판 글을 등록한다.
	 * 
	 * @param board
	 */
	void write(Board board);

	/**
	 * 게시판 목록을 가져온다.
	 * 
	 * @return
	 */
	List<Board> getList();

	/**
	 * @param board
	 * @return
	 */
	Board getContent(Board board);

	/**
	 * 게시판을 수정한다
	 * 
	 * @param board
	 */
	void modify(Board board);

	/**
	 * 게시글을 삭제한다
	 * 
	 * @param board
	 */
	void remove(Board board);

}
