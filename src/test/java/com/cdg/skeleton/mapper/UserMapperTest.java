package com.cdg.skeleton.mapper;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.cdg.skeleton.domain.Board;
import com.cdg.skeleton.domain.User;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-root.xml")
@Transactional(transactionManager = "transactionManager")
@Rollback(true)
public class UserMapperTest {
	
	@Autowired
	private UserMapper mapper;
	
	@Test
	public void testSelectBoardList() throws Exception {
		//Given
		User user = new User();
		user.setId("pokbab");
		user.setPassword("1");

		//When
		User actual = mapper.selectUser(user);
		
		//Then
		assertNotNull(actual);
	}
	


}
