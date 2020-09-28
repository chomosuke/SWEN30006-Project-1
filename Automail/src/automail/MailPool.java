package automail;

import java.util.LinkedList;
import java.util.Comparator;
import java.util.ListIterator;

import exceptions.ItemTooHeavyException;
import simulation.PriorityMailItem;

/**
 * addToPool is called when there are mail items newly arrived at the building to add to the MailPool or
 * if a robot returns with some undelivered items - these are added back to the MailPool.
 * The data structure and algorithms used in the MailPool is your choice.
 * 
 */
public class MailPool {

	private class Item {
		int priority;
		int destination;
		MailItem mailItem;
		// Use stable sort to keep arrival time relative positions
		
		public Item(MailItem mailItem) {
			priority = (mailItem instanceof PriorityMailItem) ? ((PriorityMailItem) mailItem).getPriorityLevel() : 1;
			destination = mailItem.getDestFloor();
			this.mailItem = mailItem;
		}
	}
	
	public class ItemComparator implements Comparator<Item> {
		@Override
		public int compare(Item i1, Item i2) {
			int order = 0;
			if (i1.priority < i2.priority) {
				order = 1;
			} else if (i1.priority > i2.priority) {
				order = -1;
			} else if (i1.destination < i2.destination) {
				order = 1;
			} else if (i1.destination > i2.destination) {
				order = -1;
			}
			return order;
		}
	}
	
	//private LinkedList<Item> pool;
	private LinkedList<Robot> robots;
	private LinkedList<Item> FoodItemPool; //new var
	private LinkedList<Item> RegularItemPool; //new var
	
	
	public MailPool(int nrobots){
		// Start empty
		//pool = new LinkedList<Item>();
		robots = new LinkedList<Robot>();
		FoodItemPool = new LinkedList<Item>();
		RegularItemPool = new LinkedList<Item>();
	}

	/**
     * Adds an item to the mail pool
     * @param mailItem the mail item being added.
     */
	public void addToPool(MailItem mailItem) {
		Item item = new Item(mailItem);
		
		if(mailItem instanceof RegularItem) {
			
			RegularItemPool.add(item);
			
		}
		
		if(mailItem instanceof FoodItem) {
			
			FoodItemPool.add(item);
			
		}
		
		
		
		//pool.add(item);
		//pool.sort(new ItemComparator());
	}
	
	
	
	/**
     * load up any waiting robots with mailItems, if any.
     */
	public void loadItemsToRobot() throws ItemTooHeavyException {
		//List available robots
		ListIterator<Robot> i = robots.listIterator();
		while (i.hasNext()) loadItem(i);
	}
	
	//load items to the robot
	private void loadItem(ListIterator<Robot> i) throws ItemTooHeavyException {
		Robot robot = i.next();
		assert(robot.isEmpty());
		// System.out.printf("P: %3d%n", pool.size());
		
		
		//ListIterator<Item> j = pool.listIterator();  // old code
		
		
		//handle all Regular Items First
		if (RegularItemPool.size() > 0) {
			//if it's Regular item pool is not empty
			ListIterator<Item> j = RegularItemPool.listIterator();
		}else{
			//if it's food item pool is not empty
			ListIterator<Item> j = FoodItemPool.listIterator();
		}
		
		
		
		if (RegularItemPool.size() > 0 || FoodItemPool.size() > 0) {
			
				robot.setSetup( j.next().mailItem.getNewSetup() );
			
			try {
				
				while( (RegularItemPool.size() > 0 || FoodItemPool.size() > 0)  && !robot.isFull()) {
					robot.addToSetup(j.next().mailItem); // hand first as we want higher priority delivered first
					j.remove();
				}
				
				

			robot.dispatch(); // send the robot off if it has any items to deliver
			i.remove();       // remove from mailPool queue
			} catch (Exception e) { 
	            throw e; 
	        } 
		}
	}

	/**
     * @param robot refers to a robot which has arrived back ready for more mailItems to deliver
     */	
	public void registerWaiting(Robot robot) { // assumes won't be there already
		robots.add(robot);
	}

}
