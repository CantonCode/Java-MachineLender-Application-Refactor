package sample.Home.Logic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import sample.Home.Model.Machine;
import sample.Home.Model.MachineFactory;
import sample.Main;
import sample.Runner.IAdapter;

import javax.crypto.Mac;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddMachineController implements IAdapter{
    @FXML
    private TextField machineCost;

    @FXML
    private TextField machineName;

   @FXML
   ComboBox machineType;
    MachineFactory factory = new MachineFactory();

    public void onReturn(ActionEvent actionEvent) {
        try {
            Main.currentStage.setFXMLScene("Home/UI/adminHome.fxml",new AdminHomeController());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onAdd(ActionEvent actionEvent) {
        try {
            String type = machineType.getValue().toString();
            Machine newMachine = factory.createNewMachine(type);

            String time = String.valueOf(System.currentTimeMillis());
            int i = Integer.parseInt(machineCost.getText());

            newMachine.setName(machineName.getText());
            newMachine.setCostPerDay(i);
            newMachine.setId(time);

            System.out.println(newMachine.getCostPerDay() + " " + newMachine.getType());

           // machineSerializeToFile

            Main.currentStage.setFXMLScene("Home/UI/adminHome.fxml",new AdminHomeController());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {
        ArrayList<String> option = new ArrayList<String>();
        option.add("Digger");
        option.add("Crane");

        ObservableList<String> options = FXCollections.observableArrayList(option);

        machineType.setItems(options);
    }

    @Override
    public void custom(Object... args) {

    }
}
