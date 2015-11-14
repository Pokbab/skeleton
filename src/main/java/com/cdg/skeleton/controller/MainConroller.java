/*
 * @(#)MainConroller.java $version 2015. 10. 24.
 *
 * Copyright 2015 NHN Ent. All rights Reserved. 
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.cdg.skeleton.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author Kanghoon Choi
 */
@Controller
public class MainConroller {

	@RequestMapping("/index")
	public String index() {
		return "index";
	}
	
}
