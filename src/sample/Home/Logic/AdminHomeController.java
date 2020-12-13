package sample.Home.Logic;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import sample.Authentication.Logic.FileManager;
import sample.Authentication.Logic.LoginController;
import sample.Authentication.Model.User;
import sample.Main;
import sample.Runner.IAdapter;

import java.io.IOException;
import java.util.ArrayList;

public class AdminHomeController implements IAdapter {

    public void onSettings(MouseEvent mouseEvent) {
        ArrayList<User> users= new ArrayList<>();
        new FileManager().serializeToFile("currentUser.ser",users);
        try {
            Main.currentStage.setFXMLScene("Authentication/UI/login.fxml",new LoginController());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void viewBorrowedMachines(ActionEvent actionEvent) {
    }

    public void viewCatalog(ActionEvent actionEvent) {
    }

    @Override
    public void init() {

    }

    @Override
    public void custom(Object... args) {

    }
}
