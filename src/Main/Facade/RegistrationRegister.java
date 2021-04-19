package Main.Facade;

import Main.Authentication.Logic.FileManager;
import Main.Authentication.Model.AccountType;
import Main.Authentication.Model.AdminBuilder;
import Main.Authentication.Model.CustomerBuilder;
import Main.Authentication.Model.User;
import Main.Home.Model.Machine;
import Main.Statics;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class RegistrationRegister {

    ArrayList<Machine> Inv = new ArrayList<>();
    ArrayList<User> user = new ArrayList<>();
    FileManager io = new FileManager();
    User regUser;

    public ArrayList<User> Register(String name, String username, String password, AccountType TYPE, ArrayList<User> userT)
    {
        ArrayList<User> users = new ArrayList<>(userT);

        if(TYPE == AccountType.CUSTOMER)
        {
            regUser = new CustomerBuilder().setId(String.valueOf(System.currentTimeMillis())).setName(name).setUsername(username).setPassword(password).setType(TYPE).setCurr(Inv).createCustomer();

            regUser.encryptPassword();

            users.add(regUser);
            Statics.CurrentUser = regUser;
            io.serializeToFile("CustomerDB.ser", users.stream().filter(u -> u.getType() == AccountType.CUSTOMER).collect(Collectors.toList()));

            user.add(regUser);
            io.serializeToFile("currentUser.ser", user);
        }
        else if(TYPE == AccountType.ADMIN)
        {
            String time = String.valueOf(System.currentTimeMillis());
            regUser = new AdminBuilder().setId(String.valueOf(System.currentTimeMillis())).setName(name).setUsername(username).setPassword(password).setAccountType(TYPE).setCurr(Inv).createAdmin();

            regUser.encryptPassword();
            users.add(regUser);
            Statics.CurrentUser = regUser;
            io.serializeToFile("AdminDB.ser", users.stream().filter(u -> u.getType() == AccountType.ADMIN).collect(Collectors.toList()));

            user.add(regUser);
            io.serializeToFile("currentUser.ser", user);
        }

        return users;
    }
}
