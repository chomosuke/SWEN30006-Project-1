package setUp;

import automail.MailItem;

public abstract class Setup {
	
	public abstract void loadItem(MailItem item);
	public abstract boolean isFull();
	public abstract boolean isEmpty();
	public abstract MailItem getItem();
	public abstract MailItem popItem();
	public abstract boolean isReady();
	public abstract int getNumOfItemInTube();
	
	public int getDestination() {
		MailItem item = getItem();
		if (item == null)
			return -1;
		else 
			return item.getDestFloor();
	}
}
