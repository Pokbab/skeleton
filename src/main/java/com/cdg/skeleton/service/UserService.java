/*
 * @(#)UserService.java $version 2015. 11. 14.
 *
 * Copyright 2015 NHN Ent. All rights Reserved. 
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.cdg.skeleton.service;

import com.cdg.skeleton.domain.User;
import com.cdg.skeleton.exception.LoginFailException;


/**
 * @author Kanghoon Choi
 */
public interface UserService {

	/**
	 * @param user
	 * @throws LoginFailException 
	 */
	void login(User user) throws LoginFailException;

	/**
	 * @param user
	 */
	void signUp(User user);

}
