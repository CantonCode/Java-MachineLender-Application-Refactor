package Main.Authentication.Model;

import Main.Home.Model.Machine;

import java.util.ArrayList;

public class CustomerBuilder {
    private String id;
    private String name;
    private String username;
    private String password;
    private ArrayList<Machine> curr;
    private AccountType type;

    public CustomerBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public CustomerBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public CustomerBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public CustomerBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public CustomerBuilder setCurr(ArrayList<Machine> curr) {
        this.curr = curr;
        return this;
    }

    public CustomerBuilder setType(AccountType type) {
        this.type = type;
        return this;
    }

    public Customer createCustomer() {
        return new Customer(id, name, username, password, curr);
    }
}