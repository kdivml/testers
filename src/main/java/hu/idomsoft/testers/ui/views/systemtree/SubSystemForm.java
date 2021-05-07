package hu.idomsoft.testers.ui.views.systemtree;

import java.util.List;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.shared.Registration;

import hu.idomsoft.testers.data.BaseData;
import hu.idomsoft.testers.data.SubSystem;
import hu.idomsoft.testers.data.Tester;
import hu.idomsoft.testers.data.managers.BaseDataManager;
import hu.idomsoft.testers.data.managers.TesterManager;

public class SubSystemForm extends FormLayout {

	List<Tester> testerList;
	List<BaseData> baseDataList;
	ComboBox<Tester> cbTesters = new ComboBox<>("Hozzárendelt tesztelő");
	ComboBox<BaseData> cbBaseDatas = new ComboBox<>("Hozzárendelt BaseData");
	
	private SubSystem subSystem;
	
	public SubSystemForm() {
		addClassName("pop-in-form");
		
		initComboBoxes();
		
		add(cbTesters, cbBaseDatas, createButtonsLayout());
	}
	
	private void initComboBoxes() {
		testerList = TesterManager.getTesterList();
		baseDataList = BaseDataManager.getBaseDataList();
		
		cbTesters.setItems(testerList);
		cbTesters.setItemLabelGenerator(Tester::getName);
		cbTesters.setClearButtonVisible(true);
		cbTesters.addValueChangeListener(change -> {
			if(change.getValue() == null) {
				subSystem.setTesterId(null);
			}
			else {
				subSystem.setTesterId(change.getValue().getId());
			}
		});
		
		cbBaseDatas.setItems(baseDataList);
		cbBaseDatas.setItemLabelGenerator(BaseData::getName);
		cbBaseDatas.setClearButtonVisible(true);
		cbBaseDatas.addValueChangeListener(change -> {
			if(change.getValue() == null) {
				subSystem.setBaseDataId(null);
			}
			else {
				subSystem.setBaseDataId(change.getValue().getId());
			}
		});
	}
	
	private Component createButtonsLayout() {
		Button save = new Button("Mentés");
		Button close = new Button("Bezárás");
		
		save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		
		save.addClickShortcut(Key.ENTER);
		close.addClickShortcut(Key.ESCAPE);
		
		save.addClickListener(click -> fireEvent(new SaveEvent(this, subSystem)));
		close.addClickListener(click -> fireEvent(new CloseEvent(this, subSystem)));
		
		return new HorizontalLayout(save, close);
	}
	
	public void setData(SubSystem subSystem) {
		this.subSystem = subSystem;
		if(subSystem != null) {
			if(subSystem.getTesterId() == null) {
				cbTesters.setValue(null);
			}
			else {
				for(Tester tester : testerList) {
					if(subSystem.getTesterId().equals(tester.getId())) {
						cbTesters.setValue(tester);
					}
				}
			}
			if(subSystem.getBaseDataId() == null) {
				cbBaseDatas.setValue(null);
			}
			else {
				for(BaseData baseData : baseDataList) {
					if(subSystem.getBaseDataId().equals(baseData.getId())) {
						cbBaseDatas.setValue(baseData);
					}
				}
			}
		}
	}	
	
	public abstract static class AbstractSubSystemFormEvent extends ComponentEvent<SubSystemForm> {
		private SubSystem subSystem;
		
		protected AbstractSubSystemFormEvent(SubSystemForm source, SubSystem subSystem) {
			super(source, false);
			this.subSystem = subSystem;
		}
		
		public SubSystem getSubSystem() {
			return subSystem;
		}
	}
	
	public static class SaveEvent extends AbstractSubSystemFormEvent {
		SaveEvent(SubSystemForm source, SubSystem subSystem) {
			super(source, subSystem);
		}
	}
	
	public static class CloseEvent extends AbstractSubSystemFormEvent {
		CloseEvent(SubSystemForm source, SubSystem subSystem) {
			super(source, subSystem);
		}
	}
	
	@Override
	public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
		return getEventBus().addListener(eventType, listener);
	}
}
