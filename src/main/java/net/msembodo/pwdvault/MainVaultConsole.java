package net.msembodo.pwdvault;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.MenuBar.MenuItem;

import net.msembodo.pwdvault.service.Common;

@SuppressWarnings("serial")
public class MainVaultConsole extends VerticalLayout implements View {
	
	public static final String NAME = "main";
	
	@Autowired
	protected Common impl;
	
	protected String userName;
	protected MenuItem userAction;
	protected MenuItem userLogout;
	protected MenuItem userDeactivate;
	protected MenuBar menuBar;
	protected Panel panel;
	protected VerticalLayout vBox;
	
	@PostConstruct
	void init() {
		menuBar = new MenuBar();
		menuBar.setSizeUndefined();
		menuBar.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
		addComponent(menuBar);
		
		panel = new Panel("Password Vault entries");
		addComponent(panel);
		panel.setSizeUndefined();
		vBox = new VerticalLayout();
		vBox.setMargin(true);
		panel.setContent(vBox);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		if (getSession().getAttribute("sessionKey") == null) {
			getUI().getNavigator().navigateTo(LoginPage.NAME);
		} else {
			getUI().getNavigator().navigateTo(ListActivity.NAME);
		}
	}

}
