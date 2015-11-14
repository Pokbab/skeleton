/*
 * @(#)UserServiceImpl.java $version 2015. 11. 14.
 *
 * Copyright 2015 NHN Ent. All rights Reserved. 
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.cdg.skeleton.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdg.skeleton.domain.User;
import com.cdg.skeleton.exception.LoginFailException;
import com.cdg.skeleton.mapper.UserMapper;


/**
 * @author Kanghoon Choi
 */
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;
	

	@Override
	public void login(User user) throws LoginFailException {
		if (userMapper.selectUser(user) == null) {
			throw new LoginFailException();
		}
	}

}
