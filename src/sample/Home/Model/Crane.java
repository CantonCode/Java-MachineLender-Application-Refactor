package sample.Home.Model;

import sample.Home.Model.Machine;

public class Crane extends Machine {



    public Crane() {
    }

    public Crane(String id, String name, Boolean rented, int costPerDay, String category) {
        super(id, name, rented, costPerDay, category);

    }

    public double calcRent(int days) {
        double price = this.getCostPerDay()*days;
        if(days>=3 && days<7){
            price *= 0.9;
        }else if(days>=7 && days<30){
            price *= 0.8;
        }else if(days>=30 && days<150){
            price *= 0.7;
        }else if(days>150){
            price *= 0.6;
        }
        return price;

    }

}
