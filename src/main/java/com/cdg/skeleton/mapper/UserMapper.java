/*
 * @(#)UserMapper.java $version 2015. 11. 14.
 *
 * Copyright 2015 NHN Ent. All rights Reserved. 
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.cdg.skeleton.mapper;

import com.cdg.skeleton.domain.User;


/**
 * @author Kanghoon Choi
 */
public interface UserMapper {

	User selectUser(User user);

	/**
	 * @param user
	 */
	void insertUser(User user);
}
