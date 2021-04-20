package Main.Authentication.Logic;

import Main.Authentication.Model.*;
import Main.Facade.RegisteredInfo;
import Main.Facade.RegistrationFacade;
import Main.Facade.RegistrationRegister;
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
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.*;

import java.util.stream.Collectors;

/*
    Class for logic required to run register page.
 */
public class RegisterController implements IAdapter {

    public Label infoLabel;
    private static final String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";


    User regUser;
    String otp = "";
    AccountType accountType;
    ArrayList<User> users = new ArrayList<>();
    FileManager io = new FileManager();
    ArrayList<Machine> emptyMac = new ArrayList<>();
    RegistrationRegister rr = new RegistrationRegister();
    RegistrationFacade rf = new RegistrationFacade(); //Facade pattern
    User currentUser;

    String usernameS = "";
    String nameS = "";
    String passwordS = "";
    AccountType typeS;

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
    private Label sentAddress;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private AnchorPane OTPPane;
    @FXML
    private TextField userEmail;
    @FXML
    private TextField confirmOTP;





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
    public void registerButtonOnAction(ActionEvent event) throws IOException, MessagingException, ParseException {

        nameS = firstnameField.getText();
        usernameS = regUsenameField.getText();
        passwordS = regPasswordField.getText();
        typeS = AccountType.CUSTOMER;

        rf.register(nameS, usernameS, passwordS,userEmail.getText(),typeS);

        switch(rf.getValType())
        {
            case -1:
                //both failed
                AlertBox.display("Password Error: -1", "     Err-1: Password & Name are inputted incorrectly;     \n     password must be a set of alpha-numeric values greater than 4     \n     Username already in user.     ");
                usernameHint.setText("Not Available");
                usernameHint.setStyle("-fx-text-fill: red;");

                passwordHint.setText("Weak");
                passwordHint.setStyle("-fx-text-fill: red;");
                break;
            case 1:
                // sucess

                //Main.currentStage.setFXMLScene("Home/UI/"+(this.accountType==AccountType.CUSTOMER?"userHome":"adminHome")+".fxml", new LoginController());
                break;
            case 2:
                // name failed
                AlertBox.display("Username Error: 2", "     Err2: Username already in user.     ");
                usernameHint.setText("Not Available");
                usernameHint.setStyle("-fx-text-fill: red;");
                break;
            case 3:
                // password failed
                passwordHint.setText("Weak");
                passwordHint.setStyle("-fx-text-fill: red;");
                AlertBox.display("Password Error: 3", "     Err3: password must be a set of alpha-numeric values greater than 4.     ");
                break;
            case 4:
                AlertBox.display("Password Error: 4", "     email invalid     ");
                break;
            case 5:
                AlertBox.display("Valid Credentials", " Please continue onto OTP ");
                sendOTP();
        }

    }

    public void OTPButtonOnAction(ActionEvent event) throws IOException, MessagingException, ParseException {
    String userOTP = confirmOTP.getText();
    System.out.println(otp);
    System.out.println(userOTP);

    if(userOTP != " "){
        if(userOTP.equals(otp)){
            System.out.print("VALID OTP");
            AlertBox.display("SUCCESS", "Registration Complete");

            users = rr.Register(nameS, usernameS, passwordS, typeS, users);
            currentUser = users.get(users.size() - 1);

            Main.currentStage.setFXMLScene("Home/UI/"+(this.accountType==AccountType.CUSTOMER?"userHome":"adminHome")+".fxml", new LoginController());

        } else {
            AlertBox.display("Error", "Invalid OTP please try again");

//
    }
}

    }

    /*
        Method to create customer account types
     */
    private void sendOTP() throws MessagingException {
        mainPane.setVisible(false);

        String receipt = userEmail.getText();

        otp = OTPHelper.generateDigit();
        OTPHelper.sendEmail(receipt,otp);

        sentAddress.setText(receipt);
        OTPPane.setVisible(true);
    }


    /*
        Method to create admin account types
     */

    /*
        Login button
     */
    public void loginButtonOnAction(ActionEvent event) throws IOException, ParseException {
        // Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Main.currentStage.setFXMLScene("Authentication/UI/login.fxml", new RegisterController());
    }
    // File Name SendEmail.java


    public boolean validate(String name, String username, String password,String email) {
        boolean valid = true;
        Pattern pattern = Pattern.compile(regex);

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
            AlertBox.display("Username Error", "User name must not be blank");
            valid = false; }

        Matcher matcher = pattern.matcher(email);




        return valid;
    }



    /*
        Method checks if username entered is unique
     */
    public boolean uniqueUsername(String username) {
        long matches = 0;
        if(username.isEmpty()){
            return false;
        }
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
            OTPPane.setVisible(false);
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
