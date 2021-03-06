package sample.Home.Logic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import sample.AlertBox;
import sample.Authentication.Logic.FileManager;
import sample.Authentication.Logic.LoginController;
import sample.Authentication.Model.User;
import sample.Command.NavigationInvoker;
import sample.Command.Previous;
import sample.Home.Model.Machine;
import sample.Home.Model.MachineAdapter;
import sample.IMethod;
import sample.Main;
import sample.Runner.IAdapter;
import sample.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*
    Class implements logic of borrowed items page.
 */
public class BorrowedItemsController implements IAdapter {

    public TableColumn machineCol;
    public TableColumn dueCol;
    public TableView<MachineAdapter> returnView;
    public TableColumn idCol;
    public TableColumn nameCol;
    public TableColumn typeCol;
    public TableColumn costPerDayCol;
    public TableColumn invCol;
    public Button cancelBtn;
    private FileManager io = new FileManager();
    List<MachineAdapter> machines = new ArrayList<>();
    ArrayList<User> users = new ArrayList<>();
    public Label unameField;

    @Override
    public void init() {
        if (Statics.CurrentUser != null) {
            loadUsers();
            unameField.setText(Statics.CurrentUser.getUsername() + "(" + Statics.CurrentUser.getType().name().toLowerCase() + ")");

            Arrays.asList("CustomerDB.ser").forEach(path-> {
                try {
                    io.readSerializedFile((String)path,"users");
                   users.addAll(io.users);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

          cancelBtn.setOnAction(e->{
              new NavigationInvoker(new Previous(Main.currentStage)).activate();
          });

            for (Machine u : Statics.CurrentUser.getCurrRentals().stream().collect(Collectors.toList())) {
                machines.add(new MachineAdapter(u));
            }

            final ObservableList<MachineAdapter> data = FXCollections.observableList(machines);
            idCol.setCellValueFactory(new PropertyValueFactory<MachineAdapter, String>("id"));
            nameCol.setCellValueFactory(new PropertyValueFactory<MachineAdapter, String>("name"));
            typeCol.setCellValueFactory(new PropertyValueFactory<MachineAdapter, String>("type"));
            costPerDayCol.setCellValueFactory(new PropertyValueFactory<MachineAdapter, String>("costPerDay"));
            invCol.setCellValueFactory(new PropertyValueFactory<MachineAdapter, String>("inventory"));
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

    /*
        clicking settings icon brings back to login
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
    int rowName=0;
    String selectedRow="";
    public void onReturn(ActionEvent actionEvent) {
        selectedRow= returnView.getSelectionModel().getSelectedItem().getName();
        class Borrow implements IMethod {

            @Override
            public void execute() {
                if (AlertBox.DISPLAY_QUESTION_ANSWER) {
                    MachineAdapter mac = returnView.getSelectionModel().getSelectedItem();
                    String selectedName = mac.getName();
                    selectedRow=selectedName;
                    System.out.println("---------");
                    System.out.println(mac.getName());
                    int index=-1;
                    for(int i=0; i< Statics.Machines.size();i++){
                        if(mac.getId().equals(Statics.Machines.get(i).getId()))index=i;
                    }

                    //check if valid
                    int quantity=1;
                    if(AlertBox.DISPLAY_INPUT_TEXT.matches("[0-9]+")){
                        quantity=Integer.parseInt(AlertBox.DISPLAY_INPUT_TEXT);
                        System.out.println((Statics.CurrentUser.getCurrRentals().size()));
                        quantity=Math.min(Statics.CurrentUser.getCurrRentals().get(rowName).getInventory(),quantity);
                        System.out.println("I am returning "+ quantity + ": " +selectedName);
                        System.out.println((Statics.CurrentUser.getCurrRentals().get(rowName).getInventory()-quantity+" Left!!"));
                        Machine m =(Statics.CurrentUser.getCurrRentals().get(rowName));
                        m.setInventory(quantity);


                            AlertBox.display("SUCCESS", String.format("%s\n%-15s:\t%-15s","Thank You For Your Purchase",selectedName,quantity ));
                            if(index>=0)
                        Statics.Machines.get(index).setInventory(Math.max((Statics.CurrentUser.getCurrRentals().get(rowName).getInventory()-quantity), 0));


                        //save machines
                        io.machineSerializeToFile("MachineDB.ser", Statics.Machines);
                        //save user;
                        io.serializeToFile("CustomerDB.ser", Statics.Users);
                        new NavigationInvoker(new Previous(Main.currentStage)).activate();
                        try {
                            Main.currentStage.setFXMLScene("Home/UI/borrowedItems.fxml", new ViewCatalogController());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }


                }
            }
        }

        AlertBox.displayInput("Return "+selectedRow, "How many "+selectedRow+" Would you like to return?", "Confirm", "Cancel","Enter how much you want to return",new Borrow());

    }
    /*
        goback to bring back to userHome UI
     */
    public void goBack(ActionEvent actionEvent) {
        new NavigationInvoker(new Previous(Main.currentStage)).activate();
    }
}
