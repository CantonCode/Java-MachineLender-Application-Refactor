package Main.Authentication.Logic;

import Main.Authentication.Model.*;
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
        io.serializeToFile("AdminDB.ser", users);
    }

    public void manualUser(String Name, String Username, String Password) {
        String time = String.valueOf(System.currentTimeMillis());
        regUser = new CustomerBuilder().setId(time).setName(Name).setUsername(Username).setPassword(Password).setType(AccountType.CUSTOMER).setCurr(emptyMac).createCustomer();

        regUser.encryptPassword();
        System.out.println(regUser);
        users.add(regUser);
        Statics.CurrentUser = regUser;

        io.serializeToFile("currentUser.ser", users);
    }

    /*
        Register button which stores user information to the currentUser.ser file and then brings the new user
        to the user home page
     */
    public void registerButtonOnAction(ActionEvent event) throws IOException {

        //if else either creates user if all fields are filled or throws an error message
        if (validate(firstnameField.getText(), regUsenameField.getText(), regPasswordField.getText())) {
            new PasswordValidator(1).Validate(regPasswordField.getText());
            if (this.accountType == AccountType.ADMIN)
                this.registerAsAdmin();
            else
                this.registerAsCustomer();

            infoLabel.setText("Logged In as: " + regUsenameField.getText());
            passwordHint.setStyle("-fx-text-fill: #93bf6c;");
            ArrayList<User> users = new ArrayList<>();
            users.add(Statics.CurrentUser);
            io.serializeToFile("currentUser.ser", users);
            Main.currentStage.setFXMLScene("Home/UI/"+(this.accountType==AccountType.CUSTOMER?"userHome":"adminHome")+".fxml", new LoginController());
        } else {
            infoLabel.setText("Please fill ALL fields");
            infoLabel.setStyle("-fx-text-fill: red;");
        }
    }

    /*
        Method to create customer account types
     */
    private void registerAsCustomer() {
        String time = String.valueOf(System.currentTimeMillis());
        regUser = new CustomerBuilder().setId(time).setName(firstnameField.getText()).setUsername(regUsenameField.getText()).setPassword(regPasswordField.getText()).setType(this.accountType).setCurr(emptyMac).createCustomer();

        regUser.encryptPassword();

        users.add(regUser);
        System.out.println(regUser);
        Statics.CurrentUser = regUser;

        io.serializeToFile("CustomerDB.ser", users);
    }

    /*
        Method to create admin account types
     */
    private void registerAsAdmin() {
        String time = String.valueOf(System.currentTimeMillis());
        regUser = new AdminBuilder().setId(time).setName(firstnameField.getText()).setUsername(regUsenameField.getText()).setPassword(regPasswordField.getText()).setAccountType(this.accountType).setCurr(emptyMac).createAdmin();

        regUser.encryptPassword();
        System.out.println(regUser);
        users.add(regUser);
        Statics.CurrentUser = regUser;
        io.serializeToFile("AdminDB.ser", users);
    }

    /*
        Login button
     */
    public void loginButtonOnAction(ActionEvent event) throws IOException {
        // Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Main.currentStage.setFXMLScene("Authentication/UI/login.fxml", new LoginController());
    }

    private boolean validate(String name, String username, String password) {
        boolean valid = true;
        if (new PasswordValidator(1).Validate(password)) {
            passwordHint.setText("Strong");
            passwordHint.setStyle("-fx-text-fill: green;");

        } else {
            passwordHint.setText("Weak");
            passwordHint.setStyle("-fx-text-fill: red;");
            AlertBox.display("Password Error", "Password Requires Capital Letter, Lower Case Letter & a Number");
            valid = false;
        }
        if (uniqueUsername(username)) {
            usernameHint.setText("Available");
            usernameHint.setStyle("-fx-text-fill: green;");

        } else {
            usernameHint.setText("Not Available");
            usernameHint.setStyle("-fx-text-fill: red;");
            valid = false;
        }

        return valid;
    }

    /*
        Method checks if username entered is unique
     */
    public boolean uniqueUsername(String username) {
        long matches = 0;
        for (User u : users) {
            System.out.println(username);
            if (u.getUsername().equals(username))
                matches++;
        }
        System.out.println(matches);
        return matches < 1;
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
