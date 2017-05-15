package net.msembodo.pwdvault;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@SpringView(name = LogoutPage.NAME)
public class LogoutPage extends VerticalLayout implements View {
	
	public static final String NAME = "logout";

	@Override
	public void enter(ViewChangeEvent event) {
		getUI().getNavigator().navigateTo(ListActivity.NAME);
	}

}
