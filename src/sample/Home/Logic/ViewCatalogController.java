package sample.Home.Logic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import sample.AlertBox;
import sample.Authentication.Logic.FileManager;
import sample.Authentication.Model.AccountType;
import sample.Authentication.Model.User;
import sample.Command.NavigationInvoker;
import sample.Command.Previous;
import sample.Home.Model.Machine;
import sample.Home.Model.MachineAdapter;
import sample.Authentication.Logic.LoginController;
import sample.Home.Model.MachineFactory;
import sample.IMethod;
import sample.Main;
import sample.Runner.IAdapter;
import sample.Statics;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*
    Class implements logic for view catalog page.
 */
public class ViewCatalogController implements IAdapter {

    @FXML
    public Label unameField;
    public TableColumn idCol;
    public TableColumn nameCol;
    public TableColumn typeCol;
    public TableColumn costPerDayCol;
    public TableView<MachineAdapter> catView;
    public Label itemLabel;
    public TableColumn invCol;
    public Button borrowBtn;
    private FileManager io=new FileManager();
    int rowName=0;
    String selectedRow="";
    List<MachineAdapter> machines=new ArrayList<>();

    @Override
    public void init() {
        if (Statics.CurrentUser!=null) {
            unameField.setText(Statics.CurrentUser.getUsername()+"("+Statics.CurrentUser.getType().name().toLowerCase()+")");
            if(Statics.CurrentUser.getType()==AccountType.CUSTOMER){
                borrowBtn.setText("Borrow Machine");
                itemLabel.setText(selectedRow);
            }
            else{
                borrowBtn.setText("Edit Machine");
                itemLabel.setText("Tip: Press the BACKSPACE button or the X button to delete");
            }
        }

        for (Machine u : Statics.Machines.stream().collect(Collectors.toList())) {
            machines.add(new MachineAdapter(u));
        }

        final ObservableList<MachineAdapter> data = FXCollections.observableList(machines);
        idCol.setCellValueFactory(new PropertyValueFactory<MachineAdapter, String>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<MachineAdapter, String>("name"));
        typeCol.setCellValueFactory(new PropertyValueFactory<MachineAdapter, String>("type"));
        costPerDayCol.setCellValueFactory(new PropertyValueFactory<MachineAdapter, String>("costPerDay"));
        invCol.setCellValueFactory(new PropertyValueFactory<MachineAdapter, String>("inventory"));

        catView.setItems(data);

        System.out.println(machines);
    }

    @Override
    public void custom(Object... args) {
    }

    /*
        Settings icon leads back to login page
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
        Borrow button action checks if a machine is selected from the catalog, if so checks if the current user
        meets requirements for rentals (<5 current rentals) and if so adds it to their current rentals
     */
    public void borrowBtn(ActionEvent actionEvent) throws IOException {
        catView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue)-> {
            if(catView.getSelectionModel().getSelectedItem() != null){
                TableView.TableViewSelectionModel Tv = catView.getSelectionModel();
                ObservableList cells = Tv.getSelectedCells();
                TablePosition tp = (TablePosition) cells.get(0);
                Object val = tp.getTableColumn().getCellData(newValue);

                for(int i = 0; i < Statics.Users.size(); i++){
                    if(Statics.Machines.get(i).getId().equals(val) || Statics.Machines.get(i).getName().equals(val) || Statics.Machines.get(i).getType().equals(val) ||Statics.Machines.get(i).getCostPerDay() == (int)(val)||Statics.Machines.get(i).getInventory() == (int)(val)){
                        rowName = i;
                        selectedRow= Statics.Machines.get(i).getName()+": €"+Statics.Machines.get(i).getCostPerDay();
                    }
                }
                itemLabel.setText(selectedRow);
            }
        });
        if(Statics.CurrentUser.getType()==AccountType.CUSTOMER) {
            class Borrow implements IMethod {

                @Override
                public void execute() {
                    if (AlertBox.DISPLAY_QUESTION_ANSWER) {


                        MachineAdapter mac = catView.getSelectionModel().getSelectedItem();
                        String selectedName = mac.getName();
                        System.out.println("---------");
                        System.out.println(mac.getName());

                        //check if valid
                        int quantity=1;
                        if(AlertBox.DISPLAY_INPUT_TEXT.matches("[0-9]+")){
                            quantity=Integer.parseInt(AlertBox.DISPLAY_INPUT_TEXT);
                            quantity=Math.min(Statics.Machines.get(rowName).getInventory(),quantity);
                            Machine m = Statics.Machines.get(rowName);
                            m.setInventory(quantity);
                            Statics.Machines.get(rowName).setInventory(Math.max(0,Statics.Machines.get(rowName).getInventory()-quantity));
                            Statics.CurrentUser.addCurrentRentals(m);
                            //save machines
                            io.machineSerializeToFile("MachineDB.ser", Statics.Machines);
                            //save user;
                            io.serializeToFile("CustomerDB.ser", Statics.Users);

                        }


                    }
                }
            }

            AlertBox.displayInput("Borrow", "What Quantity Would you like to borrow?", "Confirm", "Cancel","Enter how much you want to borrow",new Borrow());

        }else {

            Main.currentStage.setOnKeyListener(e-> {
                class BackSpace implements IMethod {

                    @Override
                    public void execute() {
                        if(AlertBox.DISPLAY_QUESTION_ANSWER)
                            if(e.getCode() == KeyCode.BACK_SPACE || e.getCode() == KeyCode.X){
                                Statics.Machines.remove(rowName);
                                io.machineSerializeToFile("MachineDB.ser", Statics.Machines);

                                    new NavigationInvoker(new Previous(Main.currentStage)).activate();

                            }
                    }
                }

                AlertBox.displayQuestion("Delete", "Are you sure you want to delete this Machine?", "Delete", "Keep",new BackSpace());


            });
            Main.currentStage.setFXMLScene("Home/UI/editMachine.fxml",new LoginController(),Statics.Machines.get(rowName));
        }
    }

    public void loadUsers() {
        Arrays.asList("AdminDB.ser","CustomerDB.ser").forEach(path-> {
            try {
                System.out.println(path);
                io.readSerializedFile((String)path, "users");
                Statics.Users.addAll(io.users);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /*
        onReturn leads back to user or admin home page depending on if user or admin
     */
    public void onReturn(ActionEvent actionEvent) {
        new NavigationInvoker(new Previous(Main.currentStage)).activate();
    }
}
