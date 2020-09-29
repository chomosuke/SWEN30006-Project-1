package automail;

import simulation.Building;

public class PermissionManager {
	
	private PermissionManager() {}
	
	private static PermissionManager instance;
	
	public static PermissionManager getInstance() {
		if (instance == null) // lazy initialization
			instance = new PermissionManager();
		return instance;
	}
	
	private boolean[] lockedForDelivery = new boolean[Building.FLOORS];
	public void notifyDelivered(MailItem item) {
		if (item instanceof FoodItem) {
			assert(lockedForDelivery[item.getDestFloor() - Building.LOWEST_FLOOR] == true);
			lockedForDelivery[item.getDestFloor() - Building.LOWEST_FLOOR] = false;
		}
	}
	
	public void notifyDelivering(MailItem item) {
		if (item instanceof FoodItem) {
			// this ensure that any other robot does not start moving to a destination
			// floor which currently have a robot delivering food to that floor
			assert(getDeliveryPermission(item));
			lockedForDelivery[item.getDestFloor() - Building.LOWEST_FLOOR] = true;
		}
	}
	
	public boolean getDeliveryPermission(MailItem item) {
		return !lockedForDelivery[item.getDestFloor() - Building.LOWEST_FLOOR];
	}
	
}
