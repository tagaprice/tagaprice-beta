package org.tagaprice.server.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tagaprice.server.DBConnection;
import org.tagaprice.server.dao.postgres.AccountDAO;
import org.tagaprice.server.dao.postgres.LocaleDAO;
import org.tagaprice.server.dao.postgres.ProductDAO;
import org.tagaprice.shared.AccountData;

public class ProductDAOTest {
	@SuppressWarnings("unused")
	private ProductDAO dao;
	private DBConnection db;
	private int localeId;
	@SuppressWarnings("unused")
	private long uid;

	@SuppressWarnings("deprecation")
	@Before
	public void setUp() throws Exception {
		db = new EntityDAOTest.TestDBConnection();
		dao = new ProductDAO(db);
		localeId = new LocaleDAO(db).get("English").getId();
		AccountData a = new AccountData("testAccount", localeId, "address@example.com", null);
		new AccountDAO(db).save(a);
		uid = a.getId();

	}

	@After
	public void tearDown() throws Exception {
		db.forceRollback();
	}

	@Test
	public void testCreate() throws Exception {
//		ProductData prod = new ProductData("test-product", localeId, uid, null, null, null, null);
//		dao.save(prod);
//		ProductData prod2 = new ProductData(prod.getId());
//		dao.getById(prod2);
//		assertEquals(prod, prod2);
	}

	@Test
	public void testRevs() throws Exception {
//		ProductData prod, prod1, prod2;
//		prod = new ProductData("test-product", localeId, uid, null, null, null, null);
//		dao.save(prod);
//		prod.setTitle("test-product v2");
//		prod.setImageSrc("/foo/bar.png");
//		dao.save(prod);
//		assertEquals("product revision should be 2 after two save() calls", 2, prod.getRev());
//		
//		prod2 = new ProductData(prod.getId());
//		dao.get(prod2);
//		assertEquals(prod, prod2);
//		
//		prod1 = new ProductData(prod.getId(), 1);
//		dao.get(prod1);
//		assertEquals(null, prod1.getImageSrc());
//		assertEquals("prod1.title is wrong", "test-product", prod1.getTitle());
	}

}
