package Main.Home.Logic;

import Main.Interceptor.PostLoginContext;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import Main.Authentication.Logic.FileManager;
import Main.Authentication.Logic.LoginController;
import Main.Authentication.Model.User;
import Main.Main;
import Main.InventoryHelper.IAdapter;
import Main.Statics;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import static Main.Main.myDispatcher;

/*
    Logic implementation for the user home page.
 */
public class UserHomeController implements IAdapter {

    private final FileManager io = new FileManager();

    @FXML
    public Label unameField;
    public Button borrowedBtn;
    public Button viewCatalogBtn;

    @Override
    public void init() {
        if(Statics.CurrentUser!=null) {
            System.out.println("CURRENT USER:"+ Statics.CurrentUser);
            loadUsers();
            for(int i = 0 ; i<Statics.Users.size(); i++){
                System.out.println("USER ID: "+Statics.Users.get(i).getUsername()+"--"+"CU - > "+ Statics.CurrentUser.getUsername());
            }
            unameField.setText(Statics.CurrentUser.getUsername()+"("+Statics.CurrentUser.getType().name().toLowerCase()+")");
            try{
                User user = Statics.Users.stream().filter((user1)-> user1.getId().equals(Statics.CurrentUser.getId())).collect(Collectors.toList()).get(0);
                Statics.CurrentUser = user;
                ArrayList<User> users= new ArrayList<>();
                users.add(Statics.CurrentUser);
                io.serializeToFile("currentUser.ser",users);
            }catch(ArrayIndexOutOfBoundsException exc){
                System.out.println("An Array Out of Bounds exception has occured in init.UserHomeController.java: This is most likely because the current user id no longer exists among the lists of users!\nAdvised: Delete Current user");
            }


        }else{
            System.out.println("CURRENT USER:"+ Statics.CurrentUser);
        }

        myDispatcher.onPostLogin(new PostLoginContext(Statics.CurrentUser.getUsername()));
    }

    @Override
    public void custom(Object... args) {
    }

    private void loadUsers() {
        Arrays.asList("AdminDB.ser","CustomerDB.ser").forEach(path-> {
            try {
                System.out.println(path);
                Statics.Users.clear();
                io.readSerializedFile((String)path,"users");
                for(int i = 0 ; i<io.users.size(); i++){
                    System.out.println("USER ID: "+io.users.get(i).getUsername()+"=="+"CU - > "+ Statics.CurrentUser.getUsername());
                }
                Statics.Users.addAll(io.users);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /*
        Settings icon clickable which currently leads back to login page
     */
    public void onSettings(MouseEvent mouseEvent) {
        ArrayList<User> users= new ArrayList<>();
        new FileManager().serializeToFile("currentUser.ser",users);
        try {
            Main.currentStage.setFXMLScene("Authentication/UI/login.fxml",new LoginController());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    /*
        View catalog button
     */
    public void viewCatalog(ActionEvent actionEvent) {
        try {
            Main.currentStage.setFXMLScene("Home/UI/catalog.fxml", new ViewCatalogController());
            myDispatcher.onPreCatalog(new PostLoginContext(Statics.CurrentUser.getUsername()));
        } catch (IOException | ParseException ex) {
            ex.printStackTrace();
        }
    }

    /*
        View borrowed machines button
     */
    public void viewBorrowedMachines(ActionEvent actionEvent) {
        try {
            myDispatcher.onPreInventory(new PostLoginContext(Statics.CurrentUser.getUsername()));

            Main.currentStage.setFXMLScene("Home/UI/borrowedItems.fxml", new BorrowedItemsController());

        } catch (IOException | ParseException ex) {
            ex.printStackTrace();
        }
    }
}
