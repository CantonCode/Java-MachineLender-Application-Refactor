package sample.Home.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

/*
    Adapter class for machines
 */
public class MachineAdapter {
    private final SimpleStringProperty id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty type;
    private final SimpleIntegerProperty costPerDay;

    public MachineAdapter(Machine machine) {
       id = new SimpleStringProperty(machine.getId());
       name = new SimpleStringProperty(machine.getName());
       type = new SimpleStringProperty(machine.getType());
       costPerDay = new SimpleIntegerProperty(machine.getCostPerDay());
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

    public String getType() { return type.get(); }
    public SimpleStringProperty typeProperty() {
        return type;
    }
    public void setType(String type) { this.type.set(type); }

    public Integer getCostPerDay() { return costPerDay.get(); }
    public SimpleIntegerProperty costPerDayProperty() { return costPerDay; }
    public void setCostPerDay(Integer cost) { this.costPerDay.set(cost); }
}
