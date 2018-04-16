package org.smart.main;

import org.smart.equipament.dialog.InputEquipamentDialog;
import org.smart.equipament.dialog.InputEquipamentDialogDelegate;
import org.smart.equipament.model.Equipament;
import org.smart.equipament.model.enums.EquipamentType;
import org.smart.equipament.viewcontroller.EquipamentViewController;
import org.smart.equipament.viewcontroller.util.EquipamentViewControllerUtil;
import org.smart.mqtt.client.SmartMqttClientOptions;

public class EquipamentManager implements InputEquipamentDialogDelegate {
	
	public EquipamentManager() {
		super();
		InputEquipamentDialog dialog = new InputEquipamentDialog(null, "Install Equipament");
		dialog.setDelegate(this);
		dialog.useDefaultValues();
		dialog.show();
	}

	@Override
	public void postInputEquipamentDialog(SmartMqttClientOptions options,
			EquipamentType type, String name) {
		EquipamentViewControllerUtil.getControllerByEquipamentType(type, name).initiliazeConnection(options);
	}
}
