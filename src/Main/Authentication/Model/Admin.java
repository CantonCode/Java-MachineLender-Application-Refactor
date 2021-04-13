package Main.Authentication.Model;

import Main.Home.Model.Machine;

import java.util.ArrayList;
/*
    Admin class extends the user class to create an admin account type.
 */
public class Admin extends User {


    private transient Security security;

    public Admin(String id, String name, String username, String password,ArrayList<Machine>curr) {
        super(id, name, username, password,curr );
    }

    public Admin(String id, String name, String username, ArrayList<Machine> curr) {
        super(id, name, username,curr);
    }

    public Admin(String id, String name, String username, String password, AccountType accountType, ArrayList<Machine> curr) {
        super(id,name,username,password,accountType,curr);
    }

    @Override
    public void encryptPassword() {
        security = new Encryption();
        this.password = security.layerTwo(password);
        this.password = security.layerOne(password);
        this.password = security.layerTwo(password);
        this.password = security.layerOne(password);
        this.password = security.layerTwo(password);
    }

    @Override
    public void decryptPassword() {
        security = new Decryption();
        this.password = security.layerTwo(password);
        this.password = security.layerOne(password);
        this.password = security.layerTwo(password);
        this.password = security.layerOne(password);
        this.password = security.layerTwo(password);

    }
}
