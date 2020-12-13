package sample.Home.Model;



public abstract class Machine {

    private String id;
    private String name;
    private String type;
    private int costPerDay;


    public Machine(){

    }

    public Machine(String id,String name,int costPerDay,String category){
        this.id = id;
        this.name = name;
        this.costPerDay = costPerDay;
        this.type = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setCostPerDay(int costPerDay) {
        this.costPerDay = costPerDay;
    }

    public abstract double calcRent(int days);

}
