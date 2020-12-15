package sample.Runner.Logic;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.*;

import java.net.URL;
import java.util.*;


public class LenderController implements Initializable {

    @FXML
    private Label dueDate1, dueDate2, dueDate3, dueDate4, item1, item2, item3, item4, inventoryStat, username;

    @FXML
    private Button returnBtn, borrowBtn, adminBtn, logoutBtn;

    ArrayList<String> inventory = new ArrayList<>();
    String userID;
    String usernameS;


    public void addInv()
    {
        try {
            FileWriter fr = new FileWriter("inventory" + userID + ".txt");
            BufferedWriter bw = new BufferedWriter(fr);
            PrintWriter pw = new PrintWriter(bw);

            pw.println(userID);

            for (String item : inventory) {
                if (!item.equals(null))
                    pw.print("," + item);//"getthecurrenttimeinseconds+some extra amount");
            }

            pw.flush();

            fr.close();
            bw.close();
            pw.close();
            System.out.println("MADE INVENTORY");

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void setInfo(String name, String ID) {
        userID = ID;
        usernameS = name;
        System.out.println("Succesfully wrote info");
    }


    public void showInformation(String name, String id) {
        username.setText(name);
        userID = id;
        String path = "inventory" + userID + ".txt";

        System.out.println(path);

        try {
            File myObj = new File(path);
            Scanner reader = new Scanner(myObj);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                //split data by comma, into array
                String[] tmpUser = data.split(",");
                //convert from array to list
                List<String> tempUser = Arrays.asList(tmpUser);
                //convert from list to arraylist
                inventory = new ArrayList<String>(tempUser);
                System.out.println("I just made him an inventory");
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
