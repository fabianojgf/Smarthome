package org.smart.main;

import org.smart.equipament.viewcontroller.AirConditioningViewController;
import org.smart.equipament.viewcontroller.DoorViewController;
import org.smart.equipament.viewcontroller.GateViewController;
import org.smart.equipament.viewcontroller.LampViewController;
import org.smart.equipament.viewcontroller.TVViewController;

public class Main {
	public static void main(String[] args) {
//		AirConditioningViewController controller1 = new AirConditioningViewController("Air Conditioning 1");
//		controller1.initiliazeConnection("tcp://localhost:1883", "air1_1", "smarthome", "air1", null, null);
//		
//		DoorViewController controller2 = new DoorViewController("Home Door 1");
//		controller2.initiliazeConnection("tcp://192.168.15.4:1883", "door1_1", "smarthome", "door1", null, null);
//		
		//GateViewController controller3 = new GateViewController("Garage Gate 1");
		//controller3.initiliazeConnection("tcp://localhost:1883", "gate1", "smarthome", null, null);
		
		//LampViewController controller4 = new LampViewController("Kitchen Lamp 1");
		//controller4.initiliazeConnection("tcp://localhost:1883", "lamp1", "smarthome", null, null);
		
		//TVViewController controller5 = new TVViewController("Living Room TV 1");
		//controller5.initiliazeConnection("tcp://localhost:1883", "tv1", "smarthome", null, null);
		
		EquipamentManager manager = new EquipamentManager();
	}
}
