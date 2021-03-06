package resources;

import core.EmergencyDepartment;

/**
 * ShockRoom is a class which represent the Shock Rooms of the Emergency Department.
 * It extends Rooms.
 * @author Quentin
 *
 */

public class ShockRoom extends Room {

	public ShockRoom(String name, int capacity, EmergencyDepartment emergencyDepartment) {
		super(name, capacity, emergencyDepartment);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof ShockRoom)) {
			return false;
		}
		return true;
	}
	
}
