package Main;

import Main.Authentication.Model.User;
import Main.Home.Model.Machine;
import Main.Observer.ObserverInvoker;

import java.util.ArrayList;

public class Statics {
    public static User CurrentUser;
    public static ArrayList<User> Users=new ArrayList<>();
    public static ArrayList<Machine> Machines=new ArrayList<>();
    public static ObserverInvoker inventoryObservers=new ObserverInvoker();
}
