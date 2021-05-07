package hu.idomsoft.testers.ui.views.overviewlist;

import java.util.List;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import hu.idomsoft.testers.data.OverviewData;
import hu.idomsoft.testers.data.SubSystem;
import hu.idomsoft.testers.data.managers.OverviewDataManager;
import hu.idomsoft.testers.data.managers.SubSystemManager;
import hu.idomsoft.testers.ui.MainLayout;
import hu.idomsoft.testers.ui.views.systemtree.SubSystemForm;

@Route(value = "overviewlist", layout = MainLayout.class)
@PageTitle("Testers | Áttekintő lista")
public class OverviewListView extends  VerticalLayout{

	private final SubSystemForm form;
	Grid<OverviewData> overviewGrid = new Grid<>();
	
	public OverviewListView() {
		addClassName("list-view");
		setSizeFull();
		configureGrid();
		
		form = new SubSystemForm();
		form.addListener(SubSystemForm.SaveEvent.class, this::save);
		form.addListener(SubSystemForm.CloseEvent.class, e -> closeEditor());
		
		Div content = new Div(overviewGrid, form);
		content.addClassName("content");
		content.setSizeFull();
		
		add(content);
		updateList();
		closeEditor();
	}
	
	private void configureGrid() {
		overviewGrid.addClassName("list");
		overviewGrid.setSizeFull();
		overviewGrid.addColumn(OverviewData::getSystemName).setHeader("Rendszer").setSortable(true);
		overviewGrid.addColumn(OverviewData::getName).setHeader("Alrendszer").setSortable(true);
		overviewGrid.addColumn(OverviewData::getTesterName).setHeader("Tesztelő").setSortable(true);
		overviewGrid.addColumn(OverviewData::getBaseDataName).setHeader("BaseData").setSortable(true);
		
		overviewGrid.asSingleSelect().addValueChangeListener(evt -> editSubSystem(evt.getValue()));
	}
	
	private void updateList() {
		List<OverviewData> dataList = OverviewDataManager.getOverviewDataList();
		
		for(OverviewData data : dataList) {
			if(data.getTesterName() == null) {
				data.setTesterName("<Nincs hozzárendelve>");
			}
			if(data.getBaseDataName() == null) {
				data.setBaseDataName("<Nincs hozzárendelve>");
			}
		}
		
		overviewGrid.setItems(dataList);
	}
	
	private void save(SubSystemForm.SaveEvent evt) {
		SubSystemManager.updateSubSystem(evt.getSubSystem());
		
		updateList();
		closeEditor();
	}
	
	private void editSubSystem(OverviewData overviewData) {
		if(overviewData == null) {
			closeEditor();
		}
		else {
			SubSystem subSystem = SubSystemManager.getSubSystemBindings(overviewData.getId());
			form.setData(subSystem);
			form.setVisible(true);
			addClassName("editing");
		}
	}
	
	private void closeEditor() {
		form.setData(null);
		form.setVisible(false);
		removeClassName("editing");
		overviewGrid.deselectAll();
	}
}
