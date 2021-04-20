package Main.Facade;

import Main.Authentication.Model.AccountType;
import Main.Authentication.Model.User;
import Main.Statics;

import java.awt.*;
import java.util.ArrayList;

public class RegistrationFacade {

    Boolean[] validation = new Boolean[2];
    ArrayList<User> users = new ArrayList<>();
    User currentUser;
    int valType;

    public int getValType(){
        return valType;
    }

    public void register(String name, String username, String password, String email, AccountType TYPE)
    {
        RegistrationValidator rv = new RegistrationValidator();
        RegistrationRegister rr = new RegistrationRegister();
        RegisteredInfo ri = new RegisteredInfo();
        

        //load user database
        users = ri.loadInfo();
        System.out.println("THIS IS THE STATE OF LOADED USERS!: " + users);

        //validate user info
        validation = rv.validate(username,password,email,users);
        valType = rv.valType(validation); //returns whether or not the validation passed and or where it failed

        // store user
//        if(valType == 1)
//        {
//            //sucesfully registered user
//            users = rr.Register(name, username, password, TYPE, users);
//            currentUser = users.get(users.size() - 1);
//
//
//            //return true;
//        }
//        else
//        {
//            //failed to create user! check the valType to see what failure it was, do something <-
//            //return false;
//        }
    }
}
