package automail;

import setUp.RegularSetup;
import setUp.Setup;

public class RegularItem extends MailItem {
	public RegularItem(int dest_floor, int arrival_time, int weight) {
		super(dest_floor, arrival_time, weight);
	}
	
	@Override
	public String toString() {
		return String.format("Mail Item:: ID: %6s | Arrival: %4d | Destination: %2d | Weight: %4d", id, arrival_time, destination_floor, weight);
	}
	
	@Override
	public Setup getNewSetup() {
		return new RegularSetup();
		
	}
	
}
