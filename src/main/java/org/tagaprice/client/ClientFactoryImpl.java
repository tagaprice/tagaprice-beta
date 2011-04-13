package org.tagaprice.client;

import org.tagaprice.client.features.accountmanagement.login.ILoginView;
import org.tagaprice.client.features.accountmanagement.login.ILogoutView;
import org.tagaprice.client.features.accountmanagement.login.devView.LoginViewImpl;
import org.tagaprice.client.features.accountmanagement.login.devView.LogoutViewImpl;
import org.tagaprice.client.features.accountmanagement.register.IRegisterView;
import org.tagaprice.client.features.accountmanagement.register.IRegisteredView;
import org.tagaprice.client.features.accountmanagement.register.devView.RegisterViewImpl;
import org.tagaprice.client.features.accountmanagement.register.devView.RegisteredViewImpl;
import org.tagaprice.client.features.productmanagement.createProduct.ICreateProductView;
import org.tagaprice.client.features.productmanagement.createProduct.devView.*;
import org.tagaprice.client.features.productmanagement.listProducts.*;
import org.tagaprice.client.features.productmanagement.listProducts.devView.*;
import org.tagaprice.client.features.receiptmanagement.createReceipt.ICreateReceiptView;
import org.tagaprice.client.features.receiptmanagement.createReceipt.devView.CreateReceiptViewImpl;
import org.tagaprice.client.features.receiptmanagement.listReceipts.IListReceiptsView;
import org.tagaprice.client.features.receiptmanagement.listReceipts.devView.ListReceiptsViewImpl;
import org.tagaprice.client.features.shopmanagement.createShop.ICreateShopView;
import org.tagaprice.client.features.shopmanagement.createShop.devView.CreateShopViewImpl;
import org.tagaprice.client.features.shopmanagement.listShops.ListShopsView;
import org.tagaprice.client.features.shopmanagement.listShops.devView.*;
import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.entities.receiptManagement.ReceiptEntry;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import org.tagaprice.shared.rpc.accountmanagement.ILoginService;
import org.tagaprice.shared.rpc.accountmanagement.ILoginServiceAsync;
import org.tagaprice.shared.rpc.categorymanagement.ICategoryService;
import org.tagaprice.shared.rpc.categorymanagement.ICategoryServiceAsync;
import org.tagaprice.shared.rpc.productmanagement.IProductService;
import org.tagaprice.shared.rpc.productmanagement.IProductServiceAsync;
import org.tagaprice.shared.rpc.receiptmanagement.IReceiptService;
import org.tagaprice.shared.rpc.receiptmanagement.IReceiptServiceAsync;
import org.tagaprice.shared.rpc.searchmanagement.ISearchService;
import org.tagaprice.shared.rpc.searchmanagement.ISearchServiceAsync;
import org.tagaprice.shared.rpc.shopmanagement.*;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.*;
import com.google.gwt.place.shared.PlaceController;
/**
 * The ClientFactory provides singletones for <ul><li>an EventBus</li><li>a PlaceController</li><li>and the Views</li></ul>.
 * @author Helga Weik (kaltra)
 *
 */
public class ClientFactoryImpl implements ClientFactory {
	/**
	 * The EventBus is unique for the whole GWT Application.
	 */
	private static final EventBus eventBus = new SimpleEventBus();
	/**
	 * The PlaceController is unique for the whole GWT Application.
	 */
	private static final PlaceController placeController = new PlaceController(
			ClientFactoryImpl.eventBus);
	private static final ProductCoreColumnDefinitions productCoreColumnDefinitions = new ProductCoreColumnDefinitions();
	private static final ShopColumnDefinitions shopColumnDefinitions = new ShopColumnDefinitions();

	//private static final ProductServiceDispatchImpl productServiceDispatch = new ProductServiceDispatchImpl();

	//VIEWS
	private static final CreateShopViewImpl<ReceiptEntry> createShopview = new CreateShopViewImpl<ReceiptEntry>();
	private static final ListShopsViewImpl<Shop> listShopsView = new ListShopsViewImpl<Shop>();
	private static final ILoginView loginView = new LoginViewImpl();
	private static final ILogoutView LOGOUT_VIEW = new LogoutViewImpl();
	private static final ICreateReceiptView CREATE_RECEIPT_VIEW = new CreateReceiptViewImpl();
	private static final ListProductsViewImpl<Product> productListView = new ListProductsViewImpl<Product>();
	private static final ICreateProductView createProductView = new CreateProductViewImpl();
	private static final IRegisterView registerView = new RegisterViewImpl();
	private static final IRegisteredView registeredView = new RegisteredViewImpl();
	private static final IListReceiptsView listReceiptView = new ListReceiptsViewImpl();

