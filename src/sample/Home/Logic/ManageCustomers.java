package sample.Home.Logic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Authentication.Logic.FileManager;
import sample.Authentication.Logic.LoginController;
import sample.Authentication.Model.AccountType;
import sample.Authentication.Model.Customer;
import sample.Authentication.Model.User;
import sample.Authentication.Model.UserAdapter;
import sample.Main;
import sample.Runner.IAdapter;
import sample.Statics;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ManageCustomers implements IAdapter {
    public TableColumn cidCol;
    public TableColumn cUsernameCol;
    public TableColumn cNameCol;
    public TableColumn cCreatedCol;
    public TableView cTable;

    @Override
    public void init() {
        List<UserAdapter> customers=new ArrayList<>();
        for(User u : Statics.Users.stream().filter(user -> user.getType()== AccountType.CUSTOMER).collect(Collectors.toList())){
            customers.add(new UserAdapter(u));
        }

        final ObservableList<UserAdapter> data = FXCollections.observableList(customers);
        cidCol.setCellValueFactory(
                new PropertyValueFactory<UserAdapter, String>("id"));
        cUsernameCol.setCellValueFactory(
                new PropertyValueFactory<UserAdapter, String>("username"));
        cNameCol.setCellValueFactory(
                new PropertyValueFactory<UserAdapter, String>("name"));
        cCreatedCol.setCellValueFactory(
                new PropertyValueFactory<UserAdapter, String>("created"));

        cTable.setItems(data);
    }

    @Override
    public void custom(Object... args) {
    }

    public void cancelChanges(ActionEvent actionEvent) {
    }

    public void saveChanges(ActionEvent actionEvent) {
    }
}
