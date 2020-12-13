package sample.Home.Model;

import javafx.beans.property.SimpleStringProperty;

public class MachineAdapter {
    private final SimpleStringProperty id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty type;
    private final SimpleStringProperty costPerDay;

    public MachineAdapter(Machine machine, SimpleStringProperty id, SimpleStringProperty name, SimpleStringProperty type, SimpleStringProperty costPerDay){
        this.id = id;
        this.name = name;
        this.type = type;


        this.costPerDay = costPerDay;
    }

}
