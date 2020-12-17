package sample.Home.Logic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import sample.*;
import sample.Authentication.Logic.FileManager;
import sample.Authentication.Logic.LoginController;
import sample.Authentication.Model.AccountType;
import sample.Authentication.Model.Customer;
import sample.Authentication.Model.User;
import sample.Authentication.Model.UserAdapter;
import sample.Command.NavigationInvoker;
import sample.Command.Previous;
import sample.Runner.IAdapter;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
    Class implements manage customers page
 */
public class ManageCustomers implements IAdapter {
    public TableColumn cidCol;
    public TableColumn cUsernameCol;
    public TableColumn cNameCol;
    public TableColumn cCreatedCol;
    public TableView cTable;
    public FileManager io = new FileManager();

    int rowName;

    @Override
    public void init() {
        List<UserAdapter> customers=new ArrayList<>();
        for(User u : Statics.Users.stream().filter(user -> user.getType() == AccountType.CUSTOMER).collect(Collectors.toList())) {
            customers.add(new UserAdapter(u));
        }

        final ObservableList<UserAdapter> data = FXCollections.observableList(customers);
        cidCol.setCellValueFactory(new PropertyValueFactory<UserAdapter, String>("id"));
        cUsernameCol.setCellValueFactory(new PropertyValueFactory<UserAdapter, String>("username"));
        cNameCol.setCellValueFactory(new PropertyValueFactory<UserAdapter, String>("name"));
        cCreatedCol.setCellValueFactory(new PropertyValueFactory<UserAdapter, String>("created"));

        cTable.setItems(data);
        cTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue)-> {
            if(cTable.getSelectionModel().getSelectedItem() != null){
                TableView.TableViewSelectionModel Tv = cTable.getSelectionModel();
                ObservableList cells = Tv.getSelectedCells();
                TablePosition tp = (TablePosition) cells.get(0);
                Object val = tp.getTableColumn().getCellData(newValue);

                for(int i = 0; i < Statics.Users.size(); i++){
                    if(Statics.Users.get(i).getId().equals(val) || Statics.Users.get(i).getUsername().equals(val) || Statics.Users.get(i).getName().equals(val) || new SimpleDateFormat("dd/MM/yyyy hh:mm").format(Long.parseLong(Statics.Users.get(i).getId())).equals(val)){
                        rowName = i;
                    }
                }
            }
        });

        Main.currentStage.setOnKeyListener(e-> {
            class BackSpace implements IMethod {

                @Override
                public void execute() {
                    if(AlertBox.DISPLAY_QUESTION_ANSWER)
                    if(e.getCode() == KeyCode.BACK_SPACE || e.getCode() == KeyCode.X){
                        Statics.Users.remove(rowName);
                        io.serializeToFile("CustomerDB.ser", Statics.Users);
                        new NavigationInvoker(new Previous(Main.currentStage)).activate();
                    }
                }
            }

            AlertBox.displayQuestion("Delete", "Are you sure you want to delete this user?", "Delete", "Keep",new BackSpace());


        });
    }

    @Override
    public void custom(Object... args) {
    }

    public void cancelChanges(ActionEvent actionEvent) throws IOException {
        new NavigationInvoker(new Previous(Main.currentStage)).activate();
    }

    public void saveChanges(ActionEvent actionEvent) throws IOException {
        new NavigationInvoker(new Previous(Main.currentStage)).activate();
    }

    public void addUser(ActionEvent actionEvent) throws IOException{
        Main.currentStage.setFXMLScene("Home/UI/addUser.fxml", new AddUserController());
    }
}
