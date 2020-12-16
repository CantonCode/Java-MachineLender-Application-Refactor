package sample.Home.Logic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import sample.AlertBox;
import sample.Authentication.Logic.FileManager;
import sample.Authentication.Logic.LoginController;
import sample.Authentication.Logic.RegisterController;
import sample.Home.Model.Machine;
import sample.Home.Model.MachineFactory;
import sample.Main;
import sample.Runner.IAdapter;
import sample.Statics;

import javax.crypto.Mac;
import java.awt.desktop.SystemEventListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class AddUserController implements IAdapter {

    @FXML
    private TextField nameText;

    @FXML
    private TextField password;
    @FXML
    private TextField userName;

    @FXML
    private ComboBox accountType;


    @Override
    public void init() {
        ArrayList<String> account = new ArrayList<String>();
        account.add("User");
        account.add("Admin");
        ObservableList<String> accounts = FXCollections.observableArrayList("User", "Admin");
        accountType.setItems(accounts);
        //ObservableList<String> options = FXCollections.observableArrayList(option);

    }

    @Override
    public void custom(Object... args) {
    }

    public void onReturn(ActionEvent actionEvent) {
        try {
            Main.currentStage.setFXMLScene("Home/UI/adminHome.fxml", new AdminHomeController());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onAdd(ActionEvent actionEvent) {
        try {
            String username = userName.getText();
            String name = nameText.getText();
            String pw = password.getText();
            String type = accountType.getValue().toString();
            RegisterController rc = new RegisterController();
            if (type.equals("Admin")) {
                rc.manualAdmin(name, username, pw);
            } else if (type.equals("User")) {
                rc.manualUser(name, username, pw);
            } else {
                AlertBox.display("Account Type Error", "Please select the type of account you want to create!");
            }
            Main.currentStage.setFXMLScene("Authentication/UI/login.fxml", new LoginController());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
