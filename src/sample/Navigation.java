package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Authentication.Logic.RegisterController;
import sample.Authentication.Model.AccountType;
import sample.Runner.IAdapter;

import java.io.IOException;

public class Navigation extends Stage {
    private String FXMLLoader="";
    private IAdapter adapter;

    public Navigation(String title){
        setTitle(title);
    }

    public void setFXMLScene(String sceneID,IAdapter controller,Object...args) throws IOException {
        FXMLLoader loader =new  FXMLLoader(getClass().getResource(sceneID));
        Parent root=loader.load();
        controller = loader.getController();
        controller.init();
        controller.custom(args);
        this.setScene(new Scene(root, 600, 400));
    }
}
