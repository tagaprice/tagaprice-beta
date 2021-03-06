package org.tagaprice.client.features.productmanagement.createProduct;

import java.util.Date;
import java.util.List;

import org.tagaprice.client.ClientFactory;
import org.tagaprice.client.features.categorymanagement.product.ProductCategoryPlace;
import org.tagaprice.client.features.receiptmanagement.createReceipt.CreateReceiptPlace;
import org.tagaprice.client.generics.events.DisplayLoginEvent;
import org.tagaprice.client.generics.events.DisplayLoginEvent.LoginType;
import org.tagaprice.client.generics.events.InfoBoxDestroyEvent;
import org.tagaprice.client.generics.events.InfoBoxShowEvent;
import org.tagaprice.client.generics.events.InfoBoxShowEvent.INFOTYPE;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.productmanagement.*;
import org.tagaprice.shared.entities.searchmanagement.StatisticResult;
import org.tagaprice.shared.exceptions.UserNotLoggedInException;
import org.tagaprice.shared.exceptions.dao.DaoException;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class CreateProductActivity implements ICreateProductView.Presenter, Activity {

	private CreateProductPlace _place;
	private ClientFactory _clientFactory;
	private Product _product;
	private ICreateProductView _createProductView;
	private int _statisticDebounce = 0;

	public CreateProductActivity(CreateProductPlace place, ClientFactory clientFactory) {
		Log.debug("create class");
		_place = place;
		_clientFactory = clientFactory;
		
		
		
	}

	@Override
	public void goTo(Place place) {
		_clientFactory.getPlaceController().goTo(place);
	}

	@Override
	public String mayStop() {
		_createProductView.onStop();
		_statisticDebounce++;
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCancel() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCategorySelectedEvent() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void onCanceEvent(){
		
		//redirect
		if(_place.getRedirectId()!=null){
			goTo(new CreateReceiptPlace(_place.getId(), null, "product"));
		}else{
			if(_product.getId()==null)
				goTo(new CreateProductPlace(null, null, null, _place.getTitle(), _place.getLat(), _place.getLon(), _place.getZoom()));
			else
				goTo(new CreateProductPlace(_place.getId(), _place.getRevision(), null, null, _place.getLat(), _place.getLon(), _place.getZoom()));
		}
		
	}

	@Override
	public void onSaveEvent() {
		Log.debug("Save Product");
		//Get data from View
		_product.setTitle(_createProductView.getTitle());
		_product.setCategory(_createProductView.getCategory());
		_product.setUnit(_createProductView.getUnit());
		_product.setPackages(_createProductView.getPackages());

		//infox
		//destroy all
		_clientFactory.getEventBus().fireEvent(new InfoBoxDestroyEvent(CreateProductActivity.class));

		
		if(!_product.getTitle().isEmpty() && !_product.getTitle().trim().equals("") && _product.getUnit()!=null && _product.getUnit().getId()!=null){


			final InfoBoxShowEvent trySaving = new InfoBoxShowEvent(CreateProductActivity.class, "saving...", INFOTYPE.INFO,0);
			_clientFactory.getEventBus().fireEvent(trySaving);


			_clientFactory.getProductService().saveProduct(_product, new AsyncCallback<Product>() {

				@Override
				public void onFailure(Throwable caught) {
					_clientFactory.getEventBus().fireEvent(new InfoBoxDestroyEvent(trySaving));
					try{
						throw caught;
					}catch (UserNotLoggedInException e){
						Log.warn(e.getMessage());
						_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(CreateProductActivity.class, "Please login or create new user to save.", INFOTYPE.ERROR));
					}catch (Throwable e){
						Log.error(e.getMessage());
					}

				}

				@Override
				public void onSuccess(Product result) {
					_clientFactory.getEventBus().fireEvent(new InfoBoxDestroyEvent(trySaving));
					_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(CreateProductActivity.class, "Product save successfull.", INFOTYPE.SUCCESS));
					Log.debug("Product save successful");

					//redirect
					if(_place.getRedirectId()!=null){
						goTo(new CreateReceiptPlace(_place.getId(), result.getId(), "product"));
					}else{
						if(_product.getId()==null)
							goTo(new CreateProductPlace(result.getId(), null, null, null, _place.getLat(), _place.getLon(), _place.getZoom()));
						else
							updateView(result);
					}
					
					//setReadable
					_createProductView.setReadOnly(true);

				}
			});
		}else{
			String errorString ="";
			if(_product.getTitle().isEmpty() || _product.getTitle().trim().equals(""))
				errorString +="Title must not be empty.";
			
			if(_product.getUnit()==null || _product.getUnit().getId()==null)
				errorString+=" Unit must be set.";
			
			_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(CreateProductActivity.class, errorString, INFOTYPE.ERROR));
		}




	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTitleSelectedEvent() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUnitSelectedEvent() {
		Log.debug("Unit has changed");

	}

	@Override
	public void start(final AcceptsOneWidget panel, EventBus eventBus) {
		_product = new Product();
		Log.debug("activity startet");
		_createProductView = _clientFactory.getCreateProductView();
		_createProductView.setPresenter(this);
		_createProductView.setStatisticIsLoading();


		if (_place.getId() != null && _place.getRedirectId()==null) {
			Log.debug("Get Product: id=" + _place.getId() + ", rev: "
					+ _place.getRevision());
			// panel.setWidget(new
			// Label("Get Product: id="+_place.getRevisionId().getId()+", rev: "+_place.getRevisionId().getRevision()));


			Log.debug("Load Categories...");


			_clientFactory.getProductService().getProduct(_place.getId(), _place.getRevision(), new AsyncCallback<Product>() {

				@Override
				public void onFailure(Throwable caught) {
					try{
						throw caught;
					}catch (DaoException e){
						Log.error("DaoException at getProduct: "+caught.getMessage());
					}catch (Throwable e){
						Log.error("Unexpected exception: "+caught.getMessage());
						_clientFactory.getEventBus().fireEvent(new InfoBoxShowEvent(CreateProductActivity.class, "Unexpected exception: "+caught.getMessage(), INFOTYPE.ERROR,0));
					}


				}

				@Override
				public void onSuccess(Product result) {
					Log.debug("Get Product sucessfull id: "+result.getId());
					updateView(result);
					panel.setWidget(_createProductView);
					_createProductView.setReadOnly(true);
					
					

					_createProductView.setStatisticLatLon(
							Double.parseDouble(_place.getLat()), 
							Double.parseDouble(_place.getLon()));
					

					Window.setTitle("Product - "+result.getTitle());
				}
			});


		}else {
			Log.debug("Create new Product");
			Window.setTitle("Create Product");
			
			_product.setTitle(_place.getTitle());
			updateView(_product);
			panel.setWidget(_createProductView);
			
			//setReadable
			onEditEvent();
			//_createProductView.setReadOnly(false);
		
			
		}

	}

	private void updateView(Product product) {
		_product = product;
		_createProductView.setTitle(product.getTitle());
		_createProductView.setCategory(product.getCategory());
		_createProductView.setUnit(product.getUnit());

		_createProductView.setPackages(product.getPackages());
		
		onStatisticChangedEvent(
				_createProductView.getStatisticBoundingBox(), 
				_createProductView.getStatisticBeginDate(), 
				_createProductView.getStatisticEndDate());
	}

	@Override
	public void onStatisticChangedEvent(BoundingBox bbox, Date begin, Date end) {
		Log.debug("onStatisticChangedEvent: bbox: "+bbox+", begin: "+begin+", end: "+end);
		_createProductView.setStatisticIsLoading();
		_statisticDebounce++;
		final int curDebounce=_statisticDebounce;
		
		_clientFactory.getEventBus().fireEvent(new InfoBoxDestroyEvent(CreateProductActivity.class));
		
		final InfoBoxShowEvent loadingInfo = new InfoBoxShowEvent(CreateProductActivity.class, "Getting statistic data... ", INFOTYPE.INFO,0);
		_clientFactory.getEventBus().fireEvent(loadingInfo);

		
		
		_clientFactory.getSearchService().searchProductPrices(_product.getId(), bbox, begin, end, new AsyncCallback<List<StatisticResult>>() {

			@Override
			public void onSuccess(List<StatisticResult> response) {
				if(curDebounce==_statisticDebounce){
					_clientFactory.getEventBus().fireEvent(new InfoBoxDestroyEvent(loadingInfo));
					
					for(int i=0;i<response.size();i++){
						if(response.get(i).getProduct()==null)
							response.get(i).setProduct(_product);
					}
						
					_createProductView.setStatisticResults(response);
				}
			}

			@Override
			public void onFailure(Throwable e) {
				_clientFactory.getEventBus().fireEvent(new InfoBoxDestroyEvent(CreateProductActivity.class, INFOTYPE.INFO));
				Log.error("searchproblem: "+e);
			}
		});

	}

	@Override
	public void onCategoryClicked(String categoryId) {
		goTo(new ProductCategoryPlace(
				categoryId, 
				null, 
				_place.getLat(), 
				_place.getLon(), 
				_place.getZoom()));
		
	}

	@Override
	public void onEditEvent() {
		_createProductView.setReadOnly(false);
		if(!_clientFactory.getAccountPersistor().isLoggedIn())
			_clientFactory.getEventBus().fireEvent(new DisplayLoginEvent(LoginType.login));
		
		
	}

}
