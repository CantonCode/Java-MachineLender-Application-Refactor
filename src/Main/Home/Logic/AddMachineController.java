package Main.Home.Logic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import Main.Authentication.Logic.FileManager;
import Main.Command.NavigationInvoker;
import Main.Command.Previous;
import Main.Home.Model.Machine;
import Main.Home.Model.MachineFactory;
import Main.Main;
import Main.InventoryHelper.IAdapter;
import Main.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/*
    Class for logic of add machine page.
 */
public class AddMachineController implements IAdapter {
    @FXML
    private TextField machineCost;

    @FXML
    private TextField machineName;

    @FXML
    ComboBox machineType;
    private MachineFactory factory = new MachineFactory();
    private ArrayList<Machine> machines = new ArrayList<Machine>();
    private FileManager io = new FileManager();


    @Override
    public void init() {
        Arrays.asList("MachineDB.ser").forEach(path-> {
            try {
                io.readSerializedFile((String)path,"machines");
                machines.addAll(io.machines);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        ArrayList<String> option = new ArrayList<String>();

        option.add("Digger");
        option.add("Crane");
        ObservableList<String> options = FXCollections.observableArrayList(option);
        machineType.setItems(options);
    }

    @Override
    public void custom(Object... args) {
    }

    /*
        onReturn action to bring back to adminHome
     */
    public void onReturn(ActionEvent actionEvent) {
      new NavigationInvoker(new Previous(Main.currentStage)).activate();
    }

    /*
        onAdd action creates the inputted machine and stores it then leads admin back to adminHome
     */
    public void onAdd(ActionEvent actionEvent) {
        try {
            String type = machineType.getValue().toString();
            Machine newMachine = factory.createNewMachine(type);

            String time = String.valueOf(System.currentTimeMillis());
            int i = Integer.parseInt(machineCost.getText());

            newMachine.setId(time);
            newMachine.setName(machineName.getText());
            newMachine.setCostPerDay(i);
            newMachine.setType(type);
            newMachine.setInventory(100);
            machines.add(newMachine);
            Statics.Machines.add(newMachine);

            io.machineSerializeToFile("MachineDB.ser", machines);

            Main.currentStage.setFXMLScene("Home/UI/adminHome.fxml",new AdminHomeController());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
