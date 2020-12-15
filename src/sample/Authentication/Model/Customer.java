package sample.Authentication.Model;

import sample.Home.Model.Machine;

import java.util.ArrayList;
/*
    Customer class extends the user class to create a standard customer account.
 */
public class Customer extends User {

    private transient Security security;

    public Customer(String id, String name, String username, String password, ArrayList<Machine> curr) {
        super(id, name, username, password,curr);
    }

    public Customer(String id, String name, String username,ArrayList<Machine> curr) {
        super(id, name, username,curr);
    }

    public Customer(String id, String name, String username, String password, AccountType type,ArrayList<Machine> curr) {
        super(id, name, username, password, type,curr);
    }

    @Override
    public void encryptPassword() {
        security = new Encryption();
        this.password = security.layerOne(password);

    }

    @Override
    public void decryptPassword() {
        security = new Decryption();
        this.password = security.layerOne(password);

    }
}
