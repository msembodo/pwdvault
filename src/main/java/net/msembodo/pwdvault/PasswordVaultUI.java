package net.msembodo.pwdvault;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@SpringUI
@Theme("valo")
public class PasswordVaultUI extends UI {
	
	@Autowired
	private SpringNavigator navigator;
	
	@Autowired
	private SpringViewProvider viewProvider;
					
	@Override
	protected void init(VaadinRequest request) {
		navigator.init(this, this);
		navigator.addProvider(viewProvider);
		setNavigator(navigator);
		
		getNavigator().navigateTo(ListActivity.NAME);
	}

}
