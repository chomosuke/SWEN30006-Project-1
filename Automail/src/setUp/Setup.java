package setUp;

import automail.MailItem;

public abstract class Setup {
	
	public abstract void loadItemSetup(MailItem item);
	public abstract boolean isFull();
	public abstract boolean isEmpty();
	public abstract MailItem getItem();
	public abstract MailItem popItem();
	public abstract int getNumOfItemInTube();
	
	public void boot() {} // default do nothing
	
	public boolean isReady() {
		return true; // default always ready
	}
	
	public int getDestination() {
		MailItem item = getItem();
		if (item == null)
			return -1;
		else 
			return item.getDestFloor();
	}
}
