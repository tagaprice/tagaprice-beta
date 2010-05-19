/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License. 
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
*/

/**
 * Project: TagAPriceUI
 * Filename: SelectiveVerticalPanel.java
 * Date: 14.05.2010
*/
package org.tagaprice.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Vertical panel with edit buttons.
 *
 */
public class SelectiveVerticalPanel extends Composite {

	public static final String PLUSBUTTON = "PLUSBUTTON"; 
	public static final String MINUSBUTTON = "MINUSBUTTON"; 
	
	VerticalPanel vePa1 = new VerticalPanel();
	ImageResource topImage;
	ImageResource bottomImage;
	SelectiveVerticalPanelHandler externalHandler;
	
	/**
	 * 
	 * @param selectionType Is the ImageType (e.g. SelectiveVerticalPanel.PLUSBUTTON)
	 */
	public @UiConstructor SelectiveVerticalPanel(String selectionType) {
		initWidget(vePa1);
		vePa1.setWidth("100%");
		
		if(selectionType.equals(PLUSBUTTON)){
			topImage=(MyResources.INSTANCE.plusActive());
			bottomImage=(MyResources.INSTANCE.plusInactive());
		}else if(selectionType.equals(MINUSBUTTON)){
			topImage=(MyResources.INSTANCE.minusActive());
			bottomImage=(MyResources.INSTANCE.minusInactive());
		}
		
	}
	
	/**
	 * Adds a new child widget to the panel.
	 * @param w
	 */
	public void add(final Widget w){
		final HorizontalPanel hoPa = new HorizontalPanel();
		PushButton puBa = new PushButton(new Image(topImage), new Image(bottomImage));
		hoPa.setWidth("100%");
		
		
		//insert Button
		hoPa.add(puBa);
		hoPa.setCellWidth(puBa, MyResources.INSTANCE.minusActive().getWidth()+"px");
		hoPa.setCellVerticalAlignment(puBa, HasVerticalAlignment.ALIGN_MIDDLE);
		hoPa.add(w);
		vePa1.add(hoPa);
		
		
		//handler
		puBa.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				if(externalHandler!=null)
					externalHandler.onClick(w,vePa1.getWidgetIndex(hoPa));
				
			}
		});
	}
	
	/**
	 * 
	 * @param handler
	 */
	public void addSelectiveVerticalPanelHandler(SelectiveVerticalPanelHandler handler){
		externalHandler=handler;
	}
	
	
	/**
	 * 
	 * @param index
	 */
	public void removeWidget(int index){
		vePa1.remove(index);
	}
	
	
	/**
	 * 
	 * @return
	 */
	public int getWidgetCount(){
		return vePa1.getWidgetCount();
	}
	
	/**
	 * 
	 * @param index
	 * @return
	 */
	public Widget getWidget(int index){
		return ((HorizontalPanel)vePa1.getWidget(index)).getWidget(1);
	}
}