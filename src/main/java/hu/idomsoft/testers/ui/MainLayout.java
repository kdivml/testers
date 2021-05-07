package hu.idomsoft.testers.ui;

import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;

import hu.idomsoft.testers.ui.views.basedata.BaseDataListView;
import hu.idomsoft.testers.ui.views.overviewlist.OverviewListView;
import hu.idomsoft.testers.ui.views.prefix.PrefixView;
import hu.idomsoft.testers.ui.views.systemtree.SystemTreeView;

@CssImport("./styles/shared-styles.css")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class MainLayout extends AppLayout {
	public MainLayout() {
		createHeader();
		createDrawer();
	}
	
	private void createHeader() {
		H1 logo = new H1("Testers");
		logo.addClassName("logo");
		
		Button logoutButton = new Button("Kijelentkezés",VaadinIcon.SIGN_OUT.create(), e -> logout());
		
		HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, logoutButton);
		header.addClassName("header");
		header.setWidth("100%");
		header.expand(logo);
		header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
		
		addToNavbar(header);
	}
	
	private void createDrawer() {
		VerticalLayout drawerLayout = new VerticalLayout();
		drawerLayout.setPadding(false);
		drawerLayout.setSpacing(false);
		drawerLayout.setSizeFull();
		drawerLayout.setAlignItems(Alignment.START);
		drawerLayout.addClassName("drawer");

		H1 categoryModificationsTitle = new H1("Módosítások");
		categoryModificationsTitle.addClassName("menu-category-title");
		
		Button systemTreeViewButton = new Button("Alrendszer - Tesztelő, BaseData");
		systemTreeViewButton.setWidthFull();
		systemTreeViewButton.addClickListener( click -> UI.getCurrent().navigate(SystemTreeView.class));
		
		Button baseDataButton = new Button("BaseData kezelés");
		baseDataButton.setWidthFull();
		baseDataButton.addClickListener( click -> UI.getCurrent().navigate(BaseDataListView.class));

		
		Button prefixLinkingButton = new Button("BaseData - Prefix");
		prefixLinkingButton.setWidthFull();
		prefixLinkingButton.addClickListener( click -> UI.getCurrent().navigate(PrefixView.class));
		
		H1 categoryListsTitle = new H1("Listák");
		categoryListsTitle.addClassName("menu-category-title");
		
		Button overviewButton = new Button("Áttekintés");
		overviewButton.setWidthFull();
		overviewButton.addClickListener( click -> UI.getCurrent().navigate(OverviewListView.class));
		
		drawerLayout.add(
			categoryModificationsTitle,
			systemTreeViewButton,
			baseDataButton,
			prefixLinkingButton,
			categoryListsTitle,
			overviewButton
		);
		
		addToDrawer(drawerLayout);
	}
	
	private void logout() {
		SecurityContextHolder.clearContext();
		UI.getCurrent().getSession().close();
		UI.getCurrent().getPage().reload();
	}
}
