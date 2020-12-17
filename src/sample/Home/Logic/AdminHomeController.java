package sample.Home.Logic;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import sample.Authentication.Logic.FileManager;
import sample.Authentication.Logic.LoginController;
import sample.Authentication.Model.User;
import sample.Main;
import sample.Runner.IAdapter;
import sample.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/*
    Class for logic of admin home page
 */
public class AdminHomeController implements IAdapter {

    public Label unameField;
    private FileManager io=new FileManager();

    @Override
    public void init() {
       if (Statics.CurrentUser!=null) {
           loadUsers();
           unameField.setText(Statics.CurrentUser.getUsername()+"("+Statics.CurrentUser.getType().name().toLowerCase()+")");
        }
    }

    private void loadUsers() {
        Statics.Users.clear();

        Arrays.asList("AdminDB.ser","CustomerDB.ser").forEach(path-> {
            try {
                System.out.println(path);
                io.readSerializedFile((String)path,"users");
                Statics.Users.addAll(io.users);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void custom(Object... args) {
    }

    /*
        View machines button
     */
    public void viewMachines(ActionEvent actionEvent) {
        try {
            Main.currentStage.setFXMLScene("Home/UI/catalog.fxml", new ViewCatalogController());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /*
        Clicking settings icon will bring back to login page
     */
    public void onSettings(MouseEvent mouseEvent) {
        ArrayList<User> users= new ArrayList<>();
        new FileManager().serializeToFile("currentUser.ser",users);
        try {
            Main.currentStage.setFXMLScene("Authentication/UI/login.fxml",new LoginController());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
        Manage machines button
     */
    public void manageMachine(ActionEvent actionEvent) {
        try {
            Main.currentStage.setFXMLScene("Home/UI/addMachine.fxml", new AddMachineController());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /*
        Manage customers button
     */
    public void manageCustomer(ActionEvent actionEvent) {
        try {
            Main.currentStage.setFXMLScene("Home/UI/manage_customers.fxml", new ViewCatalogController());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
