package net.msembodo.pwdvault;

import java.security.NoSuchAlgorithmException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import net.msembodo.pwdvault.api.dao.UserDao;
import net.msembodo.pwdvault.api.model.User;
import net.msembodo.pwdvault.api.response.LoginResponse;
import net.msembodo.pwdvault.api.service.CryptoService;
import net.msembodo.pwdvault.service.Common;

import com.vaadin.ui.Button.ClickEvent;

@SuppressWarnings("serial")
@SpringView(name = LoginPage.NAME)
public class LoginPage extends VerticalLayout implements View {
	
	public static final String NAME = "login";
	
	@Autowired
	private Common impl;
	
	@Autowired
	private CryptoService cryptoService;
	
	@Autowired
	private UserDao userDao;
	
	@PostConstruct
	void init() {
		Panel panel = new Panel("Password Vault Login");
		panel.setSizeUndefined();
		addComponent(panel);
		
		FormLayout form = new FormLayout();
		TextField txtEmail = new TextField("E-mail");
		form.addComponent(txtEmail);
		PasswordField pwdPass = new PasswordField("Password");
		form.addComponent(pwdPass);
		
		Button btnSubmit = new Button("Enter");
		btnSubmit.setClickShortcut(KeyCode.ENTER);
		btnSubmit.addStyleName(ValoTheme.BUTTON_PRIMARY);
		btnSubmit.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					LoginResponse loginResponse = impl.login(txtEmail.getValue(), pwdPass.getValue());
					
					if (loginResponse.isLoginSuccess()) {
						getSession().setAttribute("sessionKey", loginResponse.getSessionId());
						getSession().setAttribute("name", loginResponse.getVaultUser().getName());
						getUI().getNavigator().navigateTo(ListActivity.NAME);
						
					} else
						Notification.show(loginResponse.getMessage(), Notification.Type.ERROR_MESSAGE);
					
				} catch (Exception e) {
					Notification.show(e.toString(), Notification.Type.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		});
		Button btnSignup = new Button("Sign up");
		btnSignup.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// show registration sub-window
				Window wdwSignup = new Window("Sign up");
				VerticalLayout lytSignup = new VerticalLayout();
				FormLayout frmSignup = new FormLayout();
				frmSignup.setSizeUndefined();
				frmSignup.setMargin(true);
				
				TextField txtEmail = new TextField("E-mail");
				TextField txtName = new TextField("Name");
				PasswordField pwdPassword1 = new PasswordField("Password");
				PasswordField pwdPassword2 = new PasswordField("Confirm password");
				Button btnSubmit = new Button("Submit");
				frmSignup.addComponents(txtEmail, txtName, pwdPassword1, pwdPassword2, btnSubmit);
				btnSubmit.addClickListener(new Button.ClickListener() {
					
					@Override
					public void buttonClick(ClickEvent event) {
						if (txtEmail.getValue().equals("") || txtName.getValue().equals("") || pwdPassword1.getValue().equals("")) {
							Notification.show("Fields can not be empty", Notification.Type.ERROR_MESSAGE);
						} else {
							// check for availability
							if (userDao.findByEmail(txtEmail.getValue()) != null) {
								Notification.show("User already exist", Notification.Type.ERROR_MESSAGE);
							} else {
								// check if password1 & password2 match
								if (pwdPassword1.getValue().equals(pwdPassword2.getValue())) {
									// save new user to database with hashed password
									try {
										User user = new User(txtEmail.getValue(), txtName.getValue(), 
												cryptoService.hash(pwdPassword1.getValue()).toUpperCase());
										userDao.save(user);
										wdwSignup.close();
										Notification.show("Registration success");
										
									} catch (NoSuchAlgorithmException e) {
										Notification.show(e.toString(), Notification.Type.ERROR_MESSAGE);
									}
									
								} else {
									Notification.show("Password 1 & password 2 don't match", Notification.Type.ERROR_MESSAGE);
								}
							}
						}
					}
				});
				
				lytSignup.addComponent(frmSignup);
				wdwSignup.setContent(lytSignup);
				wdwSignup.center();
				getUI().addWindow(wdwSignup);
			}
		});
		
		HorizontalLayout hBox1 = new HorizontalLayout();
		hBox1.addComponents(btnSubmit, btnSignup);
		
		form.addComponent(hBox1);
		form.setSizeUndefined();
		form.setMargin(true);
		panel.setContent(form);
		setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		if (getSession().getAttribute("sessionKey") != null) {
			getUI().getNavigator().navigateTo(ListActivity.NAME);
		}
	}

}
