package Main.Authentication.Logic;

import Main.Interceptor.PreLoginContext;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import Main.AlertBox;
import Main.Authentication.Model.AccountType;
import Main.Authentication.Model.User;
import Main.Home.Logic.AdminHomeController;
import Main.Home.Logic.UserHomeController;
import Main.Home.Model.Machine;
import Main.Main;
import Main.InventoryHelper.IAdapter;
import Main.InventoryHelper.Logic.LenderController;
import Main.Statics;

import java.io.IOException;
import java.util.*;

import static Main.Main.myDispatcher;

/*
    Class for the logic necessary for the login page.
 */
public class LoginController implements IAdapter {

    @FXML
    private Button loginButton, registerPageButton;
    @FXML
    private Label messanger;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    String username, userId;


    ArrayList<User>users=new ArrayList<>();
    ArrayList<Machine>machines=new ArrayList<>();
    FileManager io=new FileManager();


    private void loadSceneAndSendInfo() {
        try {

            System.out.println("It's creating the new window....");

            //Load second scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("lender.fxml"));
            Parent root = loader.load();

            //Get controller of scene2
            LenderController lend = loader.getController();
            //Pass whatever data you want. You can have multiple method calls here
            lend.setInfo(username, userId);
            System.out.println("The passed in info " + username + " " + userId);
            lend.addInv();
            lend.showInformation(username, userId);

            //Show scene 2 in new window
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Second Window");
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex);
            System.out.println("Error check");
        }
    }

    public LoginController() {
    }

    public void loginButtonOnAction(ActionEvent event) throws IOException {


        if (!usernameField.getText().isBlank() && !passwordField.getText().isBlank()) {
            /// myDispatcher.onPreLogin(new PreLoginContext()); /// onattempt

            if (validateLogin(usernameField.getText(), passwordField.getText())) {
//                messanger.setText("Logged in as: "+Statics.CurrentUser);
                ArrayList<User> users= new ArrayList<>();
                users.add(Statics.CurrentUser);
             //   messanger.setStyle("-fx-text-fill: green;");

                io.serializeToFile("currentUser.ser",users);

                if (Statics.CurrentUser.getType() == AccountType.CUSTOMER) {
                    Main.currentStage.setFXMLScene("Home/UI/userHome.fxml", new UserHomeController()); //test 2 Home/UI/adminHome.fxml
                } else {
                    Main.currentStage.setFXMLScene("Home/UI/adminHome.fxml", new AdminHomeController()); //test 2
                    System.out.println("Admin page check");
                }
                System.out.println("Login Button Check");
            }
        }
    }

    public boolean validateLogin(String username, String password) throws IOException {
        //check text file to see if the specified log in exists already
        for(User user : users){
            System.out.print(user.getUsername()+" ");
            System.out.println(user.getPassword());
            user.decryptPassword();
            if(username.equalsIgnoreCase(user.getUsername())&&password.equals(user.getPassword())){
                System.out.println("we just set the user to logged in");
                Statics.CurrentUser=user;
                return true;
            }
        }
        AlertBox.display("Login Error","Username and/or Password was incorrect!");
        messanger.setText("User not recognized");
        messanger.setStyle("-fx-text-fill: red;");
        return false;
    }

    public void registerButtonOnAction(ActionEvent actionEvent)throws IOException {
        ///change last argument to either admin/customer
        Main.currentStage.setFXMLScene("Authentication/UI/register.fxml",new LoginController(), AccountType.CUSTOMER);
    }

    @Override
    public void init() {
        myDispatcher.onPreLogin(new PreLoginContext());

        Arrays.asList("AdminDB.ser","CustomerDB.ser").forEach(path-> {
            try {
                io.readSerializedFile((String)path,"users");
                users.addAll(io.users);
                System.out.println("DB send check");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }

    @Override
    public void custom(Object... args) {
    }
}

