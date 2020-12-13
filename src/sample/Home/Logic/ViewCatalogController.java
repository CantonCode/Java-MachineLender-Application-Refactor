package sample.Home.Logic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import sample.Authentication.Logic.FileManager;
import sample.Authentication.Logic.LoginController;
import sample.Authentication.Model.UserAdapter;
import sample.Home.Model.Machine;
import sample.Home.Model.MachineAdapter;
import sample.Main;
import sample.Runner.IAdapter;
import sample.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewCatalogController implements IAdapter {

    @FXML
    public Label unameField;
    public TableColumn idCol;
    public TableColumn nameCol;
    public TableColumn typeCol;
    public TableColumn stockCol;
    public TableView catView;
    private FileManager io=new FileManager();

    @Override
    public void init() {
        if(Statics.CurrentUser!=null){
            loadUsers();
            unameField.setText(Statics.CurrentUser.getUsername()+"("+Statics.CurrentUser.getType().name().toLowerCase()+")");
        }

        List<MachineAdapter> machines=new ArrayList<>();

    }

    @Override
    public void custom(Object... args) {

    }

    public void onSettings(MouseEvent mouseEvent) {
    }

    public void borrowBtn(ActionEvent actionEvent) {
    }

    public void loadUsers(){
        Arrays.asList("AdminDB.ser","CustomerDB.ser").forEach(path->{
            try {
                System.out.println(path);
                io.readSerializedFile((String)path, "users");
                Statics.Users.addAll(io.users);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void onReturn(ActionEvent actionEvent) {
        try {
            Main.currentStage.setFXMLScene("Home/UI/userHome.fxml",new UserHomeController());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
