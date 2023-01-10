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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdg.skeleton.domain.Board;
import com.cdg.skeleton.service.BoardService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @author Kanghoon Choi
 */
@Controller
@RequestMapping("/board")
public class BoardController {
ㅇㄹㅇㄹ
	df
//	ddfㅇㄹㅇㄹㅇㄹㅇㄹ
//			dfㅇㄹㅇ
//	df
//					df
//	d
//							f
//	df
//
//
//ㅇㄹㅇㄹ
	@Autowired
	private BoardService boardService;

	@RequestMapping("/writeForm")
	public String getWriteForm() {
		return "board/writeForm";
	}
	
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(Board board) {
		
		boardService.write(board);
		
		return "redirect:/board/list";
	}
	
	@RequestMapping("/list")
	public String getList(Model model) {
		
		List<Board> boardList = boardService.getList();
		
		model.addAttribute("boardList", boardList);
		
		return "board/list";
	}
	
	@RequestMapping("/detail")
	public String getDetail(Model model, Board board) {
		
		model.addAttribute("board", boardService.getContent(board));
		
		return "board/detail";
	}
	
	@ResponseBody
	@RequestMapping("/detail/json")
	public String getDetailJson(Model model, Board board) throws JsonProcessingException {
		
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(boardService.getContent(board));
		
		return json;
	}
	
	
	
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(Board board) {
		
		boardService.modify(board);
		
		return "redirect:/board/list";
	}
	
	@RequestMapping("/remove")
	public String remove(Board board) {
		
		boardService.remove(board);
		
		return "redirect:/board/list";
	}
	
	
}
