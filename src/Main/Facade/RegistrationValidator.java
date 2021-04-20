package Main.Facade;

import Main.AlertBox;
import Main.Authentication.Logic.PasswordValidator;
import Main.Authentication.Model.User;

import java.awt.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationValidator {

    Boolean[] validation = new Boolean[3];
    private static final String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

    public Boolean[] validate(String username, String password,String email,ArrayList<User> users)
    {
        Boolean valPass = new PasswordValidator(1).Validate(password);
        Boolean valName = uniqueUsername(username, users);
        Boolean valEmail = validateEmail(email);

        validation[0] = valName;
        validation[1] = valPass;
        validation[2] = valEmail;

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

    public boolean validateEmail(String email){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if(matcher.matches()){
            System.out.println("MATCHES");
            return true;
        }else{
            System.out.println("NOT MATCHING");
            return false;
        }

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
        else if(valid[0] && valid[1] && valid[2])
        {
            //pass
            System.out.println("SUCESSS!!!!!!!!!!");
            return 5;
        }
        else if(!valid[2]){
            System.out.println("FAILED EMAIL VALIDATION!!!!!!!!!!");
            return 4;
        }
        else
        {
            System.out.println("FAILED BOTH?????!!!!!!!!!!");
            return -1;
        }
    }
}
