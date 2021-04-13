package Main.Observer;

import java.util.ArrayList;

public class InventoryManager implements Subject{
    //This class will manage the behaviors of all the observes, the observers are stored in an arrayList
     ArrayList<Observer> observers;
    int inventory=0;
   public  InventoryManager(){
        observers=new ArrayList<>();
    }
    @Override
    public void register(Observer newObserver) {
        observers.add(newObserver);
    }

    @Override
    public void unregister(Observer oldObserver) {
       int observerIndex= observers.indexOf(oldObserver);
       if(observerIndex>-1)
           observers.remove(observerIndex);
        System.out.println("Observer deleted: ");
    }

    //notifies all servers of any changes within the inventory
    //changing their values every where;
    @Override
    public void notifyObserver() {
       for(Observer o : observers)
           o.update(inventory);
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
        //when the inventory changes all obsers are notified
        notifyObserver();
    }
}
