package org.smart.home.dialog;

import org.smart.home.equipament.model.enums.EquipamentType;
import org.smart.mqtt.client.SmartMqttClientOptions;

public interface InputEquipamentDialogDelegate {
	public void postInputEquipamentDialog(SmartMqttClientOptions options, EquipamentType type, String name);
}
