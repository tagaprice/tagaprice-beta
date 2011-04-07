package org.tagaprice.client.features.shopmanagement.listShops.devView;

import java.util.ArrayList;

import org.tagaprice.client.generics.ColumnDefinition;
import org.tagaprice.shared.entities.shopmanagement.IShop;

import com.google.gwt.user.client.ui.*;

public class ShopColumnDefinitions {

	ArrayList<ColumnDefinition<IShop>> columns = new ArrayList<ColumnDefinition<IShop>>();

	public ShopColumnDefinitions() {
		columns.add(new ColumnDefinition<IShop>() {

			@Override
			public Widget render(IShop t) {
				return new HTML(t.getTitle());
			}

			@Override
			public String getColumnName() {
				return "Title";
			}
		});
		this.columns.add(new ColumnDefinition<IShop>() {

			@Override
			public Widget render(IShop t) {

				return new Anchor("Open/Edit", "#CreateShop:/show/id/" + t.getId());

			}

			@Override
			public String getColumnName() {
				// TODO Auto-generated method stub
				return "Action";
			}
		});
		this.columns.add(new ColumnDefinition<IShop>() {

			@Override
			public Widget render(IShop t) {
				if(t.getId() != null)
					return new HTML(t.getId());
				else
					return new HTML("Revision is null");
			}

			@Override
			public String getColumnName() {
				return "Delete";
			}
		});

	}

	public ArrayList<ColumnDefinition<IShop>> getColumnDefinitions() {
		return this.columns;
	}
}
