package net.msembodo.pwdvault;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.MenuBar.MenuItem;

import net.msembodo.pwdvault.api.model.VaultAccountType;
import net.msembodo.pwdvault.api.response.DeleteTokenResponse;
import net.msembodo.pwdvault.api.response.ListResponse;
import net.msembodo.pwdvault.api.response.LogoutResponse;
import net.msembodo.pwdvault.api.response.RetrieveTokenResponse;
import net.msembodo.pwdvault.api.response.TokenizeResponse;
import net.msembodo.pwdvault.api.response.UpdateTokenResponse;

@SuppressWarnings("serial")
@SpringView(name = ListActivity.NAME)
public class ListActivity extends MainVaultConsole implements View {
	
	public static final String NAME = "main/list";
		
	@Override
	void init() {
		super.init();
	}

	@Override
	public void enter(ViewChangeEvent event) {
		if (getSession().getAttribute("sessionKey") == null) {
			getUI().getNavigator().navigateTo(LoginPage.NAME);
		} else {
			// *** menu bar ***
			userName = (String) getSession().getAttribute("name");
			userAction = menuBar.addItem(userName, null);
			
			// menu items
			userLogout = userAction.addItem("Logout", new MenuBar.Command() {
				
				@Override
				public void menuSelected(MenuItem selectedItem) {
					try {
						String sessionKey = (String) getSession().getAttribute("sessionKey");
						LogoutResponse logoutResponse = impl.logout(sessionKey); // call logout API
						
						if (logoutResponse.isLogoutSuccess()) {
							Notification.show(logoutResponse.getMessage());
							getSession().setAttribute("sessionKey", null);
							getSession().setAttribute("name", null);
							getUI().getNavigator().navigateTo(LogoutPage.NAME);
							
						} else
							Notification.show(logoutResponse.getMessage(), Notification.Type.ERROR_MESSAGE);
						
					} catch (Exception e) {
						Notification.show(e.toString(), Notification.Type.ERROR_MESSAGE);
					}					
				}
			});
			userDeactivate = userAction.addItem("Deactivate*", null);
			
			// *** body part ***
			try {
				String sessionKey = (String) getSession().getAttribute("sessionKey");
				ListResponse listResponse = impl.list(sessionKey); // call list API
				
				// show action buttons for each account type
				if (listResponse.isListSuccess()) {
					List<VaultAccountType> accountTypes = listResponse.getAccountTypes();
					
					List<Button> viewButtons = new ArrayList<>();
					List<Button> deleteButtons = new ArrayList<>();
					for (int i = 0; i < accountTypes.size(); i++) {
						VaultAccountType accountType = accountTypes.get(i);
						
						Button btnView = new Button("View");
						btnView.addClickListener(new Button.ClickListener() {
							
							@Override
							public void buttonClick(ClickEvent event) {
								// add sub-window for pin (and details if pin matches)
								Window dialogPin = new Window("Verify PIN for " + accountType.getAccountType());
								VerticalLayout layoutPin = new VerticalLayout();
								dialogPin.setContent(layoutPin);
								dialogPin.setResizable(false);
								dialogPin.center();
								
								FormLayout formPin = new FormLayout();
								PasswordField txtPin = new PasswordField("PIN");
								formPin.addComponent(txtPin);
								Button btnSubmitPin = new Button("OK");
								btnSubmitPin.addClickListener(new Button.ClickListener() {
									
									@Override
									public void buttonClick(ClickEvent event) {
										// display account details by calling retrieve API
										try {
											RetrieveTokenResponse retrieveTokenResponse = impl.retrieve(sessionKey, 
													accountType.getTokenId(), txtPin.getValue());
											if (retrieveTokenResponse.isRetrieveTokenSuccess()) {
												Window wdwDetails = new Window("Account details");
												GridLayout layoutDetails = new GridLayout(3, 4);
												layoutDetails.setMargin(true);
												layoutDetails.setSpacing(true);
												layoutDetails.addComponent(new Label("Account name"), 0, 0);
												layoutDetails.addComponent(new Label(":"), 1, 0);
												layoutDetails.addComponent(new Label(retrieveTokenResponse.getVaultData().getAccountName()), 2, 0);
												layoutDetails.addComponent(new Label("Account type"), 0, 1);
												layoutDetails.addComponent(new Label(":"), 1, 1);
												layoutDetails.addComponent(new Label(retrieveTokenResponse.getVaultData().getAccountType()), 2, 1);
												layoutDetails.addComponent(new Label("Description"), 0, 2);
												layoutDetails.addComponent(new Label(":"), 1, 2);
												layoutDetails.addComponent(new Label(retrieveTokenResponse.getVaultData().getDescription()), 2, 2);
												layoutDetails.addComponent(new Label("Password"), 0, 3);
												layoutDetails.addComponent(new Label(":"), 1, 3);
												layoutDetails.addComponent(new Label(retrieveTokenResponse.getVaultData().getPassword()), 2, 3);
												
												Button btnEdit = new Button("Edit");
												btnEdit.addClickListener(new Button.ClickListener() {
													
													@Override
													public void buttonClick(ClickEvent event) {
														// show edit account window
														wdwDetails.close();
														
														Window wdwEdit = new Window("Edit account");
														VerticalLayout layoutEdit = new VerticalLayout();
														FormLayout formEdit = new FormLayout();
														TextField txtAccountName = new TextField("Account name");
														txtAccountName.setValue(retrieveTokenResponse.getVaultData().getAccountName());
														formEdit.addComponent(txtAccountName);
														TextField txtAccountType = new TextField("Account type");
														txtAccountType.setValue(retrieveTokenResponse.getVaultData().getAccountType());
														formEdit.addComponent(txtAccountType);
														TextField txtDescription = new TextField("Description");
														txtDescription.setValue(retrieveTokenResponse.getVaultData().getDescription());
														formEdit.addComponent(txtDescription);
														TextField txtPassword = new TextField("Password");
														txtPassword.setValue(retrieveTokenResponse.getVaultData().getPassword());
														formEdit.addComponent(txtPassword);
														Button btnSubmitUpdate = new Button("Update");
														btnSubmitUpdate.addClickListener(new Button.ClickListener() {
															
															@Override
															public void buttonClick(ClickEvent event) {
																// update account by calling update API
																Notification.show("calling update API");
																try {
																	UpdateTokenResponse updateTokenResponse = impl.update(sessionKey, 
																			String.valueOf(accountType.getTokenId()), txtAccountName.getValue(), 
																			txtAccountType.getValue(), txtDescription.getValue(), txtPassword.getValue(), 
																			txtPin.getValue());
																	if (updateTokenResponse.isUpdateTokenSuccess()) {
																		wdwEdit.close();
																		Notification.show(accountType.getAccountType() + " is successfully updated");
																	} else {
																		wdwEdit.close();
																		Notification.show("Update fails", Notification.Type.ERROR_MESSAGE);
																	}
																	
																} catch (Exception e) {
																	wdwEdit.close();
																	Notification.show(e.toString(), Notification.Type.ERROR_MESSAGE);
																}
															}
														});
														formEdit.addComponent(btnSubmitUpdate);
														
														formEdit.setSizeUndefined();
														formEdit.setMargin(true);
														layoutEdit.addComponent(formEdit);
														wdwEdit.setContent(layoutEdit);
														wdwEdit.center();
														getUI().addWindow(wdwEdit);
													}
												});
												layoutDetails.addComponent(btnEdit);
												
												wdwDetails.setContent(layoutDetails);
												wdwDetails.center();
												getUI().addWindow(wdwDetails);
												
											} else
												Notification.show("PIN is incorrect", Notification.Type.ERROR_MESSAGE);
											
										} catch (Exception e) {
											Notification.show(e.toString(), Notification.Type.ERROR_MESSAGE);
										}
										
										dialogPin.close();
									}
								});
								formPin.addComponent(btnSubmitPin);
								formPin.setSizeUndefined();
								formPin.setMargin(true);
								layoutPin.addComponent(formPin);
								getUI().addWindow(dialogPin);
							}
						});
						viewButtons.add(btnView);
						
						Button btnDelete = new Button("Delete");
						btnDelete.addClickListener(new Button.ClickListener() {
							
							@Override
							public void buttonClick(ClickEvent event) {
								// add sub-window for delete confirmation
								Window dialogDelete = new Window("Confirm removal");
								dialogDelete.setResizable(false);
								dialogDelete.center();
								VerticalLayout layoutDelete = new VerticalLayout();
								layoutDelete.setMargin(true);
								dialogDelete.setContent(layoutDelete);
								
								layoutDelete.addComponent(new Label("Are you sure you want to delete " + accountType.getAccountType() + "?"));
								HorizontalLayout layoutDeleteOkCancel = new HorizontalLayout();
								layoutDelete.addComponent(layoutDeleteOkCancel);
								
								Button btnOkDelete = new Button("Delete");
								btnOkDelete.addClickListener(new Button.ClickListener() {
									
									@Override
									public void buttonClick(ClickEvent event) {
										dialogDelete.close();
										try {
											DeleteTokenResponse deleteTokenResponse = impl.delete(sessionKey, accountType.getTokenId());
											if (deleteTokenResponse.isDeleteTokenSuccess()) {
												Notification.show(accountType.getAccountType() + " is successfully deleted");
												getUI().getNavigator().navigateTo(ListActivity.NAME); // refresh page
												
											} else 
												Notification.show(deleteTokenResponse.getMessage(), Notification.Type.ERROR_MESSAGE);
											
										} catch (Exception e) {
											Notification.show(e.toString());
										}
									}
								});
								Button btnCancelDelete = new Button("Cancel");
								btnCancelDelete.addClickListener(new Button.ClickListener() {
									
									@Override
									public void buttonClick(ClickEvent event) {
										dialogDelete.close();
									}
								});
								layoutDeleteOkCancel.addComponents(btnCancelDelete, btnOkDelete);
								getUI().addWindow(dialogDelete);
							}
						});
						deleteButtons.add(btnDelete);
					}
					
					if (accountTypes.size() > 0) {
						GridLayout grid = new GridLayout(3, accountTypes.size());
						grid.setMargin(true);
						grid.setSpacing(true);
						for (int i = 0; i < accountTypes.size(); i++) {
							grid.addComponent(new Label(accountTypes.get(i).getAccountType()), 0, i);
							grid.addComponent(viewButtons.get(i), 1, i);
							grid.addComponent(deleteButtons.get(i), 2, i);
						}
						vBox.addComponent(grid);
					} else
						vBox.addComponent(new Label("No vault entry"));
					
					Button btnAddEntry = new Button("Add entry");
					btnAddEntry.addClickListener(new Button.ClickListener() {
						
						@Override
						public void buttonClick(ClickEvent event) {
							// show add entry dialog
							Window dialogAddEntry = new Window("New Password Vault entry");
							VerticalLayout layoutAddEntry = new VerticalLayout();
							layoutAddEntry.setMargin(true);
							layoutAddEntry.setSpacing(true);
							dialogAddEntry.setContent(layoutAddEntry);
							dialogAddEntry.center();
							
							FormLayout formAddEntry = new FormLayout();
							TextField txtAccountName = new TextField("Account name");
							formAddEntry.addComponent(txtAccountName);
							TextField txtAccountType = new TextField("Account type");
							formAddEntry.addComponent(txtAccountType);
							TextField txtDescription = new TextField("Description");
							formAddEntry.addComponent(txtDescription);
							TextField txtPassword = new TextField("Password");
							formAddEntry.addComponent(txtPassword);
							TextField txtPinCode = new TextField("PIN");
							formAddEntry.addComponent(txtPinCode);
							Button btnAddEntry = new Button("Submit");
							btnAddEntry.addClickListener(new Button.ClickListener() {
								
								@Override
								public void buttonClick(ClickEvent event) {
									// call tokenize API
									dialogAddEntry.close();
									try {
										TokenizeResponse tokenizeResponse = impl.add(sessionKey, txtAccountName.getValue(), 
												txtAccountType.getValue(), txtDescription.getValue(), txtPassword.getValue(), 
												txtPinCode.getValue());
										if (tokenizeResponse.isTokenizeSuccess()) {
											Notification.show(txtAccountName.getValue() + " has been added as new entry");
											getUI().getNavigator().navigateTo(ListActivity.NAME); // refresh page
										} else {
											Notification.show("Failed adding new entry", Notification.Type.ERROR_MESSAGE);
										}
										
									} catch (Exception e) {
										Notification.show(e.toString(), Notification.Type.ERROR_MESSAGE);
									}
								}
							});
							formAddEntry.addComponent(btnAddEntry);
							
							formAddEntry.setSizeUndefined();
							formAddEntry.setMargin(true);
							layoutAddEntry.addComponent(formAddEntry);
							getUI().addWindow(dialogAddEntry);
						}
					});
					addComponent(btnAddEntry);
					
				} else
					Notification.show(listResponse.getMessage(), Notification.Type.ERROR_MESSAGE);
				
			} catch (Exception e) {
				Notification.show(e.toString(), Notification.Type.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
	}

}
