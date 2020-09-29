package automail;

import setUp.FoodSetup;
import setUp.Setup;

public class FoodItem extends MailItem {
	public FoodItem(int dest_floor, int arrival_time, int weight) {
		super(dest_floor, arrival_time, weight);
	}
	
	@Override
	public String toString() {
		return String.format("Food Item:: ID: %6s | Arrival: %4d | Destination: %2d | Weight: %4d", id, arrival_time, destination_floor, weight);
	}
}
