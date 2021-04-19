package Main.Authentication.Logic;

import Main.Authentication.Model.*;
import Main.Facade.RegistrationFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import Main.AlertBox;
import Main.Home.Model.Machine;
import Main.Main;
import Main.InventoryHelper.IAdapter;
import Main.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

/*
    Class for logic required to run register page.
 */
public class RegisterController implements IAdapter {

    public Label infoLabel;
    User regUser;
    AccountType accountType;
    ArrayList<User> users = new ArrayList<>();
    FileManager io = new FileManager();
    ArrayList<Machine> emptyMac = new ArrayList<>();
    @FXML
    private TextField firstnameField;
    @FXML
    private PasswordField regPasswordField;
    @FXML
    private TextField regUsenameField;
    @FXML
    private Button registerButton, loginButton;
    @FXML
    private Label passwordHint;
    @FXML
    private Label usernameHint;
    @FXML
    private Label msgr;

    public void setAccountType(AccountType type) {
        this.accountType = type;
    }

    /*
        Class for manual creation of admin account
     */
    public void manualAdmin(String Name, String Username, String Password) {
        String time = String.valueOf(System.currentTimeMillis());
        User origin = new AdminBuilder().setId(time).setName(Name).setUsername(Username).setPassword(Password).setAccountType(AccountType.ADMIN).setCurr(emptyMac).createAdmin();
        origin.encryptPassword();
        users.add(origin);

        users.stream().filter(u -> u.getType() == AccountType.ADMIN);
        io.serializeToFile("AdminDB.ser", users);
    }

    /*
        Register button which stores user information to the currentUser.ser file and then brings the new user
        to the user home page
     */

    public void registerButtonOnAction(ActionEvent event) throws IOException {
        //if else either creates user if all fields are filled or throws an error message

        RegistrationFacade rf = new RegistrationFacade(); //Facade pattern
        rf.register(firstnameField.getText(), regUsenameField.getText(), regPasswordField.getText(), AccountType.CUSTOMER);

        switch(rf.getValType())
        {
            case -1:
                //both failed
                AlertBox.display("Password Error: -1", "     Err-1: Password & Name are inputted incorrectly;     \n     password must be a set of alpha-numeric values greater than 4     \n     Username already in user.     ");
                break;
            case 1:
                // sucess
                Main.currentStage.setFXMLScene("Home/UI/"+(this.accountType==AccountType.CUSTOMER?"userHome":"adminHome")+".fxml", new LoginController());
                break;
            case 2:
                // name failed
                AlertBox.display("Username Error: 2", "     Err2: Username already in user.     ");
                break;
            case 3:
                // password failed
                AlertBox.display("Password Error: 3", "     Err3: password must be a set of alpha-numeric values greater than 4.     ");
        }
    }


    public void loginButtonOnAction(ActionEvent event) throws IOException {
        // Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Main.currentStage.setFXMLScene("Authentication/UI/login.fxml", new RegisterController());
    }


    public void registerOnManageUsers(ActionEvent actionEvent) {
    }

    @Override
    public void init() {
        try {
            io.readSerializedFile("AdminDB.ser", "users");
            users.addAll(io.users);
            io.readSerializedFile("CustomerDB.ser", "users");
            users.addAll(io.users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void custom(Object... args) {
        if (args[0] instanceof AccountType) {
            setAccountType((AccountType) args[0]);
        }
    }
}
