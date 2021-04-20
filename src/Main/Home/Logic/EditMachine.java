package Main.Home.Logic;

import Main.Command.NavigationInvoker;
import Main.Command.Previous;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.text.Text;
import Main.Authentication.Logic.FileManager;
import Main.Home.Model.Machine;
import Main.Main;
import Main.InventoryHelper.IAdapter;
import Main.Statics;

import java.io.IOException;
import java.text.ParseException;
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
    public Button deleteMachineBtn;
    private Machine machine;
    public void onReturn(ActionEvent actionEvent) {
        new NavigationInvoker(new Previous(Main.currentStage)).activate();
    }

    public void onEdit(ActionEvent actionEvent) throws IOException, ParseException {
        machine.setName(machineName.getText());

        String pattern = "^[0-9.]+$";
        String pattern2 = "^[0-9]+$";
        machine.setInventory(!ed_inv.getText().matches(pattern2)?0:Integer.parseInt(ed_inv.getText()));
        machine.setCostPerDay(!machineCost.getText().matches(pattern)?0:Double.parseDouble(machineCost.getText()));
        machine.setType((String)machineType.getValue());
        Statics.Machines.set(Statics.Machines.indexOf(machine),machine);
        System.out.println("------");
        System.out.println(Statics.Machines);

        new FileManager().machineSerializeToFile("MachineDB.ser", Statics.Machines);
        new NavigationInvoker(new Previous(Main.currentStage)).activate();

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

    public void onDeleteMachine(ActionEvent actionEvent) throws IOException, ParseException {

        Statics.Machines.remove(machine);
        new FileManager().machineSerializeToFile("MachineDB.ser", Statics.Machines);
        Main.currentStage.setFXMLScene("Home/UI/adminHome.fxml",new AdminHomeController());

    }
}
