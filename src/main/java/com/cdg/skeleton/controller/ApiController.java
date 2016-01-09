/*
 * @(#)ApiController.java $version 2016. 1. 9.
 *
 * Copyright 2016 NHN Ent. All rights Reserved. 
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.cdg.skeleton.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cdg.skeleton.domain.Board;
import com.cdg.skeleton.service.BoardService;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;


/**
 * @author Kanghoon Choi
 */
@RequestMapping("/v1")
@RestController
@Api(value = "ApiTest", description = "API 테스트")
public class ApiController {
	
	@Autowired
	private BoardService boardService;

	@RequestMapping(value = "/board", method = RequestMethod.GET)
	public List<Board> getList(@ApiIgnore Board board) {
		
		List<Board> boardList = boardService.getList();
		
		return boardList;
	}
}
