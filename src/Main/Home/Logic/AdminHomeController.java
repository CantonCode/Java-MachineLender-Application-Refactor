package Main.Home.Logic;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import Main.Authentication.Logic.FileManager;
import Main.Authentication.Logic.LoginController;
import Main.Authentication.Model.User;
import Main.Main;
import Main.InventoryHelper.IAdapter;
import Main.Statics;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

/*
    Class for logic of admin home page
 */
public class AdminHomeController implements IAdapter {

    public Label unameField;
    private final FileManager io=new FileManager();

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
                io.readSerializedFile(path,"users");
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
        } catch (IOException | ParseException ex) {
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
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    /*
        Manage machines button
     */
    public void manageMachine(ActionEvent actionEvent) {
        try {
            Main.currentStage.setFXMLScene("Home/UI/addMachine.fxml", new AddMachineController());
        } catch (IOException | ParseException ex) {
            ex.printStackTrace();
        }
    }

    /*
        Manage customers button
     */
    public void manageCustomer(ActionEvent actionEvent) {
        try {
            Main.currentStage.setFXMLScene("Home/UI/manage_customers.fxml", new ViewCatalogController());
        } catch (IOException | ParseException ex) {
            ex.printStackTrace();
        }
    }
}
