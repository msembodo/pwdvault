package net.msembodo.pwdvault;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;

@SuppressWarnings("serial")
public class AddActivity extends MainVaultConsole implements View {
	
	public static final String NAME = "main/add";
	
	@Override
	void init() {
		super.init();
		
		panel.setCaption("Add vault entry");
		
		FormLayout frmAdd = new FormLayout();
		TextField txtAccountName = new TextField("Account name");
		TextField txtAccountType = new TextField("Account type");
		TextField txtDescription = new TextField("Description");
		TextField txtPassword = new TextField("Password");
		frmAdd.addComponents(txtAccountName, txtAccountType, txtDescription, txtPassword);
		
		Button btnSubmit = new Button("Submit");
		frmAdd.addComponent(btnSubmit);
		frmAdd.setSizeUndefined();
		frmAdd.setMargin(true);
		
		vBox.addComponent(frmAdd);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		if (getSession().getAttribute("sessionKey") == null) {
			getUI().getNavigator().navigateTo(LoginPage.NAME);
		} 
	}

}
