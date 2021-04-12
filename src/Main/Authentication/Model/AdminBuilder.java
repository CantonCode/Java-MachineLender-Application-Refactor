package Main.Authentication.Model;

import Main.Home.Model.Machine;

import java.util.ArrayList;

public class AdminBuilder {
    private String id;
    private String name;
    private String username;
    private String password;
    private ArrayList<Machine> curr;
    private AccountType accountType;

    public AdminBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public AdminBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public AdminBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public AdminBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public AdminBuilder setCurr(ArrayList<Machine> curr) {
        this.curr = curr;
        return this;
    }

    public AdminBuilder setAccountType(AccountType accountType) {
        this.accountType = accountType;
        return this;
    }

    public Admin createAdmin() {
        return new Admin(id, name, username, password, curr);
    }
}