package sample.Home.Logic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import sample.Authentication.Logic.FileManager;
import sample.Authentication.Model.AccountType;
import sample.Authentication.Model.User;
import sample.Home.Model.Machine;
import sample.Home.Model.MachineAdapter;
import sample.Home.Model.MachineFactory;
import sample.Main;
import sample.Runner.IAdapter;
import sample.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ViewCatalogController implements IAdapter {

    @FXML
    public Label unameField;
    public TableColumn idCol;
    public TableColumn nameCol;
    public TableColumn typeCol;
    public TableColumn stockCol;
    public TableView<MachineAdapter> catView;
    public Label itemLabel;
    private FileManager io=new FileManager();
    List<MachineAdapter> machines=new ArrayList<>();

    @Override
    public void init() {
        if(Statics.CurrentUser!=null){
            unameField.setText(Statics.CurrentUser.getUsername()+"("+Statics.CurrentUser.getType().name().toLowerCase()+")");
        }


        for(Machine u : Statics.Machines.stream().collect(Collectors.toList())){
            machines.add(new MachineAdapter(u));
        }

        final ObservableList<MachineAdapter> data = FXCollections.observableList(machines);
        idCol.setCellValueFactory(
                new PropertyValueFactory<MachineAdapter, String>("id"));
        nameCol.setCellValueFactory(
                new PropertyValueFactory<MachineAdapter, String>("name"));
        typeCol.setCellValueFactory(
                new PropertyValueFactory<MachineAdapter, String>("type"));


        catView.setItems(data);

        System.out.println(machines);



    }

    @Override
    public void custom(Object... args) {

    }

    public void onSettings(MouseEvent mouseEvent) {
    }

    public void borrowBtn(ActionEvent actionEvent) {
        MachineAdapter mac = catView.getSelectionModel().getSelectedItem();
        String selectedName = mac.getName();
        System.out.println(mac.getName());

        for(Machine m : Statics.Machines.stream().collect(Collectors.toList())){
            String listName = m.getName();

            if (listName.equals(selectedName)){
                //Need to subtract the quantity of the item available
                //Need to add the item to the user array

                for(User u : Statics.Users){
                    if(u.getUsername().equals(Statics.CurrentUser.getUsername())){
                        System.out.println("CURRENT USER FOUND IN DB");
                        ArrayList<Machine> currRentals = u.getCurrRentals();

                        if(currRentals.size() < 5) {
                            currRentals.add(m);
                            u.setCurrRentals(currRentals);
                            Statics.CurrentUser.setCurrRentals(currRentals);
                            System.out.println(u);

                            ArrayList<User> user= new ArrayList<>();
                            user.add(Statics.CurrentUser);

                            io.serializeToFile("CustomerDB.ser",Statics.Users);
                            io.serializeToFile("currentUser.ser", user);
                        }else{
                            System.out.println("YOU HAVE TOO MANY ITEMS RENTED");
                        }
                    }
                }

                for(User u : Statics.Users){
                    System.out.println(u);
                }


        }


        }



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
        try {/*Needs if statement to determine admin or normal user*/

            if(Statics.CurrentUser.getType() == AccountType.CUSTOMER){
                    Main.currentStage.setFXMLScene("Home/UI/userHome.fxml", new UserHomeController()); //test 2 Home/UI/adminHome.fxml
                }else {
                Main.currentStage.setFXMLScene("Home/UI/adminHome.fxml", new AdminHomeController()); //test 2
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
