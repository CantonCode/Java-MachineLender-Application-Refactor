package sample;

import sample.Authentication.Model.User;
import sample.Home.Model.Machine;
import sample.Observer.ObserverInvoker;

import java.util.ArrayList;

public class Statics {
    public static User CurrentUser;
    public static ArrayList<User> Users=new ArrayList<>();
    public static ArrayList<Machine> Machines=new ArrayList<>();
    public static ObserverInvoker inventoryObservers=new ObserverInvoker();
}
