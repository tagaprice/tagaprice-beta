package org.tagaprice.client.features.accountmanagement.register;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface IRegisterView extends IsWidget {

	/**
	 * Sets the {@link Presenter} which implements the {@link IRegisterView} to control this view. It is also necessary
	 * for the {@link IRegisterView} to communicate with the {@link Presenter}
	 * 
	 * @param presenter
	 *            Sets the {@link Presenter} which implements the {@link IRegisterView} to control this view.
	 */
	public void setPresenter(Presenter presenter);

	/**
	 * Email of the user
	 * 
	 * @return loginMail
	 */
	public String getEmail();

	/**
	 * Informs the View that this email is free or in use.
	 * 
	 * @param inUse
	 *            Informs the View that this email is free or in use. TRUE=Free
	 */
	public void setEmailIsFree(boolean inFree);

	/**
	 * Returns the Password
	 * 
	 * @return the password
	 */
	public String getPassword();

	/**
	 * Returns the Confirm Password. Must be the same as Password <a href="http://code.google.com/apis/recaptcha/docs/display.html" >ReCaptcha
	 * Docu</a>
	 * 
	 * @return the Confirm Password. Must be the same as Password
	 */
	public String getConfirmPassword();

	/**
	 * Returns the ReCaptcha Challenge. <a href="http://code.google.com/apis/recaptcha/docs/display.html" >ReCaptcha
	 * Docu</a>
	 * 
	 * @return the ReCaptcha Challenge
	 */
	public String getChallenge();

	/**
	 * Returns the ReCaptcha Response.
	 * 
	 * @return the ReCaptcha Response.
	 */
	public String getResponse();

	/**
	 * Returns true if user agree the Terms and Conditions
	 * @return true if user agree the Terms and Conditions
	 */
	public boolean getAgreeTerms();

	public interface Presenter {

		/**
		 * Is used by the {@link org.tagaprice.client.mvp.AppActivityMapper} to display a new place in the
		 * browser window.
		 * 
		 * @param place
		 *            The {@link Place} which should be displayed next.
		 */
		public void goTo(Place place);


		/**
		 * Tell the implementing class to control if the email address is free.
		 */
		public void checkEmail();


		public void onRegisterButtonEvent();

	}
}