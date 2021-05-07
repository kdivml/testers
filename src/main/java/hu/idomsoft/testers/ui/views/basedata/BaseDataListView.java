package hu.idomsoft.testers.ui.views.basedata;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import hu.idomsoft.testers.data.BaseData;
import hu.idomsoft.testers.data.managers.BaseDataManager;
import hu.idomsoft.testers.ui.MainLayout;

@Route(value = "basedata", layout = MainLayout.class)
@PageTitle("Testers | BaseData")
public class BaseDataListView extends VerticalLayout {

	private final BaseDataForm form;
	Grid<BaseData> baseDataGrid = new Grid<>();
	Button addButton = new Button("Hozzáadás", VaadinIcon.PLUS.create(),click -> addBaseData());
	
	public BaseDataListView() {
		addClassName("list-view");
		setSizeFull();
		configureGrid();
		
		form = new BaseDataForm();
		form.addListener(BaseDataForm.SaveEvent.class, this::save);
		form.addListener(BaseDataForm.DeleteEvent.class, this::delete);
		form.addListener(BaseDataForm.CloseEvent.class, e -> closeEditor());
		
		Div content = new Div(baseDataGrid, form);
		content.addClassName("content");
		content.setSizeFull();
		
		add(addButton, content);
		updateList();
		closeEditor();
	}
	
	private void configureGrid() {
		baseDataGrid.addClassName("list");
		baseDataGrid.setSizeFull();
		baseDataGrid.addColumn(BaseData::getName).setHeader("Név").setSortable(true);
		
		baseDataGrid.asSingleSelect().addValueChangeListener(evt -> editBaseData(evt.getValue()));
	}
	
	private void updateList() {
		baseDataGrid.setItems(BaseDataManager.getBaseDataList());
	}
	
	private void save(BaseDataForm.SaveEvent evt) {
		if(evt.getBaseData().getId() == null) {
			BaseDataManager.insertBaseData(evt.getBaseData());
		}
		else {
			BaseDataManager.updateBaseData(evt.getBaseData());
		}
		
		updateList();
		closeEditor();
	}
	
	private void delete(BaseDataForm.DeleteEvent evt) {
		BaseDataManager.deleteBaseData(evt.getBaseData());
		
		updateList();
		closeEditor();
	}
	
	private void editBaseData(BaseData baseData) {
		if(baseData == null) {
			closeEditor();
		}
		else {
			form.setData(baseData);
			form.setVisible(true);
			addClassName("editing");
		}
	}
	
	private void closeEditor() {
		form.setData(null);
		form.setVisible(false);
		removeClassName("editing");
		baseDataGrid.deselectAll();
	}
	
	private void addBaseData() {
		baseDataGrid.asSingleSelect().clear();
		editBaseData(new BaseData());
	}
}
