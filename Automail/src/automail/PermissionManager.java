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
	
	private FoodItem[] foodDeliveryHappening = new FoodItem[Building.FLOORS];
	public void notifyDelivered(MailItem item) {
		if (item instanceof FoodItem) {
			assert(foodDeliveryHappening[item.getDestFloor() - Building.LOWEST_FLOOR] == item);
			foodDeliveryHappening[item.getDestFloor() - Building.LOWEST_FLOOR] = null;
		}
	}
	
	public void notifyDelivering(MailItem item) {
		assert(getDeliveryPermission(item));
		if (item instanceof FoodItem) {
			// this ensure that any other robot does not start moving to a destination
			// floor which currently have a robot delivering food to that floor
			foodDeliveryHappening[item.getDestFloor() - Building.LOWEST_FLOOR] = (FoodItem) item;
		}
	}
	
	public boolean getDeliveryPermission(MailItem item) {
		return foodDeliveryHappening[item.getDestFloor() - Building.LOWEST_FLOOR] == null // not currently locked
				|| foodDeliveryHappening[item.getDestFloor() - Building.LOWEST_FLOOR] == item; // already gave permission
	}
	
}
