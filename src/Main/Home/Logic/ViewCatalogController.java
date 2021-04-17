package Main.Home.Logic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import Main.AlertBox;
import Main.Authentication.Logic.FileManager;
import Main.Authentication.Model.AccountType;
import Main.Authentication.Model.User;
import Main.Command.NavigationInvoker;
import Main.Command.Previous;
import Main.Home.Model.Machine;
import Main.Home.Model.MachineAdapter;
import Main.Authentication.Logic.LoginController;
import Main.IMethod;
import Main.Main;
import Main.InventoryHelper.IAdapter;
import Main.Statics;

import java.io.IOException;
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

    private boolean isValidMachine(int index, Object val){
        return Statics.Machines.get(index).getId().equals(val) ||
                Statics.Machines.get(index).getName().equals(val) ||
                Statics.Machines.get(index).getType().equals(val) ||
                Statics.Machines.get(index).getCostPerDay() == (int) (val) ||
                Statics.Machines.get(index).getInventory() == (int) (val);
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
                    if(isValidMachine(i, val)){
                        rowName = i;
                        selectedRow= Statics.Machines.get(i).getName()+": €"+Statics.Machines.get(i).getCostPerDay();
                    }
                }
                itemLabel.setText(selectedRow);
            }
        });

        MachineAdapter mac = catView.getSelectionModel().getSelectedItem();
        if(Statics.CurrentUser.getType()==AccountType.CUSTOMER) {

            class Borrow implements IMethod {

                @Override
                public void execute() {
                    if (AlertBox.DISPLAY_QUESTION_ANSWER) {


                        MachineAdapter mac = catView.getSelectionModel().getSelectedItem();
                        String selectedName = mac.getName();
                        System.out.println("---------");
                        System.out.println(mac.getName());
                        System.out.println(mac);
                        //check if validF
                        int quantity=1;
                        if(AlertBox.DISPLAY_INPUT_TEXT.matches("[0-9]+")){
                            quantity=Integer.parseInt(AlertBox.DISPLAY_INPUT_TEXT);
                            System.out.println(quantity);
                            System.out.println(mac.getInventory());
                            quantity=Math.min(mac.getInventory()+1,quantity);
                            System.out.println(quantity);
                            int left=(mac.getInventory())-quantity;

                            System.out.println("I am Borrowing "+ quantity + ": " +selectedName);
                            System.out.println(mac.getInventory()-quantity+" Left!!");
                            Machine m = mac.getMachine();
                            m.setInventory(quantity);

                            if(mac.getInventory()>0) {
                                AlertBox.display("SUCCESS", String.format("%s\n%-15s:\t%-15s","Thank You For Your Purchase",selectedName,quantity ));
                                System.out.println(m);
                                Statics.CurrentUser.getCurrRentals().stream().filter(machine -> machine.getId().equals(m.getId())).collect(Collectors.toList()).get(0).increaseInventory(m.getInventory());

                                Statics.Machines.stream().filter(machine -> machine.getId().equals(mac.getId())).collect(Collectors.toList()).get(0).setInventory(Math.max(mac.getInventory()-quantity,0));
                               // mac.setInventory(Math.max((left), 0));

                            }
                            else  AlertBox.display("OUT OF STOCK", "OH No this item: "+selectedName+" is currently out of Stock");
                            int index=-1;
                            for(int i=0; i < Statics.Users.size(); i++){
                                if(Statics.Users.get(i).getId().equals(Statics.CurrentUser.getId())){
                                    System.out.println("Line 162: "+Statics.CurrentUser.getCurrRentals());
                                    Statics.Users.get(i).setCurrRentals(Statics.CurrentUser.getCurrRentals());
                                }
                            }
                            //save machines
                            io.machineSerializeToFile("MachineDB.ser", Statics.Machines);
                            //save user;
                            io.serializeToFile("CustomerDB.ser", Statics.Users.stream().filter((user)->user.getType() == AccountType.CUSTOMER).collect(Collectors.toList()));
                            new NavigationInvoker(new Previous(Main.currentStage)).activate();
                            try {
                                Main.currentStage.setFXMLScene("Home/UI/catalog.fxml", new ViewCatalogController());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }


                    }
                }
            }

            AlertBox.displayInput("Borrow "+selectedRow, "How many "+selectedRow+" Would you like to borrow?",
                    "Confirm", "Cancel","Enter how much you want to borrow",new Borrow());

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

                AlertBox.displayQuestion("Delete", "Are you sure you want to delete this Machine?",
                        "Delete", "Keep",new BackSpace());


            });
            Main.currentStage.setFXMLScene("Home/UI/editMachine.fxml",new LoginController(),  Statics.Machines.stream().filter(machine -> machine.getId().equals(mac.getId())).collect(Collectors.toList()).get(0));
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
