package Main.Facade;

import Main.Authentication.Logic.FileManager;
import Main.Authentication.Model.User;

import java.io.IOException;
import java.util.ArrayList;


public class RegisteredInfo {
    FileManager io = new FileManager();
    ArrayList<User> users = new ArrayList<>();

    public ArrayList<User> loadInfo()
    {
        try {
            io.readSerializedFile("AdminDB.ser", "users");
            users.addAll(io.users);
            io.readSerializedFile("CustomerDB.ser", "users");
            users.addAll(io.users);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this.users;
    }
}
