package Main.Facade;

import Main.Authentication.Logic.PasswordValidator;
import Main.Authentication.Model.User;

import java.util.ArrayList;

public class RegistrationValidator {

    Boolean[] validation = new Boolean[2];

    public Boolean[] validate(String username, String password, ArrayList<User> users)
    {
        Boolean valPass = new PasswordValidator(1).Validate(password);
        Boolean valName = uniqueUsername(username, users);

        validation[0] = valName;
        validation[1] = valPass;

        return validation;
    }

    public boolean uniqueUsername(String username, ArrayList<User> users) {
        int matches = 0;
        for (User u : users)
        {
            if (u.getUsername().equals(username))
                matches++;
        }

        Boolean valUniqueUserName = matches < 1;

        return valUniqueUserName;
    }

    public int valType(Boolean[] valid)
    {
        if(valid[0] && !valid[1])  //0 is username validation, 1 is password validation
        {
            System.out.println("password failed!!!!!!!!!!");
            return 3;
        }
        else if(!valid[0] && valid[1])
        {
            System.out.println("name is not unique!!!!!!!!!!");
            return 2;
        }
        else if(valid[0] && valid[1])
        {
            //pass
            System.out.println("SUCESSS!!!!!!!!!!");
            return 1;
        }
        else
        {
            System.out.println("FAILED BOTH?????!!!!!!!!!!");
            return -1;
        }
    }
}
