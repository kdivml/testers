package hu.idomsoft.testers.ui.views.loginview;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("login")
@PageTitle("Testers | Login")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {
	
	private LoginForm loginForm = new LoginForm();
	
	public LoginView() {
		addClassName("login-view");
		setSizeFull();
		setAlignItems(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);
		
		loginForm.setAction("login");
		loginForm.setForgotPasswordButtonVisible(false);
		loginForm.setI18n(createHungarianI18n());
		
		add(new H1("Testers"), loginForm);
	}
	
	@Override
	public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
		if(beforeEnterEvent.getLocation().getQueryParameters().getParameters().containsKey("error")) {
			loginForm.setError(true);
		}
	}
	
	private LoginI18n createHungarianI18n() {
		final LoginI18n i18n = LoginI18n.createDefault();
		
		i18n.getForm().setTitle("");
		i18n.getForm().setUsername("Felhasználónév");
		i18n.getForm().setPassword("Jelszó");
		i18n.getForm().setSubmit("Bejelentkezés");
		i18n.getErrorMessage().setTitle("Hibás felhasználónév vagy jelszó!");
		i18n.getErrorMessage().setMessage("");
		
		return i18n;
	}
}
