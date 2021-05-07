package hu.idomsoft.testers.ui.views.basedata;

import java.util.List;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.shared.Registration;

import hu.idomsoft.testers.data.BaseData;
import hu.idomsoft.testers.data.managers.BaseDataManager;
import hu.idomsoft.testers.data.managers.PrefixManager;
import hu.idomsoft.testers.data.managers.SubSystemManager;

public class BaseDataForm extends FormLayout {
	
	Button saveButton = new Button("Mentés");
	Button deleteButton = new Button("Törlés");
	Button closeButton = new Button("Bezárás");
	
	private BaseData baseData;
	TextField tfName = new TextField("Név:");
	TextArea taDescription = new TextArea("Leírás:");
	
	public BaseDataForm() {
		addClassName("pop-in-form");
		tfName.setRequired(true);
		add(tfName, taDescription, createButtonsLayout());
	}
	
	
	private Component createButtonsLayout( ) {		
		saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
		closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		
		saveButton.addClickShortcut(Key.ENTER);
		closeButton.addClickShortcut(Key.ESCAPE);
		
		saveButton.addClickListener(click -> {
			if(tfName.isEmpty()) {
				ConfirmDialog cdNameEmptyWarning = new ConfirmDialog(
						"Kötelező mező",
						"A BaseData neve nem lehet üres!",
						"OK", e -> {});
				cdNameEmptyWarning.open();
			}
			else {
				if(checkIfExists(tfName.getValue())) {
					ConfirmDialog cdExistingBaseData = new ConfirmDialog(
						"Létező BaseData",
						"A megadott BaseData név már létezik!",
						"OK", e -> {});
					cdExistingBaseData.open();
				}
				else {
					baseData.setName(tfName.getValue());
					baseData.setDescription(taDescription.getValue());
					fireEvent(new SaveEvent(this, baseData));
				}
			}
		});
		
		deleteButton.addClickListener(click -> {
			boolean bindedToSubSystem = checkIfBindedToSubSystem(baseData);
			boolean hasBindendPrefix = checkIfHasBindedPrefix(baseData);
			if(bindedToSubSystem || hasBindendPrefix) {
				String bindingWarning = "";
				if(bindedToSubSystem && hasBindendPrefix) {
					bindingWarning = 
							"A BaseData nem törölhető, mert hozzá van rendelve legalább egy alrendszerhez és legalább egy prefix is hozzá van kapcsolva!";
				}
				else {
					if(bindedToSubSystem) 
						bindingWarning = "A BaseData nem törölhető, mert hozzá van rendelve legalább egy alrendszerhez!";
					if(hasBindendPrefix) 
						bindingWarning = "A BaseData nem törölhető, mert hozzá van rendelve legalább egy prefix!";
				}
				ConfirmDialog cdHasBinding = new ConfirmDialog(
						"Nem törölhető!",
						bindingWarning,
						"OK", e -> {});
				cdHasBinding.open();
			}
			else {
				ConfirmDialog cdDelete = new ConfirmDialog(
						"Törlés megerősítése",
						"Biztosan törölni kívánja a kijelölt BaseData-t?",
						"Törlés", e -> fireEvent(new DeleteEvent(this, baseData)),
						"Mégse", e -> {});
				cdDelete.open();
			}
		});
		
		closeButton.addClickListener(click -> fireEvent(new CloseEvent(this, baseData)));
		
		return new HorizontalLayout(saveButton, deleteButton, closeButton);
	}
	
	public void setData(BaseData baseData) {
		this.baseData = baseData;
		if(baseData != null) {
			if(baseData.getName() == null) {
				tfName.setValue("");
			}
			else {
				tfName.setValue(baseData.getName());
			}
			if(baseData.getDescription() == null) {
				taDescription.setValue("");
			}
			else {
				taDescription.setValue(baseData.getDescription());
			}
			deleteButton.setVisible(baseData.getId() != null);
		}
	}
	
	public abstract static class AbstractBaseDataFormEvent extends ComponentEvent<BaseDataForm> {
		private BaseData baseData;
		
		protected AbstractBaseDataFormEvent(BaseDataForm source, BaseData baseData) {
			super(source, false);
			this.baseData = baseData;
		}
		
		public BaseData getBaseData() {
			return baseData;
		}
	}
	
	public static class SaveEvent extends AbstractBaseDataFormEvent {
		SaveEvent(BaseDataForm source, BaseData baseData) {
			super(source, baseData);
		}
	}
	
	public static class DeleteEvent extends AbstractBaseDataFormEvent {
		DeleteEvent(BaseDataForm source, BaseData baseData) {
			super(source, baseData);
		}
	}
	
	public static class CloseEvent extends AbstractBaseDataFormEvent {
		CloseEvent(BaseDataForm source, BaseData baseData) {
			super(source, baseData);
		}
	}
	
	@Override
	public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
		return getEventBus().addListener(eventType, listener);
	}
	
	private boolean checkIfExists(String newName) {
		List<BaseData> existingList = BaseDataManager.getBaseDataList();
		for(BaseData existing : existingList) {
			if(existing.getName().equals(newName)) {
				return true;
			}
		}
		
		return false;
	}

	private boolean checkIfBindedToSubSystem(BaseData baseData) {
		return !SubSystemManager.getBindedSubSystemsForBaseData(baseData).isEmpty();
	}
	
	private boolean checkIfHasBindedPrefix(BaseData baseData) {
		return !PrefixManager.getLinkedPrefixList(baseData).isEmpty();
	}
}
