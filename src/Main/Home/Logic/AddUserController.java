package Main.Home.Logic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import Main.Authentication.Logic.LoginController;
import Main.Authentication.Logic.RegisterController;
import Main.Command.NavigationInvoker;
import Main.Command.Previous;
import Main.Main;
import Main.InventoryHelper.IAdapter;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

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
        new NavigationInvoker(new Previous(Main.currentStage)).activate();
    }

    /*
        onAdd action creates a user using inputted information then brings back to login
     */
    public void onAdd(ActionEvent actionEvent) {
        try {
            String username = userName.getText();
            String name = nameText.getText();
            String pw = password.getText();
            String type = accountType.getValue().toString();
            RegisterController rc = new RegisterController();
            //if else statements to check if account type is valid

                if(type.equals("Admin")) {
                    rc.manualAdmin(name, username, pw);
                } else if (type.equals("User")) {
                    rc.manualUser(name, username, pw);
                }
                Main.currentStage.setFXMLScene("Authentication/UI/login.fxml", new LoginController());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
