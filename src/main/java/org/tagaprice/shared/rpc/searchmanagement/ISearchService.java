package org.tagaprice.shared.rpc.searchmanagement;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.productmanagement.IProduct;
import org.tagaprice.shared.entities.shopmanagement.IShop;
import org.tagaprice.shared.entities.shopmanagement.ISubsidiary;

@RemoteServiceRelativePath("searchservice")
public interface ISearchService extends RemoteService {

	/**
	 * Returns a list of shops specified by a searchString and a BoundingBox
	 * 
	 * @param searchString
	 *            Name of the Shop
	 * @param bbox
	 *            the area where to search for this shop
	 * @return a list of shop results. (Is never null)
	 */
	ArrayList<IShop> searchShop(String searchString, BoundingBox bbox);

	/**
	 * Returns a list of {@link IProduct} sorted by {@link IShop} plus {@link IAddress}, than {@link IShop}, than
	 * similar
	 * products.
	 * 
	 * @param searchString
	 *            Name of the Product
	 * @param address
	 *            name of the {@link IAddress} where the search process should begin
	 * @return a list of {@link IProduct} sorted by {@link IAddress} plus {@link IAddress}, than {@link IAddress}, than
	 *         similar
	 *         products.
	 */
	ArrayList<IProduct> searchProduct(String searchString, ISubsidiary address);
}