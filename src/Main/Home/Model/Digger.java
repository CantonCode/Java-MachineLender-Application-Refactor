package Main.Home.Model;

/*
    Class to make diggers
 */
public class Digger extends Machine {

    public Digger() {
    }

    public Digger(String id,String name,int costPerDay) {
        super(id,name,costPerDay);
    }

    @Override
    public void setType(String type) {
        super.setType("Digger");
    }

    /*
        Method to calculate rent for digger with discounts for longer time periods
     */
    public double calcRent(int days) {
        double price = this.getCostPerDay()*days;
        if (days>7 && days<=30) {
            price *= 0.9;
        } else if(days>30 && days<=150) {
            price *= 0.8;
        } else if(days>150) {
            price *= 0.7;
        }

        return price;
    }
}
