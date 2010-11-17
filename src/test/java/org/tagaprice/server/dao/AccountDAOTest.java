/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License. 
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
*/

/**
 * Project: tagaprice
 * Filename: AccountDAOTest.java
 * Date: 21.07.2010
*/
package org.tagaprice.server.dao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tagaprice.server.DBConnection;
import org.tagaprice.server.dao.postgres.AccountDAO;
import org.tagaprice.server.dao.postgres.LocaleDAO;
import org.tagaprice.shared.entities.Account;

public class AccountDAOTest {
	private AccountDAO dao;
	private DBConnection db;
	private Account testAccount;
	
	@SuppressWarnings("deprecation")
	@Before
	public void setUp() throws Exception {
		db = new EntityDAOTest.TestDBConnection();
		dao = new AccountDAO(db);
		
		testAccount = new Account("testAccount", new LocaleDAO(db).get("English").getId(), "mailaddress@example.org", null);
		dao.save(testAccount);
		assertNotNull(testAccount.getId());
	}
	
	@After
	public void tearDown() throws Exception {
		db.forceRollback();
	}

	@Test
	public void testCreate() throws Exception {
		Account a = new Account(testAccount.getId(), 0);
		dao.get(a);
		assertEquals(a, testAccount);
	}

}
