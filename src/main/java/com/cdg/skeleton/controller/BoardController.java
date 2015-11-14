/*
 * @(#)BoardController.java $version 2015. 10. 31.
 *
 * Copyright 2015 NHN Ent. All rights Reserved. 
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.cdg.skeleton.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cdg.skeleton.domain.Board;
import com.cdg.skeleton.service.BoardService;


/**
 * @author Kanghoon Choi
 */
@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;

	@RequestMapping("/board/writeForm")
	public String getWriteForm() {
		return "board/writeForm";
	}
	
	@RequestMapping(value = "/board/write", method = RequestMethod.POST)
	public String write(Board board) {
		
		boardService.write(board);
		
		return "redirect:/board/list";
	}
	
	@RequestMapping("/board/list")
	public String getList(Model model) {
		
		List<Board> boardList = boardService.getList();
		
		model.addAttribute("boardList", boardList);
		
		return "board/list";
	}
}
