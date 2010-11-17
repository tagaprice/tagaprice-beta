package org.tagaprice.client.pages;

import java.util.ArrayList;

import org.tagaprice.client.RPCHandlerManager;
import org.tagaprice.client.widgets.TitleWidget;
import org.tagaprice.client.widgets.InfoBoxWidget.BoxType;
import org.tagaprice.client.widgets.TitleWidget.Level;
import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.Receipt;
import org.tagaprice.shared.exception.InvalidLoginException;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ReceiptListPage extends APage {

	private VerticalPanel vePa1 = new VerticalPanel();
	private Grid table;
	
	public ReceiptListPage() {
		init(vePa1);
		vePa1.setWidth("100%");
		
		
		
		try {
			RPCHandlerManager.getReceiptHandler().getUserReceipts(new AsyncCallback<ArrayList<Receipt>>() {
				
				@Override
				public void onSuccess(ArrayList<Receipt> result) {
					
					table = new Grid(result.size()+1,3);
					int c = 1;
					
					//head
					table.setWidth("100%");
					table.setText(0, 0, "Name");
					table.setText(0, 1, "Date");
					table.setText(0, 2, "Price [€]");
					
					long totalPrice = 0;
					
					//get Rows
					for(final Receipt pd: result){
						Label title = new Label(pd.getTitle()+": "+pd.getId());
						if(pd.getDraft())title.setText(title.getText()+" [DRAFT]");
						table.setWidget(c, 0, title);
						table.setText(c, 1, DateTimeFormat.getLongDateFormat().format(pd.getDate()));
						table.setText(c, 2, ""+(pd.getTotalPrice()/100.00));
						
						totalPrice=totalPrice+pd.getTotalPrice();
						
						title.addClickHandler(new ClickHandler() {							
							@Override
							public void onClick(ClickEvent event) {
								History.newItem("receipt/get&id="+pd.getId());							
							}
						});						
						c++;
					}
					
					
					vePa1.add(new TitleWidget("MyReceipts", table, Level.H2));
					
					vePa1.add(new Label("Total Price: "+(totalPrice/100.00)+"[€]"));
					
				}
				
				@Override
				public void onFailure(Throwable caught) {
					showInfo("getUserReceipts FAIL ", BoxType.WARNINGBOX);					
				}
			});
		} catch (IllegalArgumentException e) {
			showInfo("IllegalArgumentException: "+e, BoxType.WARNINGBOX);
		} catch (InvalidLoginException e) {
			showInfo("InvalidLoginException: "+e, BoxType.WARNINGBOX);

		}
	}

	@Override
	public void setAddress(Address address) {
		// TODO Auto-generated method stub
		
	}
}
