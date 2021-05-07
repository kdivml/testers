package hu.idomsoft.testers.ui.views.prefix;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import hu.idomsoft.testers.data.BaseData;
import hu.idomsoft.testers.data.Prefix;
import hu.idomsoft.testers.data.managers.BaseDataManager;
import hu.idomsoft.testers.data.managers.PrefixManager;
import hu.idomsoft.testers.ui.MainLayout;

@Route(value = "prefix", layout = MainLayout.class)
@PageTitle("Testers | Prefix")
public class PrefixView extends VerticalLayout {
	
	ComboBox<BaseData> cbBaseData = new ComboBox<>("BaseData:");
	HorizontalLayout selectorSectionLayout = new HorizontalLayout();
	List<Prefix> displayedLinkedPrefixList = new ArrayList<>();
	List<Prefix> displayedFreePrefixList = new ArrayList<>();
	List<Prefix> originalLinkedPrefixList = new ArrayList<>();
	List<Prefix> originalFreedPrefixList = new ArrayList<>();
	Grid<Prefix> freePrefixGrid = new Grid<>();
	Grid<Prefix> linkedPrefixGrid = new Grid<>();
	VerticalLayout btnLayout = new VerticalLayout();
	Button btnLink = new Button(VaadinIcon.ARROW_LEFT.create());
	Button btnFree = new Button(VaadinIcon.ARROW_RIGHT.create());
	Button btnSave = new Button("Mentés");

	public PrefixView() {
		addClassName("prefix-selector");
		setSizeFull();
		initComboBox();
		configureSelectors();
		updateSelectorsFromDataBase();
		
		btnLink.addThemeVariants(ButtonVariant.LUMO_LARGE);
		btnLink.addClickListener(click -> linkPrefixes());
		btnFree.addThemeVariants(ButtonVariant.LUMO_LARGE);
		btnFree.addClickListener(click -> freePrefixes());
		btnSave.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btnSave.addClickListener(click -> save());
		
		btnLayout.addClassName("button-layout");
		btnLayout.add(btnLink, btnFree, btnSave);
		btnLayout.setAlignItems(Alignment.CENTER);
		
		selectorSectionLayout.addClassName("content");
		selectorSectionLayout.add(linkedPrefixGrid, btnLayout, freePrefixGrid);
		selectorSectionLayout.setSizeFull();
		selectorSectionLayout.setVerticalComponentAlignment(Alignment.CENTER, btnLayout);
		
		add(cbBaseData, selectorSectionLayout);
	}
	
	private void initComboBox() {
		List<BaseData> baseDatas = BaseDataManager.getBaseDataList();
		cbBaseData.setItemLabelGenerator(BaseData::getName);
		cbBaseData.setItems(baseDatas);
		cbBaseData.setValue(baseDatas.get(0));
		
		cbBaseData.addValueChangeListener(change -> updateSelectorsFromDataBase());
	}
	
	private void configureSelectors() {
		freePrefixGrid.addClassName("free-prefix-grid");
		freePrefixGrid.addColumn(Prefix::getPrefix).setHeader("Szabad prefixek:");
		freePrefixGrid.setSizeFull();
		freePrefixGrid.setSelectionMode(SelectionMode.MULTI);
		
		linkedPrefixGrid.addClassName("linked-prefix-grid");
		linkedPrefixGrid.addColumn(Prefix::getPrefix).setHeader("Ehhez a BaseDatahoz rendelve:");
		linkedPrefixGrid.setSizeFull();
		linkedPrefixGrid.setSelectionMode(SelectionMode.MULTI);
	}
	
	private void updateSelectorsFromDataBase() {
		originalFreedPrefixList = PrefixManager.getfreePrefixList();
		displayedFreePrefixList.clear();
		displayedFreePrefixList.addAll(originalFreedPrefixList);
		originalLinkedPrefixList = PrefixManager.getLinkedPrefixList(cbBaseData.getValue());
		displayedLinkedPrefixList.clear();
		displayedLinkedPrefixList.addAll(originalLinkedPrefixList);
		
		refreshSelectors();
	}
	
	private void refreshSelectors() {
		displayedFreePrefixList.sort(Comparator.comparing(Prefix::getPrefix));
		displayedLinkedPrefixList.sort(Comparator.comparing(Prefix::getPrefix));
		
		freePrefixGrid.setItems(displayedFreePrefixList);
		linkedPrefixGrid.setItems(displayedLinkedPrefixList);
	}
	
	private void freePrefixes() {
		displayedLinkedPrefixList.removeAll(linkedPrefixGrid.getSelectedItems());
		displayedFreePrefixList.addAll(linkedPrefixGrid.getSelectedItems());
		
		refreshSelectors();
	}
	
	private void linkPrefixes() {
		displayedFreePrefixList.removeAll(freePrefixGrid.getSelectedItems());
		displayedLinkedPrefixList.addAll(freePrefixGrid.getSelectedItems());
		
		refreshSelectors();
	}
	
	private void save() {
		displayedLinkedPrefixList.removeAll(originalLinkedPrefixList);
		if(!displayedLinkedPrefixList.isEmpty()) {
			PrefixManager.linkBaseDataToPrefix(displayedLinkedPrefixList, cbBaseData.getValue());
		}
		
		displayedFreePrefixList.removeAll(originalFreedPrefixList);
		if(!displayedFreePrefixList.isEmpty()) {
			PrefixManager.linkBaseDataToPrefix(displayedFreePrefixList, null);
		}
		
		updateSelectorsFromDataBase();
	}
}
