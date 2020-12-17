package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sample.Authentication.Logic.RegisterController;
import sample.Authentication.Model.AccountType;
import sample.Command.Navigator;
import sample.Home.Logic.AdminHomeController;
import sample.Runner.IAdapter;

import javax.print.attribute.standard.MediaSize;
import java.io.IOException;
import java.util.Stack;

public class Navigation extends Stage implements Navigator {
    private String FXMLLoader = "";
    private IAdapter adapter;
    private Scene currentScene;
    private Stack<NavObject> navStack = new Stack<>();

    public Navigation(String title) {
        setTitle(title);
    }


    public void setFXMLScene(String sceneID,  IAdapter controller, Object... args) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(sceneID));
        navStack.push(new NavObject(sceneID,args));
        Parent root = loader.load();
        controller = loader.getController();
        currentScene = new Scene(root, 600, 400);
        controller.init();
        controller.custom(args);
        this.setScene(currentScene);
    }


    public void setOnKeyListener(EventHandler<KeyEvent> event) {
        currentScene.setOnKeyPressed(event);
    }

    @Override
    public void previous()  {
        NavObject nav = navStack.pop();
        nav = navStack.pop();
        System.out.println("Back pressed!!"+nav.sceneId);
        try {
            setFXMLScene(nav.sceneId,new AdminHomeController(),nav.args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
class NavObject{
    String sceneId;
    Object[] args;
    public NavObject(String id, Object...args){
        sceneId=id;
        this.args=args;
    }
}
