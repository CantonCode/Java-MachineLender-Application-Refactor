package Main.Home.Model;

import java.io.Serializable;

public abstract class Machine implements Serializable {

    private String id;
    private String name;
    private int costPerDay;
    private String type;
    private int inventory;

    public Machine() {
    }

    public Machine(String id,String name,int costPerDay) {
        this.id = id;
        this.name = name;
        this.costPerDay = costPerDay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getInventory() {
        return inventory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCostPerDay() {
        return costPerDay;
    }
//go ahe
    public void setCostPerDay(int costPerDay) { this.costPerDay = costPerDay; }

    public abstract double calcRent(int cost);

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setInventory(int inventory) {
        this.inventory=inventory;
    }
}
