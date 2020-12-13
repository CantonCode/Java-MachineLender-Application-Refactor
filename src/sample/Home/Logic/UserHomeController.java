package sample.Home.Logic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

public class UserHomeController implements IAdapter {

    private FileManager io = new FileManager();

    @FXML
    public Label unameField;

    public Button borrowedBtn;
    public Button viewCatalogBtn;

    @Override
    public void init() {
        if(Statics.CurrentUser!=null){
            loadUsers();
            unameField.setText(Statics.CurrentUser.getUsername()+"("+Statics.CurrentUser.getType().name().toLowerCase()+")");
        }
    }
    public void loadUsers(){
        Arrays.asList("AdminDB.ser","CustomerDB.ser").forEach(path->{
            try {
                System.out.println(path);
                io.readSerializedFile((String)path,"users");
                Statics.Users.addAll(io.users);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void onSettings(MouseEvent mouseEvent) {
        ArrayList<User> users= new ArrayList<>();
        new FileManager().serializeToFile("currentUser.ser",users);
        try {
            Main.currentStage.setFXMLScene("Authentication/UI/login.fxml",new LoginController());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void viewCatalog(ActionEvent actionEvent){
        try {
            Main.currentStage.setFXMLScene("Home/UI/catalog.fxml", new ViewCatalogController());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public void viewBorrowedMachines(ActionEvent actionEvent) {
        try {
            Main.currentStage.setFXMLScene("Home/UI/borrowedItems.fxml", new BorrowedItemsController());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void custom(Object... args) {

    }
}
