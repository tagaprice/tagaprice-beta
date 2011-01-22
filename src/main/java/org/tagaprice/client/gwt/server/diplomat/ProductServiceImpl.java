package org.tagaprice.client.gwt.server.diplomat;

import java.util.*;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tagaprice.client.gwt.shared.entities.*;
import org.tagaprice.client.gwt.shared.entities.dump.*;
import org.tagaprice.client.gwt.shared.entities.productmanagement.*;
import org.tagaprice.client.gwt.shared.rpc.productmanagement.IProductService;
import org.tagaprice.core.api.ServerException;
import org.tagaprice.core.entities.*;
import org.tagaprice.core.entities.Category;
import org.tagaprice.core.entities.Locale;
import org.tagaprice.core.entities.Product;
import org.tagaprice.server.boot.Boot;
import org.tagaprice.server.dao.interfaces.IProductDAO;
import org.tagaprice.server.dao.interfaces.IProductRevisionDAO;
import org.tagaprice.server.service.DefaultProductService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
/**
 * This is the Servlet that relies on the server-service
 * @author Martin
 *
 */
public class ProductServiceImpl extends RemoteServiceServlet implements
IProductService {

	private org.tagaprice.core.api.IProductService coreService;
	private static Locale defaultLocale = new Locale(1, "de", "de");
	private static Account defaultAccount = new Account(1L, "love@you.org", "super", new Date());

	/**
	 * 
	 */
	private static final long serialVersionUID = 1733780607553359495L;

	Logger _log = LoggerFactory.getLogger(ProductServiceImpl.class);


	public ProductServiceImpl() {
		_log.debug("Starting GWT-ProductService");

		_log.debug("Attempting to load product service from core.api");
		try {
			coreService = (org.tagaprice.core.api.IProductService) Boot.getApplicationContext().getBean("defaultProductService");
			//TODO/WORKAROUND this should be mapped by spring, but does not work yet
			
			
//			((DefaultProductService) coreService).setProductDAO((IProductDAO) Boot.getApplicationContext().getBean("defaultProductDAO")); 
//			((DefaultProductService) coreService).setProductRevisionDAO((IProductRevisionDAO) Boot.getApplicationContext().getBean("defaultProductRevisionDAO")); //TODO/WORKAROUND this should be mapped by spring, but does not work yet
			_log.debug("Loaded product service successfully.");
		} catch(Exception e) {
			_log.debug(e.getClass()+": "+e.getMessage());
		} finally {
		}
	}

	@Override
	public IProduct getProduct(IRevisionId revionsId) {
		_log.debug("revisionsId: "+revionsId);

		_log.debug("load dummy product");

		ArrayList<IProduct> list = getProducts(new org.tagaprice.client.gwt.shared.entities.productmanagement.Product("a", null, null));
		_log.debug("found products: " + list.size());

		if(list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public ArrayList<IProduct> getProducts(IProduct searchCriteria) {
		_log.debug("getProducts for name " + searchCriteria);

		List<Product> list = new ArrayList<Product>();
		try {
			if(searchCriteria != null){
				list = coreService.getByTitle(searchCriteria.getTitle());
			}else if (searchCriteria == null){
				list = coreService.getByTitle("");
			}
		} catch (ServerException e) {
			_log.error("Exception thrown: " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			_log.error("Exception thrown: " + e.getMessage());
			e.printStackTrace();
		}
		ArrayList<IProduct> returnList = new ArrayList<IProduct>();

		for(Product p: list) {

			returnList.add(convertProductToGWT(p, 0));
		}
		return returnList;
	}

	@Override
	public IProduct saveProduct(IProduct product) {
		_log.debug("product :"+product);
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ICategory> getCategories() {
		_log.debug("attempting to get available categories");
		// TODO Auto-generated method stub
		return null;
	}

	public org.tagaprice.core.entities.Product convertProductToCore(final IProduct productGWT) {
		//Default values for new product...
		Long productId = 0L;
		Integer revisionNumber = 1;
		String title = productGWT.getTitle();
		Date date = new Date();
		Category category = new Category(null, productGWT.getCategory().getTitle(), null, new Date(), ProductServiceImpl.defaultAccount);
		//If product allready exists...
		if(productGWT.getRevisionId() != null && productGWT.getRevisionId().getId() != 0L && productGWT.getRevisionId().getId() != 0L) {
			//productId =
		}
		ProductRevision revision = new ProductRevision(productId, revisionNumber, title, date, ProductServiceImpl.defaultAccount, null, null, category, "");
		Product productCore = new Product(productGWT.getRevisionId().getId(), ProductServiceImpl.defaultLocale, null);
		return null;
	}

	/**
	 * 
	 * @param productCore
	 * @param revision when 0, then the latest revision is returned.
	 * @return
	 */
	public IProduct convertProductToGWT(final Product productCore, int revisionToGet) {
		_log.debug("Convert core -> GWT, id: " + productCore.getId() + ", rev: " + revisionToGet);
		//these are allways existing products!!!
		ProductRevision pr = productCore.getCurrentRevision();


		//get the data from the latest revision
		long id = productCore.getId();
		long revision = pr.getRevisionNumber();
		String title = pr.getTitle();
		ICategory category = new org.tagaprice.client.gwt.shared.entities.dump.Category(pr.getCategory().getTitle());
		IQuantity quantity = new Quantity(pr.getAmount(), pr.getUnit());

		IRevisionId revisionId = new RevisionId(id, revision);
		IProduct productGWT = new org.tagaprice.client.gwt.shared.entities.productmanagement.Product(revisionId, title, category, quantity);
		return productGWT;
	}

	public void setProductService(org.tagaprice.core.api.IProductService productService) {
		coreService = productService;
	}

}