package org.tagaprice.client.gwt.client;

import org.tagaprice.client.gwt.client.features.accountmanagement.login.ILoginView;
import org.tagaprice.client.gwt.client.features.productmanagement.ProductServiceDispatch;
import org.tagaprice.client.gwt.client.features.productmanagement.createProduct.*;
import org.tagaprice.client.gwt.client.features.productmanagement.listProducts.ListProductsView;
import org.tagaprice.client.gwt.client.features.shopmanagement.createShop.ICreateShopView;
import org.tagaprice.client.gwt.shared.entities.productmanagement.IProduct;
import org.tagaprice.client.gwt.shared.rpc.accountmanagement.ILoginServiceAsync;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;

public interface ClientFactory {
	/**
	 * GWT Magic EventBus. Is never used programmatically.
	 * 
	 * @return
	 */
	EventBus getEventBus();

	/**
	 * GWT Magic PlaceController. Is used to change the Place (-> Activity ->
	 * Data -> View)
	 * 
	 * @return
	 */
	PlaceController getPlaceController();


	/**
	 * Singleton for ListProductsView
	 * 
	 * @return
	 */
	ListProductsView<IProduct> getListProductsView();

	/**
	 * Locale Dispatch for remote RPC Service
	 * 
	 * @return
	 */
	ProductServiceDispatch getProductServiceDispatch();


	ILoginServiceAsync getLoginService();


	/****************** VIEWS ***********************/

	/**
	 * Returns the CreateProductView for the selected screen
	 * @return the CreateProductView for the selected screen
	 */
	ICreateProductView getCreateProductView();

	/**
	 * Singleton for EditProductView
	 * 
	 * @return
	 */
	ICreateProductView getEditProductView();


	/**
	 * Returns the LoginView
	 * @return the LoginView
	 */
	ILoginView getLoginView();


	/**
	 * Returns the CreateShopView
	 * @return the CreateShopView
	 */
	ICreateShopView getCreateShopView();

	/****************** WIDGETS ***********************/
}
