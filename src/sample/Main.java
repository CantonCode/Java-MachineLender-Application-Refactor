package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Authentication.Logic.FileManager;
import sample.Authentication.Logic.LoginController;
import sample.Authentication.Logic.RegisterController;
import sample.Authentication.Model.AccountType;
import sample.Authentication.Model.Admin;
import sample.Authentication.Model.Customer;
import sample.Home.Logic.AdminHomeController;
import sample.Home.Logic.Home;
import sample.Home.Logic.UserHomeController;
import sample.Home.Model.Machine;
import sample.Runner.Logic.LenderController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main extends Application {
    public static  Navigation currentStage;

    public FileManager io=new FileManager();



    @Override
    public void start(Stage primaryStage) throws Exception{

        currentStage= new Navigation("Machine Lender");
        getCurrentUser();





         RegisterController rc = new RegisterController();  //these two lines create an admin account
        rc.manualAdmin("Admin", "Admin1", "Admin123");

        if(Statics.CurrentUser!=null) {
            if(Statics.CurrentUser.getType() == AccountType.CUSTOMER){
          /*!!!! */      currentStage.setFXMLScene("Home/UI/userHome.fxml", new UserHomeController()); //test 2 Home/UI/adminHome.fxml
            }else{
                currentStage.setFXMLScene("Home/UI/adminHome.fxml", new AdminHomeController()); //test 2
            }
        } else {
            currentStage.setFXMLScene("Authentication/UI/login.fxml", new LoginController());
        }
        primaryStage=currentStage;
        primaryStage.show();
    }

    public void getCurrentUser(){
        Statics.CurrentUser=null;
        try {
            io.readSerializedFile("currentUser.ser","users");
            Statics.CurrentUser=io.users.size()>0?io.users.get(0):null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {

        ArrayList<Machine> machines=new ArrayList<>();
        FileManager io=new FileManager();


        System.out.println(Arrays.toString(args));
        Arrays.asList("MachineDB.ser").forEach(path->{
            try {
                io.readSerializedFile((String)path,"machines");
                machines.addAll(io.machines);
                System.out.println(machines);
                Statics.Machines.addAll(machines);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        launch(args);
    }
}
