package org.smart.equipament.viewcontroller.util;

import org.smart.equipament.model.Equipament;
import org.smart.equipament.model.enums.EquipamentType;
import org.smart.equipament.viewcontroller.AirConditioningViewController;
import org.smart.equipament.viewcontroller.DoorViewController;
import org.smart.equipament.viewcontroller.EquipamentViewController;
import org.smart.equipament.viewcontroller.GateViewController;
import org.smart.equipament.viewcontroller.LampViewController;
import org.smart.equipament.viewcontroller.LightSensorViewController;
import org.smart.equipament.viewcontroller.TVViewController;

public class EquipamentViewControllerUtil {
	public static EquipamentViewController<? extends Equipament> getControllerByEquipamentType(EquipamentType type, String name) {
		if(type == EquipamentType.AIR_CONDITIONING) 
			return new AirConditioningViewController(name);
		if(type == EquipamentType.DOOR) 
			return new DoorViewController(name);
		if(type == EquipamentType.GATE) 
			return new GateViewController(name);
		if(type == EquipamentType.LAMP) 
			return new LampViewController(name);
		if(type == EquipamentType.TV) 
			return new TVViewController(name);
		if(type == EquipamentType.LIGHT_SENSOR) 
			return new LightSensorViewController(name);
		return null;
	}
}
