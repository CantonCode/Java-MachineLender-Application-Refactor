package Main.Authentication.Model;

import javafx.beans.property.SimpleStringProperty;
import Main.Home.Model.Machine;


import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class UserAdapter implements IUserAdapter {
    User thisUser;

    public UserAdapter(User user){
        thisUser = user;
    }

    @Override
    public String getUsername() {
        return thisUser.username;
    }

    @Override
    public void setUsername(String username) {
        thisUser.setUsername(username);
    }

    @Override
    public String getName() {
        return thisUser.name;
    }

    @Override
    public void setName(String name) {
        thisUser.setName(name);
    }

    @Override
    public String getId() {
        return thisUser.id;
    }

    @Override
    public void setId(String id) {
        thisUser.setId(id);
    }




}
