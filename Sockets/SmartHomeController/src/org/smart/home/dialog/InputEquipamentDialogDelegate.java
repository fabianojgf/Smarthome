package org.smart.home.dialog;

import org.smart.gateway.service.options.SocketClientOptions;
import org.smart.home.equipament.model.enums.EquipamentType;

public interface InputEquipamentDialogDelegate {
	public void postInputEquipamentDialog(SocketClientOptions options, EquipamentType type, String name);
}
