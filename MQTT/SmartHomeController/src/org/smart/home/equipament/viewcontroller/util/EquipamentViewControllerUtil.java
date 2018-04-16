package org.smart.home.equipament.viewcontroller.util;

import org.smart.home.equipament.model.Equipament;
import org.smart.home.equipament.model.enums.EquipamentType;
import org.smart.home.equipament.viewcontroller.AirConditioningViewController;
import org.smart.home.equipament.viewcontroller.DoorViewController;
import org.smart.home.equipament.viewcontroller.EquipamentViewController;
import org.smart.home.equipament.viewcontroller.GateViewController;
import org.smart.home.equipament.viewcontroller.LampViewController;
import org.smart.home.equipament.viewcontroller.LightSensorViewController;
import org.smart.home.equipament.viewcontroller.TVViewController;

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
