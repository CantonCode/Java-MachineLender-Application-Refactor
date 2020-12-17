package sample.Observer;
/**
* This class wraps the obserpattern in an invoker
* everything related to the Observer class is controlled here
* */
public class ObserverInvoker {
    private InventoryManager invMan;

    public ObserverInvoker(){
        invMan=new InventoryManager();
        InventoryObserver invObs=new InventoryObserver(invMan);

    }
    public void registerNewObserver(){
        InventoryObserver invObs=new InventoryObserver(invMan);

    }
    public void updateInventory(int inventory){
        invMan.setInventory(inventory);
    }
    public void unregisterObservaer(Observer o){
        invMan.unregister(o);
    }
}
