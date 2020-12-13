package sample.Home.Model;

import sample.Home.Model.Machine;

public class Digger extends Machine {


    public Digger(){
    }


    public Digger(String id,String name,int costPerDay){
        super(id,name,costPerDay);
        super.setType("Digger");
    }



    public double calcRent(int days) {
        double price = this.getCostPerDay()*days;
        if(days>7 && days<=30){
            price *= 0.9;
        }else if(days>30 && days<=150){
            price *= 0.8;
        }else if(days>150){
            price *= 0.7;
        }
        return price;
    }
}
