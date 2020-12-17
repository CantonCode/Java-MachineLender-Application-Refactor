package sample.Home.Logic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.text.Text;
import sample.Authentication.Logic.FileManager;
import sample.Home.Model.Machine;
import sample.Main;
import sample.Runner.IAdapter;
import sample.Statics;

import java.io.IOException;
import java.util.ArrayList;

public class EditMachine implements IAdapter {

    public TextField machineName;
    public Text regNameTitle;
    public Text registerTitle;
    public Text regPasswordTitle;
    public Button backBtn;
    public Button editMachineBtn;
    public Label infoLabel;
    public TextField machineCost;
    public Text regPasswordTitle1;
    public ComboBox machineType;
    public TextField ed_inv;
    private Machine machine;
    public void onReturn(ActionEvent actionEvent) {
    }

    public void onEdit(ActionEvent actionEvent) throws IOException {
        machine.setName(machineName.getText());

        machine.setInventory(!ed_inv.getText().matches("[0-9]+")?0:Integer.parseInt(ed_inv.getText()));
        machine.setCostPerDay(!machineCost.getText().matches("[0-9]+")?0:Integer.parseInt(machineCost.getText()));
        machine.setType((String)machineType.getValue());
        Statics.Machines.set(Statics.Machines.indexOf(machine),machine);
        System.out.println("------");
        System.out.println(machine.getInventory());

        new FileManager().machineSerializeToFile("MachineDB.ser", Statics.Machines);

        Main.currentStage.setFXMLScene("Home/UI/adminHome.fxml",new AdminHomeController());

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
        if(args[0] instanceof Machine){
             machine= (Machine)args[0];
            machineName.setText(machine.getName());
            machineCost.setText(String.valueOf(machine.getCostPerDay()));
            ed_inv.setText(String.valueOf(machine.getInventory()));
            machineType.getSelectionModel().select(machine.getType());
            registerTitle.setText(machine.getName());
        }
    }

    public void onInvChange(InputMethodEvent inputMethodEvent) {
        if(!ed_inv.getText().matches("[0-9]+"))
            ed_inv.clear();
    }
}
