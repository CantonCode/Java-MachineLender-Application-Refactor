package Main.Authentication.Model;

import javafx.beans.property.SimpleStringProperty;
import Main.Home.Model.Machine;


import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class UserAdapter {
    private final SimpleStringProperty username;
    private final SimpleStringProperty name;
    private final SimpleStringProperty id;
    private final SimpleStringProperty created;
    private final ArrayList<Machine> currRentals;

    public UserAdapter(User user){
        username=new SimpleStringProperty(user.getUsername());
        name=new SimpleStringProperty(user.getName());
        id=new SimpleStringProperty(user.getId());
        created=new SimpleStringProperty(new SimpleDateFormat("dd/MM/yyyy hh:mm").format(Long.parseLong(user.getId())));
        currRentals = user.getCurrRentals();
    }

    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getCreated() {
        return created.get();
    }

    public SimpleStringProperty createdProperty() {
        return created;
    }

    public void setCreated(String created) {
        this.created.set(created);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public ArrayList<Machine> getCurrRentals() {
        return currRentals;
    }
}
