/*
 * @(#)Board.java $version 2015. 10. 24.
 *
 * Copyright 2015 NHN Ent. All rights Reserved. 
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.cdg.skeleton.domain;

import java.util.Date;

import lombok.Data;


/**
 * @author Kanghoon Choi
 */
@Data
public class Board {
	private long boardNo;
	private String title;
	private String contents;
	private int userNo;
	private Date createDate;
	private Date updateDate;
	private int hit;
}
