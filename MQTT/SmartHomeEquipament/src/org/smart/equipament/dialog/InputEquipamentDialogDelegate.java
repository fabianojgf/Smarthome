package org.smart.equipament.dialog;

import org.smart.equipament.model.enums.EquipamentType;
import org.smart.mqtt.client.SmartMqttClientOptions;

public interface InputEquipamentDialogDelegate {
	public void postInputEquipamentDialog(SmartMqttClientOptions options, EquipamentType type, String name);
}
