package org.tagaprice.client.features.accountmanagement.login.desktopView;

import org.tagaprice.client.features.accountmanagement.login.ILoginView;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LoginViewImpl extends Composite implements ILoginView {

	private SimplePanel panel = new SimplePanel();
	private VerticalPanel vePaSignInUp = new VerticalPanel();
	private TextBox email = new TextBox();
	private PasswordTextBox password = new PasswordTextBox();
	private Button signInButton = new Button("Log in");
	private Button signUpButton = new Button("Sign up!");
	private Presenter _presenter;
	private RadioButton signInRadio = new RadioButton("blabla","I have an account.");
	private RadioButton signUpRadio = new RadioButton("blabla","I'm new!");
	private HTML passwordForgotText = new HTML("<a >Forgot your password?</a>");
	private HTML terms = new HTML("By clicking 'Sign up!' above, you confirm that you accept the <a>terms of service</a>.");

	public LoginViewImpl() {
		initWidget(panel);
		panel.setWidth("200px");
		setStyleName("loginView");
		
		//We have to add hander here because of multible fire events
		signInButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				_presenter.onLoginEvent();				
			}
		});
		
		signUpButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				_presenter.onRegisterButtonEvent();				
			}
		});
		
		password.addKeyDownHandler(new KeyDownHandler() {
			
			@Override
			public void onKeyDown(KeyDownEvent key) {
				if(key.getNativeKeyCode() == 13){
					if(signInRadio.getValue())
						_presenter.onLoginEvent();
					else if(signUpRadio.getValue())
						_presenter.onRegisterButtonEvent();
				}
				
			}
		});
	}
	
	@Override
	public void setPresenter(Presenter presenter) {
		_presenter=presenter;		
	}

	@Override
	public String getEmail() {
		return email.getText();
	}

	@Override
	public String getPassword() {
		return password.getText();
	}

	@Override
	public void showWaitForConfirmation() {
		VerticalPanel conWait = new VerticalPanel();
		conWait.add(new Label("We have just sent you an confirmation mail. Please klick the link in this mail to finish you registration."));
		conWait.add(new Label("..."));
		panel.setWidget(conWait);
		
	}


	@Override
	public void showSignInUp(boolean showSingIn) {
		vePaSignInUp.clear();
		
		//facebook
		Image fb = new Image("desktopView/fb-login-button.png");
		fb.setStyleName("loginView-pic");
		vePaSignInUp.add(fb);
		
		//twitter
		Image tw = new Image("https://si0.twimg.com/images/dev/buttons/sign-in-with-twitter-l.png");
		vePaSignInUp.add(tw);
		vePaSignInUp.add(new HTML("<br />"));
		
		//Username
		Label emailText = new Label("Email");
		Label passwordText = new Label("Password");
		
		vePaSignInUp.add(emailText);
		vePaSignInUp.add(email);
		
		//radio
		vePaSignInUp.add(signInRadio);
		vePaSignInUp.add(signUpRadio);
		
		
		vePaSignInUp.add(passwordText);
		vePaSignInUp.add(password);
		
		//sign in button
		
		vePaSignInUp.add(signInButton);
		vePaSignInUp.add(signUpButton);
		signUpButton.setVisible(false);
		
		
		
		//forgot Password
		vePaSignInUp.add(passwordForgotText);
		vePaSignInUp.add(terms);
		terms.setVisible(false);

		
		
		//sign in/up select
		signInRadio.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> value) {
				if(value.getValue()){
					signInButton.setVisible(true);
					signUpButton.setVisible(false);
					passwordForgotText.setVisible(true);
					terms.setVisible(false);
				}
			}
		});
		
		signUpRadio.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> value) {
				if(value.getValue()){
					signUpButton.setVisible(true);
					signInButton.setVisible(false);
					passwordForgotText.setVisible(false);
					terms.setVisible(true);
				}				
			}
		});		
		
		if(showSingIn){
			signInRadio.setValue(true);
			signInButton.setVisible(true);
			signUpButton.setVisible(false);
			passwordForgotText.setVisible(true);
			terms.setVisible(false);
		}else{
			signUpRadio.setValue(true);
			signUpButton.setVisible(true);
			signInButton.setVisible(false);
			passwordForgotText.setVisible(false);
			terms.setVisible(true);
		}
		
		panel.setWidget(vePaSignInUp);
	}

}
