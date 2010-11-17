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
 * Filename: ProductHandler.java
 * Date: 27.05.2010
*/
package org.tagaprice.shared.rpc;

import org.tagaprice.shared.entities.Product;
import org.tagaprice.shared.exception.InvalidLoginException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpc/product")
public interface ProductHandler extends RemoteService {
	Product get(long id) throws IllegalArgumentException;
	Product save(Product data)  throws IllegalArgumentException, InvalidLoginException;
}
