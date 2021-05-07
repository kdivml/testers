package hu.idomsoft.testers.ui.views.systemtree;

import java.util.ArrayList;
import java.util.List;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import hu.idomsoft.testers.data.SubSystem;
import hu.idomsoft.testers.data.System;
import hu.idomsoft.testers.data.managers.SubSystemManager;
import hu.idomsoft.testers.data.managers.SystemManager;
import hu.idomsoft.testers.ui.MainLayout;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Testers | Rendszerfa")
public class SystemTreeView extends VerticalLayout {
	
	private final SubSystemForm form;
	TreeGrid<System> systemTree = new TreeGrid<>(System.class);
	
	public SystemTreeView() {
		addClassName("list-view");
		setSizeFull();
		configureTree();
		
		form = new SubSystemForm();
		form.addListener(SubSystemForm.SaveEvent.class, this::save);
		form.addListener(SubSystemForm.CloseEvent.class, e -> closeEditor());
		
		Div content = new Div(systemTree, form);
		content.addClassName("content");
		content.setSizeFull();
		
		add(content);
		closeEditor();
	}
	
	private void configureTree() {
		systemTree.addClassName("list");
		systemTree.setSizeFull();
		systemTree.setHierarchyColumn("name").setHeader("Név");
		systemTree.removeColumnByKey("parent");
		systemTree.removeColumnByKey("id");
	
		getTreeData().forEach(o -> systemTree.getTreeData().addItem(o.getParent(), o));
		systemTree.asSingleSelect().addValueChangeListener(evt -> {
			if(evt.getValue() == null) {
				closeEditor();
			}
			else {
				if(evt.getValue().getParent() != null) {
					editSubSystem(SubSystemManager.getSubSystemBindings(evt.getValue().getId()));
				}
				else {
					closeEditor();
				}
			}
		});
	}
	
	private List<System> getTreeData() {
		List<System> systemList = SystemManager.getSystemList();
		List<SubSystem> subSystemList = SubSystemManager.getSubSystemList();
		List<System> treeList = new ArrayList<>();
		
		treeList.addAll(systemList);
		
		
		for(SubSystem subsystem : subSystemList) {
			for(System system : systemList) {
				if(subsystem.getSystemId().equals(system.getId())) {
					subsystem.setParent(system);
				}
			}
			treeList.add(subsystem);
		}
		
		return treeList;
	}
	
	private void save(SubSystemForm.SaveEvent evt) {
		SubSystemManager.updateSubSystem(evt.getSubSystem());
		closeEditor();
	}
	
	private void editSubSystem(SubSystem subSystem) {
		form.setData(subSystem);
		form.setVisible(true);
		addClassName("editing");
	}
	
	private void closeEditor() {
		form.setData(null);
		form.setVisible(false);
		removeClassName("editing");
		systemTree.deselectAll();
	}
	
}


