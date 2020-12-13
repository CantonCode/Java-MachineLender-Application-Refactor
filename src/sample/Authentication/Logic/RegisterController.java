package sample.Authentication.Logic;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import sample.Authentication.Model.AccountType;
import sample.Authentication.Model.Admin;
import sample.Authentication.Model.Customer;
import sample.Authentication.Model.User;
import sample.Main;
import sample.Runner.IAdapter;
import sample.Statics;

import java.io.*;

import java.net.URL;
import java.util.*;

public class RegisterController implements IAdapter {

    public Label infoLabel;
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
    User regUser;
    AccountType accountType;
    ArrayList<User>users=new ArrayList<>();
    FileManager io=new FileManager();

    public void setAccountType(AccountType type){
        this.accountType = type;
    }

    public void manualAdmin (String Name, String Username, String Password){
        String time = String.valueOf(System.currentTimeMillis());

        User origin = new Admin(time, Name, Username, Password, AccountType.ADMIN);
        origin.encryptPassword();
        users.add(origin);
        io.serializeToFile("AdminDB.ser", users);
    }

    public void registerButtonOnAction(ActionEvent event) throws IOException {

        if(validate(firstnameField.getText(), regUsenameField.getText(), regPasswordField.getText())) {
            new PasswordValidator(1).Validate(regPasswordField.getText());
            if(this.accountType==AccountType.ADMIN)
                this.registerAsAdmin();
            else
                this.registerAsCustomer();

            infoLabel.setText("Logged In as: "+regUsenameField.getText());
            passwordHint.setStyle("-fx-text-fill: #93bf6c;");
            ArrayList<User> users= new ArrayList<>();
            users.add(Statics.CurrentUser);
            io.serializeToFile("currentUser.ser",users);
            Main.currentStage.setFXMLScene("Home/UI/home.fxml",new LoginController());


        }else{
            infoLabel.setText("Registration Has Failed");
            passwordHint.setStyle("-fx-text-fill: red;");
        }

    }
    private void  registerAsCustomer(){
        String time = String.valueOf(System.currentTimeMillis());
        regUser = new Customer(time, firstnameField.getText(), regUsenameField.getText(), regPasswordField.getText(),this.accountType);

        regUser.encryptPassword();

        users.add(regUser);
        Statics.CurrentUser=regUser;

        io.serializeToFile("CustomerDB.ser",users);
    }
    private void registerAsAdmin() {
        String time = String.valueOf(System.currentTimeMillis());
        regUser = new Admin(time, firstnameField.getText(), regUsenameField.getText(), regPasswordField.getText(),this.accountType);

        regUser.encryptPassword();

        users.add(regUser);
        Statics.CurrentUser=regUser;
        io.serializeToFile("AdminDB.ser",users);
    }


    public void loginButtonOnAction(ActionEvent event) throws IOException {
        // Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Main.currentStage.setFXMLScene("Authentication/UI/login.fxml",new LoginController());

    }

    private boolean validate(String name, String username, String password){
        boolean valid =true;
        if(new PasswordValidator(1).Validate(password)){
            passwordHint.setText("Strong");
            passwordHint.setStyle("-fx-text-fill: green;");

        }else{
            passwordHint.setText("Weak");
            passwordHint.setStyle("-fx-text-fill: red;");
            valid=false;
        }
        if(uniqueUsername(username)){
            usernameHint.setText("Available");
            usernameHint.setStyle("-fx-text-fill: green;");

        }else{
            usernameHint.setText("Not Available");
            usernameHint.setStyle("-fx-text-fill: red;");
            valid=false;
        }

        return valid;
    }

    public boolean uniqueUsername(String username){
        //check text file to see if the specified log in exists already

        long matches=0;
        for(User u:users){
            System.out.println(username);
            if(u.getUsername().equals(username))
                matches++;
        }
        System.out.println(matches);
        return matches<1;
    }

    public void registerOnManageUsers(ActionEvent actionEvent) {

    }

    @Override
    public void init() {

        try {
            io.readSerializedFile("AdminDB.ser","users");
            users.addAll(io.users);
            io.readSerializedFile("CustomerDB.ser","users");
            users.addAll(io.users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void custom(Object... args) {
        if(args[0] instanceof AccountType){
            setAccountType((AccountType) args[0]);
        }
    }
}