	//RPC
	private static final IShopServiceAsync I_SHOP_SERVICE_ASYNC = GWT.create(IShopService.class);
	private static final IReceiptServiceAsync I_RECEIPT_SERVICE_ASYNC = GWT.create(IReceiptService.class);
	private static final IProductServiceAsync I_PRODUCT_SERVICE_ASYNC = GWT.create(IProductService.class);
	private static final ICategoryServiceAsync I_CATEGORY_SERVICE_ASYNC = GWT.create(ICategoryService.class);
	private static final ISearchServiceAsync I_SEARCH_SERVICE_ASYNC = GWT.create(ISearchService.class);
	private static final ILoginServiceAsync I_LOGIN_SERVICE_ASYNC = GWT.create(ILoginService.class);

	private static Address I_ADDRESS=new Address();
	private static AccountPersistor s_accountPersistor = new AccountPersistor();

	public ClientFactoryImpl() {
		ClientFactoryImpl.productListView.setColumnDefinitions(ClientFactoryImpl.productCoreColumnDefinitions
				.getColumnDefinitions());
		ClientFactoryImpl.listShopsView.setColumnDefinitions(ClientFactoryImpl.shopColumnDefinitions.getColumnDefinitions());
	}

	@Override
	public EventBus getEventBus() {
		return ClientFactoryImpl.eventBus;
	}

	@Override
	public PlaceController getPlaceController() {
		return ClientFactoryImpl.placeController;
	}

	@Override
	public ICreateProductView getEditProductView() {
		return ClientFactoryImpl.createProductView;
	}
	@Override
	public ListProductsView<Product> getListProductsView() {
		return ClientFactoryImpl.productListView;
	}



	@Override
	public ICreateProductView getCreateProductView() {
		return ClientFactoryImpl.createProductView;
	}

	@Override
	public ILoginView getLoginView() {
		return ClientFactoryImpl.loginView;
	}

	@Override
	public ICreateShopView<ReceiptEntry> getCreateShopView() {
		return ClientFactoryImpl.createShopview;
	}

	@Override
	public ILoginServiceAsync getLoginService() {
		return ClientFactoryImpl.I_LOGIN_SERVICE_ASYNC;
	}

	@Override
	public ILogoutView getLogoutView() {
		return ClientFactoryImpl.LOGOUT_VIEW;
	}

	@Override
	public IShopServiceAsync getShopService() {
		return ClientFactoryImpl.I_SHOP_SERVICE_ASYNC;
	}

	@Override
	public ListShopsView<Shop> getListShopsView() {
		return ClientFactoryImpl.listShopsView;
	}

	@Override
	public ICreateReceiptView getCreateReceiptView() {
		return ClientFactoryImpl.CREATE_RECEIPT_VIEW;
	}

	@Override
	public IReceiptServiceAsync getReceiptService() {
		return ClientFactoryImpl.I_RECEIPT_SERVICE_ASYNC;
	}

	@Override
	public IProductServiceAsync getProductService() {
		return ClientFactoryImpl.I_PRODUCT_SERVICE_ASYNC;
	}

	@Override
	public ICategoryServiceAsync getCategoryService() {
		return ClientFactoryImpl.I_CATEGORY_SERVICE_ASYNC;
	}

	@Override
	public ISearchServiceAsync getSearchService() {
		// TODO Auto-generated method stub
		return ClientFactoryImpl.I_SEARCH_SERVICE_ASYNC;
	}


	@Override
	public IRegisterView getRegisterView() {
		return ClientFactoryImpl.registerView;
	}

	@Override
	public IRegisteredView getRegisteredView() {
		return ClientFactoryImpl.registeredView;
	}

	@Override
	public IListReceiptsView getListReceiptsView() {
		return ClientFactoryImpl.listReceiptView;
	}

	@Override
	public AccountPersistor getAccountPersistor() {
		return ClientFactoryImpl.s_accountPersistor;
	}


}
