/*
 * @(#)MainConroller.java $version 2015. 10. 24.
 *
 * Copyright 2015 NHN Ent. All rights Reserved. 
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.cdg.skeleton.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cdg.skeleton.domain.User;
import com.cdg.skeleton.exception.LoginFailException;
import com.cdg.skeleton.service.UserService;


/**
 * @author Kanghoon Choi
 */
@Controller
public class MainConroller {
	
	@Autowired
	private UserService userService;

	@RequestMapping("/index")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/loginForm")
	public String getLoginForm() {
		return "loginForm";
	}
	
	
	@RequestMapping("/login")
	public String login(User user) throws LoginFailException {
		
		userService.login(user);
		
		return "redirect:/board/list";
	}
	
	@ExceptionHandler(value = LoginFailException.class)
	public String loginFail() {
		return "redirect:/loginForm";
	}
}
