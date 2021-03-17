package Main.Authentication.Model;
import Main.Home.Model.Machine;

import java.beans.Transient;
import java.io.Serializable;
import java.util.ArrayList;

/*
    This class creates the user accounts necessary to use the app.
 */
public abstract class User implements Serializable {

    protected String id;
    protected String name;
    protected String username;
    protected String password;
    protected AccountType type;
    protected ArrayList<Machine> currRentals;

    public User(String id, String name, String username, String password,ArrayList<Machine> curr) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.currRentals = curr;
    }

    public User(String id, String name, String username, String password, AccountType type,ArrayList<Machine> curr) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.type = type;
        this.currRentals = curr;
    }

    public User(String id, String name, String username,ArrayList<Machine> curr) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.currRentals = curr;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public ArrayList<Machine> getCurrRentals() {
        return currRentals;
    }

    public void setCurrRentals(ArrayList<Machine> currRentals) {
        this.currRentals = currRentals;
    }

    @Transient
    public abstract void encryptPassword();
    @Transient
    public abstract void decryptPassword();

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", type=" + type + '\'' +
                ", rentals =" + currRentals +
                '}';
    }

    public  void addCurrentRentals(Machine m){
        this.currRentals.add(m);
    }
}
