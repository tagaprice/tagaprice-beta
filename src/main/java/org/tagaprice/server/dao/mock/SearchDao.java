package org.tagaprice.server.dao.mock;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import org.tagaprice.server.dao.IProductDao;
import org.tagaprice.server.dao.ISearchDao;
import org.tagaprice.server.dao.IShopDao;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.Document;
import org.tagaprice.shared.exceptions.dao.DaoException;

public class SearchDao extends DaoClass<Document> implements ISearchDao {

	IProductDao _m_productDAO;
	IShopDao _m_shopDAO;
	
	
	public SearchDao(IProductDao m_productDAO, IShopDao m_shopDAO) {
		_m_productDAO = m_productDAO;
		_m_shopDAO = m_shopDAO;
	}
	
	@Override
	public List<Document> search(String query, BoundingBox bbox)
			throws DaoException {
		// TODO Auto-generated method stub
		
		
		ArrayList<Document> rc = new ArrayList<Document>();
		
		rc.addAll(_m_shopDAO.list());
		rc.addAll(_m_productDAO.list());
	//	rc.addAll(_m_productDAO.find(query));
	//	rc.addAll(_m_shopDAO.find(query));
		
		return rc;
	}

}
