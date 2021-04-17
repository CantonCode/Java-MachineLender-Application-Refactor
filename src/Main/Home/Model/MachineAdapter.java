package Main.Home.Model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

/*
    Adapter class for machines
 */
public class MachineAdapter {
    private final SimpleStringProperty id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty type;
    private final SimpleDoubleProperty costPerDay;
    private final SimpleIntegerProperty inventory;
    private Machine machine;

    public MachineAdapter(Machine machine) {
        this.machine = machine;
       id = new SimpleStringProperty(machine.getId());
       name = new SimpleStringProperty(machine.getName());
       type = new SimpleStringProperty(machine.getType());
       costPerDay = new SimpleDoubleProperty(machine.getCostPerDay());
       inventory = new SimpleIntegerProperty(machine.getInventory());
    }

    public String getId() {
        return id.get();
    }
    public SimpleStringProperty idProperty() {
        return id;
    }
    public void setId(String id) { this.id.set(id); }

    public String getName() {
        return name.get();
    }
    public SimpleStringProperty nameProperty() {
        return name;
    }
    public void setName(String name) {
        this.name.set(name);
    }

    public void setCostPerDay(int costPerDay) {
        this.costPerDay.set(costPerDay);
    }

    public int getInventory() {
        return inventory.get();
    }

    public SimpleIntegerProperty inventoryProperty() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory.set(inventory);
    }

    public String getType() { return type.get(); }
    public SimpleStringProperty typeProperty() {
        return type;
    }
    public void setType(String type) { this.type.set(type); }

    public double getCostPerDay() { return costPerDay.get(); }
    public SimpleDoubleProperty costPerDayProperty() { return costPerDay; }
    public void setCostPerDay(Integer cost) { this.costPerDay.set(cost); }

    public Machine getMachine(){
        machine.setName(getName());
        machine.setId(getId());
        machine.setInventory(getInventory());
        machine.setType(getType());
        machine.setCostPerDay(getCostPerDay());
        return machine;
    }
}
