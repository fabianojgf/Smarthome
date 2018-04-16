package org.smart.equipament.dialog;

import org.smart.gateway.service.options.SocketClientOptions;
import org.smart.equipament.model.enums.EquipamentType;

public interface InputEquipamentDialogDelegate {
	public void postInputEquipamentDialog(SocketClientOptions options, EquipamentType type, String name);
}
