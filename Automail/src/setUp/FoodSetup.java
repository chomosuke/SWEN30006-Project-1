package setUp;

import automail.MailItem;
import automail.FoodItem;
import simulation.Clock;

import java.util.ArrayList;

public class FoodSetup extends Setup {

    private static final int SETUPTIME = 5;
    private static final int MAXITEMS = 3;
    private int initialTime;
    private final ArrayList<FoodItem> foodTube;

    public void loadItemSetup(MailItem item) {
        if(foodTube.size() < MAXITEMS) {
            foodTube.add((FoodItem) item);
        }
    }

    public FoodSetup() {
        foodTube = new ArrayList<>();
    }

    @Override
    public boolean isFull() {
        return foodTube.size() == MAXITEMS;
    }

    @Override
    public boolean isEmpty(){
        return foodTube.isEmpty();
    }

    @Override
    public MailItem getItem() {
        MailItem item = null;
        if(!foodTube.isEmpty()) {
            item = foodTube.get(foodTube.size()-1);
        }
        return item;
    }

    @Override
    public MailItem popItem() {
        MailItem item = null;
        if(!foodTube.isEmpty()) {
            item = foodTube.get(foodTube.size()-1);
            foodTube.remove(foodTube.size()-1);
        }
        return item;
    }

    @Override
    public int getNumOfItemInTube() {
        int num = 0;
        if (foodTube != null) {
            return foodTube.size();
        }
        return num;
    }
    
    @Override 
    public void boot() {
        initialTime = Clock.Time();
    }

    @Override
    public boolean isReady() {
        return Clock.Time() - initialTime >= SETUPTIME;
    }

}