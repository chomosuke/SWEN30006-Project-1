package automail;

import java.util.LinkedList;
import java.util.Comparator;
import java.util.ListIterator;

import exceptions.ItemTooHeavyException;
import setUp.FoodSetup;
import setUp.RegularSetup;
import setUp.Setup;
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
	private LinkedList<Item> foodItemPool; //new var
	private LinkedList<Item> regularItemPool; //new var
	
	
	public MailPool(int nrobots){
		// Start empty
		//pool = new LinkedList<Item>();
		robots = new LinkedList<Robot>();
		foodItemPool = new LinkedList<Item>();
		regularItemPool = new LinkedList<Item>();
	}

	/**
     * Adds an item to the mail pool
     * @param mailItem the mail item being added.
     */
	public void addToPool(MailItem mailItem) {
		Item item = new Item(mailItem);
		
		if(mailItem instanceof RegularItem) {
			
			regularItemPool.add(item);
			regularItemPool.sort(new ItemComparator());
			
		}
		
		if(mailItem instanceof FoodItem) {
			
			foodItemPool.add(item);
			
		}
	}
	
	
	
	/**
     * load up any waiting robots with mailItems, if any.
     */
	public void loadItemsToRobot() throws ItemTooHeavyException {
		//List available robots
		ListIterator<Robot> i = robots.listIterator();
		while (i.hasNext() && (!foodItemPool.isEmpty() || !regularItemPool.isEmpty())) 
			loadItem(i);
	}
	
	// start with loading regular item and alternate between the two for each robot
	private boolean lastRegular = true;
	//load items to the robot
	private void loadItem(ListIterator<Robot> i) throws ItemTooHeavyException {
		
		if (lastRegular)
			loadItemSetup(i, foodItemPool, new FoodSetup());
		else
			loadItemSetup(i, regularItemPool, new RegularSetup());
		
		lastRegular = !lastRegular;
	}
	
	private void loadItemSetup(ListIterator<Robot> i, LinkedList<Item> pool, Setup setup) throws ItemTooHeavyException {
		if (!pool.isEmpty()) {
			//if it's pool is not empty
			
			Robot robot = i.next();
			assert(robot.isEmpty());
//			 System.out.printf("P: %3d%n", pool.size());
			
			robot.setSetup(setup);
			// record the setup
			if (setup instanceof FoodSetup) {
				numOfTimesFoodTubeIsAttached++;
			}
			
			while(!pool.isEmpty() && !robot.isFull()) {
				robot.addToSetup(pool.poll().mailItem); // hand first as we want higher priority delivered first
			}
			
			robot.dispatch(); // send the robot off if it has any items to deliver
			i.remove();       // remove from mailPool queue
		}
	}
	
	private int numOfTimesFoodTubeIsAttached = 0;
	public int getNumOfTimesFoodTubeIsAttached() {
		return numOfTimesFoodTubeIsAttached;
	}
	

	/**
     * @param robot refers to a robot which has arrived back ready for more mailItems to deliver
     */	
	public void registerWaiting(Robot robot) { // assumes won't be there already
		robots.add(robot);
	}

}
