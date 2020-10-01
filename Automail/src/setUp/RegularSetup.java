package setUp;

import automail.MailItem;
import automail.RegularItem;

public class RegularSetup extends Setup {
	
	// hand first in first out
	private RegularItem hand = null;
	private RegularItem tube = null;

	@Override
	public void loadItemSetup(MailItem item) {
		if (hand == null)
			hand = (RegularItem) item;
		else if (tube == null)
			tube = (RegularItem) item;
		
	}

	@Override
	public boolean isFull() {
		return tube != null;
	}

	@Override
	public boolean isEmpty() {
		return hand == null;
	}

	// if empty return null
	@Override
	public MailItem getItem() {
		if (hand != null)
			return hand;
		else
			return tube;
	}
	
	@Override
	public MailItem popItem() {
		MailItem item;
		item = hand;
		hand = tube;
		tube = null;
		return item;
	}
	
	@Override
	public int getNumOfItemInTube() {
		int num = 0;
		if (tube != null) {
			num++;
		}
		return num;
	}
	
}
