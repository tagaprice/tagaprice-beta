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
 * Filename: RegistrationHandlerAsync.java
 * Date: 07.07.2010
*/
package org.tagaprice.shared.rpc;

import org.tagaprice.shared.entities.Address;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LocalAccountHandlerAsync {
	void checkMailAvailability(String email, AsyncCallback<Boolean> callback)
		throws IllegalArgumentException;

	void registerNewUser(
			String password,
			String confirmPassword, 
			String email, 
			boolean gtc,
			AsyncCallback<Boolean> callback) 
		throws IllegalArgumentException;
	
	void loginDeprecated(String mail, String password, AsyncCallback<String> callback)
		throws IllegalArgumentException;
	
	void getId(AsyncCallback<Long> callback) throws IllegalArgumentException;
	
	void logout(AsyncCallback<Boolean> callback) throws IllegalArgumentException;
	
	void confirm(String confirm, AsyncCallback<Boolean> callback)throws IllegalArgumentException;

}
