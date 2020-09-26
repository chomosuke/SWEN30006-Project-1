package setUp;

import automail.MailItem;
import automail.FoodItem;
import simulation.Clock;

import java.util.ArrayList;

public class FoodSetup extends Setup {

    private static int SETUPTIME = 5;
    private static int INITIALTIME = Clock.Time();
    private static int MAXITEMS = 3;
    private ArrayList<FoodItem> foodTube;

    public void loadItem(MailItem item) {
        if(foodTube == null) {
            foodTube = new ArrayList<>();
            foodTube.add((FoodItem) item);
        } else if(foodTube.size() < MAXITEMS) {
            foodTube.add((FoodItem) item);
        }
    }

    @Override
    public boolean isFull() {
        return foodTube.size() == MAXITEMS;
    }

    @Override
    public boolean isEmpty(){
        return foodTube == null;
    }

    @Override
    public MailItem getItem() {
        MailItem item;
        item = foodTube.get(foodTube.size()-1);
        return item;
    }

    //whats the difference between these methods?
    @Override
    public MailItem popItem() {
        MailItem item;
        item = foodTube.get(foodTube.size()-1);
        foodTube.remove(foodTube.size()-1);
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
    public boolean isReady() {
        return INITIALTIME - Clock.Time() >= SETUPTIME;
    }

}
