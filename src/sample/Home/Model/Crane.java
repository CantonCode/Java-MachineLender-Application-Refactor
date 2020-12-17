package sample.Home.Model;

/*
    Class to make cranes
 */
public class Crane extends Machine {

    public Crane() {
    }

    public Crane(String id, String name, int costPerDay) {
        super(id, name, costPerDay);
    }

    /*
        Method to calculate rent for crane with discounts for longer time periods
     */
    public double calcRent(int days) {
        double price = this.getCostPerDay()*days;
        if (days>=3 && days<7) {
            price *= 0.9;
        } else if (days>=7 && days<30) {
            price *= 0.8;
        } else if (days>=30 && days<150) {
            price *= 0.7;
        } else if (days>150) {
            price *= 0.6;
        }

        return price;
    }
}
