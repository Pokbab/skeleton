package com.cdg.skeleton.service;

import static org.junit.Assert.*;

import org.junit.Test;



public class BoardServiceImplTest {
	
	private BoardServiceImpl service;

	@Test
	public void test() throws Exception {
		//Given
		service = new BoardServiceImpl();

		//When
		long actual = service.makeBoardNo();
		
		//Then
		assertTrue(actual > 0);
	}
}
