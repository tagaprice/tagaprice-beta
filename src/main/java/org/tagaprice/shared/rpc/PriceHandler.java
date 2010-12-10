/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License. 
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
*/

/**
 * Project: TagAPrice
 * Filename: PriceHandler.java
 * Date: 02.06.2010
*/
package org.tagaprice.shared.rpc;

import java.util.ArrayList;

import org.tagaprice.shared.entities.PriceData;
import org.tagaprice.shared.enums.PriceMapType;
import org.tagaprice.shared.utility.BoundingBox;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpc/price")
public interface PriceHandler extends RemoteService {
	/**
	 * 
	 * @param id
	 * @param bbox
	 * @param type
	 * @return
	 */
	ArrayList<PriceData> get(long id, BoundingBox bbox, PriceMapType type)throws IllegalArgumentException;
}