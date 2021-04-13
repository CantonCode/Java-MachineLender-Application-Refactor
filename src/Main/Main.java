package Main;

import Main.Interceptor.Dispatcher;
import javafx.application.Application;
import javafx.stage.Stage;
import Main.Authentication.Logic.FileManager;
import Main.Authentication.Logic.LoginController;
import Main.Authentication.Logic.RegisterController;
import Main.Authentication.Model.AccountType;
import Main.Home.Logic.AdminHomeController;
import Main.Home.Logic.UserHomeController;
import Main.Home.Model.Machine;
import Main.Observer.ObserverInvoker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main extends Application {
    public static Navigation currentStage;

    public FileManager io = new FileManager();

    public static void main(String[] args) {

        ArrayList<Machine> machines = new ArrayList<>();

        /// bad code smell?  <=== #Shadowing variable
        FileManager io = new FileManager();

        Dispatcher myDispatcher = new Dispatcher();

        System.out.println(Arrays.toString(args));
        Statics.inventoryObservers =new ObserverInvoker();

        // Context object
        // myDispatcher.onPreLogin(/*pass context object*/);

        Arrays.asList("MachineDB.ser").forEach(path -> {
            try {
                io.readSerializedFile(path, "machines");
                machines.addAll(io.machines);
                System.out.println(machines);
                Statics.Machines.addAll(machines);
                Statics.inventoryObservers.registerNewObserver();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        currentStage = new Navigation("Machine Lender");
        getCurrentUser();

        /*
            Below two lines create an admin account
        */

        RegisterController rc = new RegisterController();
        rc.manualAdmin("Admin", "Admin1", "Admin123");

        /*
            If else to check if user is logged in and if so if they should be led to the admin or user home pages
         */
        if (Statics.CurrentUser != null) {
            if (Statics.CurrentUser.getType() == AccountType.CUSTOMER) {
                /*!!!! */
                currentStage.setFXMLScene("Home/UI/userHome.fxml", new UserHomeController()); //test 2 Home/UI/adminHome.fxml
            } else {
                currentStage.setFXMLScene("Home/UI/adminHome.fxml", new AdminHomeController()); //test 2
            }
        } else {
            currentStage.setFXMLScene("Authentication/UI/login.fxml", new LoginController());
        }
        primaryStage = currentStage;
        primaryStage.show();
    }

    public void getCurrentUser() {
        Statics.CurrentUser = null;
        try {
            io.readSerializedFile("currentUser.ser", "users");
            Statics.CurrentUser = io.users.size() > 0 ? io.users.get(0) : null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
