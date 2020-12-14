package sample.Home.Logic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import sample.Authentication.Logic.FileManager;
import sample.Authentication.Logic.LoginController;
import sample.Authentication.Model.User;
import sample.Home.Model.Machine;
import sample.Home.Model.MachineAdapter;
import sample.Main;
import sample.Runner.IAdapter;
import sample.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BorrowedItemsController implements IAdapter {

    public TableColumn machineCol;
    public TableColumn dueCol;
    public TableView<MachineAdapter> returnView;
    private FileManager io = new FileManager();
    List<MachineAdapter> machines = new ArrayList<>();
    ArrayList<User> users = new ArrayList<>();
    public Label unameField;

    @Override
    public void init() {
        if (Statics.CurrentUser != null) {
            loadUsers();
            unameField.setText(Statics.CurrentUser.getUsername() + "(" + Statics.CurrentUser.getType().name().toLowerCase() + ")");

            Arrays.asList("CustomerDB.ser").forEach(path->{
                try {
                    io.readSerializedFile((String)path,"users");
                   users.addAll(io.users);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            for (Machine u : Statics.CurrentUser.getCurrRentals().stream().collect(Collectors.toList())) {
                machines.add(new MachineAdapter(u));
            }

            final ObservableList<MachineAdapter> data = FXCollections.observableList(machines);
            machineCol.setCellValueFactory(
                    new PropertyValueFactory<MachineAdapter, String>("name"));
            dueCol.setCellValueFactory(
                    new PropertyValueFactory<MachineAdapter, String>("type"));


            returnView.setItems(data);


        }

    }

    public void loadUsers() {
        Arrays.asList("AdminDB.ser", "CustomerDB.ser").forEach(path -> {
            try {
                System.out.println(path);
                io.readSerializedFile((String) path, "users");
                Statics.Users.addAll(io.users);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void custom(Object... args) {
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

    public void onReturn(ActionEvent actionEvent) {
        MachineAdapter mac = returnView.getSelectionModel().getSelectedItem();
        String selectedName = mac.getName();
        System.out.println(mac.getName());
        ArrayList<Machine> currRentals = Statics.CurrentUser.getCurrRentals();
        System.out.println(currRentals);
        Boolean removed = false;


        for (int i = 0; i < currRentals.size(); i++)
            if (currRentals.get(i).getName().equals(selectedName)) {

                if (!removed) {
                    currRentals.remove(i);
                    removed = true;
                }
            }

        for (User u : Statics.Users) {
            if (u.getUsername().equals(Statics.CurrentUser.getUsername())) {
                System.out.println("CURRENT USER FOUND IN DB");
                u.setCurrRentals(currRentals);
                Statics.CurrentUser.setCurrRentals(currRentals);
                System.out.println(u);

                ArrayList<User> user = new ArrayList<>();
                user.add(Statics.CurrentUser);

                io.serializeToFile("CustomerDB.ser", Statics.Users);
                io.serializeToFile("currentUser.ser", user);


//
                System.out.println(currRentals);
            }
        }
    }









    public void goBack(ActionEvent actionEvent) {
        try {
            Main.currentStage.setFXMLScene("Home/UI/userHome.fxml",new UserHomeController());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
