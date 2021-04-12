package Main.Authentication.Model;

import Main.Home.Model.Machine;

import java.util.ArrayList;

public class UserBuilder {
    private String id;
    private String name;
    private String username;
    private String password;
    private ArrayList<Machine> curr;
    private AccountType type;

    public UserBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public UserBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder setCurr(ArrayList<Machine> curr) {
        this.curr = curr;
        return this;
    }

    public UserBuilder setType(AccountType type) {
        this.type = type;
        return this;
    }

    public User createUser() {
        return new User(id, name, username, password, curr) {
            @Override
            public void encryptPassword() {

            }

            @Override
            public void decryptPassword() {

            }
        };
    }
}