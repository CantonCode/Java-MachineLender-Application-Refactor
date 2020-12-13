package sample.Home.Logic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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

public class Home implements IAdapter {
    @FXML
    public Label unameField;
    @FXML
    public ImageView settingsButton;
    public Button manageCustomer;

    private FileManager io=new FileManager();

    @Override
    public void init() {

        if(Statics.CurrentUser!=null){
            loadUsers();
            unameField.setText(Statics.CurrentUser.getUsername()+"("+Statics.CurrentUser.getType().name().toLowerCase()+")");
        }
        manageCustomer.setOnAction(e->{
            try {
                Main.currentStage.setFXMLScene("Home/UI/manage_customers.fxml",new ManageCustomers());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

    }

    @Override
    public void custom(Object... args) {

    }
    public void loadUsers(){
        Arrays.asList("AdminDB.ser","CustomerDB.ser").forEach(path->{
            try {
                System.out.println(path);
                io.readSerializedFile((String)path);
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


}
