package Main.Observer;

public class InventoryObserver  implements  Observer{
    private int inventory = 0;
    private   int observerId=0;
    private   int observerIdTracker=0;
    private Subject inventoryManager;
    public InventoryObserver(Subject inventoryManager){
        this.inventoryManager=inventoryManager;
        this.observerId = ++observerIdTracker;
        inventoryManager.register(this);
    }
    @Override
    public void update(int inventory) {
        this.inventory=inventory;
    }
}
