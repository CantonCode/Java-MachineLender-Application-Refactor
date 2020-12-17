package sample.Authentication.Logic;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import sample.AlertBox;
import sample.Authentication.Model.AccountType;
import sample.Authentication.Model.User;
import sample.Home.Logic.AdminHomeController;
import sample.Home.Logic.UserHomeController;
import sample.Home.Logic.ViewCatalogController;
import sample.Home.Model.Machine;
import sample.Main;
import sample.Navigation;
import sample.Runner.IAdapter;
import sample.Runner.Logic.LenderController;
import sample.Statics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/*
    Class for the logic necessary for the login page.
 */
public class LoginController implements Initializable, IAdapter {

    @FXML
    private Button loginButton, registerPageButton;
    @FXML
    private Label messager;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    String userSname, userId;


    ArrayList<User>users=new ArrayList<>();
    ArrayList<Machine>machines=new ArrayList<>();
    FileManager io=new FileManager();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    private void loadSceneAndSendInfo() {

    }

    public LoginController() {
    }

    public void loginButtonOnAction(ActionEvent event) throws IOException {

    }

    public boolean validateLogin(String username, String password) throws IOException {
        //check text file to see if the specified log in exists already
        return false;
    }

    public void registerButtonOnAction(ActionEvent actionEvent)throws IOException {
    }

    @Override
    public void init() {

    }

    @Override
    public void custom(Object... args) {
    }
}

