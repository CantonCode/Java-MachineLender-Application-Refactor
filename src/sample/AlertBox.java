package sample;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AlertBox {
    public static boolean DISPLAY_QUESTION_ANSWER = false;
    public static String DISPLAY_INPUT_TEXT="";
    static boolean IS_DIPLAYED=false;

    public static void display(String title, String message){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);// Stops input into other windows.
        window.setTitle(title);
        window.setMinWidth(250);
        window.setMinHeight(125);

        Label label = new Label();
        label .setText(message);

        Button closeButton = new Button("Close the window");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();// Waits for window to be closed then returns to previous window.
    }

    public static void  displayQuestion(String title, String message, String yesBtn, String noBtn, IMethod method){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);// Stops input into other windows.
        window.setTitle(title);
        window.setMinWidth(250);
        window.setMinHeight(125);

        Label label = new Label();
        label .setText(message);

        Button closeButton = new Button("Close the window");
        closeButton.setOnAction(e -> window.close());

        Button noButton = new Button(noBtn);
        Button yesButton = new Button(yesBtn);

        HBox hb = new HBox();
        hb.setSpacing(15);
        hb.setAlignment(Pos.CENTER_RIGHT);
        hb.getChildren().addAll(noButton,yesButton);


        noButton.setOnAction(e->{
            DISPLAY_QUESTION_ANSWER = false;
            IS_DIPLAYED=false;
            window.close();
            method.execute();
        });

        yesButton.setOnAction(e->{
            DISPLAY_QUESTION_ANSWER = true;
            IS_DIPLAYED=false;
            window.close();
            method.execute();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, hb);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();// Waits for window to be closed then returns to previous window.

    }

    public static void  displayInput(String title, String message, String yesBtn, String noBtn,String hint, IMethod method){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);// Stops input into other windows.
        window.setTitle(title);
        window.setMinWidth(250);
        window.setMinHeight(125);

        Label label = new Label();
        label .setText(message);

        Button closeButton = new Button("Close the window");
        closeButton.setOnAction(e -> window.close());

        Button noButton = new Button(noBtn);
        Button yesButton = new Button(yesBtn);

        HBox hb = new HBox();
        hb.setSpacing(15);
        hb.setAlignment(Pos.CENTER_RIGHT);
        hb.getChildren().addAll(noButton,yesButton);

        TextField input=new TextField();
        input.setPromptText(hint);
        input.setOnKeyTyped(e->{
            DISPLAY_INPUT_TEXT=input.getText();
        });

        noButton.setOnAction(e->{
            DISPLAY_QUESTION_ANSWER = false;
            IS_DIPLAYED=false;
            window.close();
            method.execute();
        });

        yesButton.setOnAction(e->{
            DISPLAY_QUESTION_ANSWER = true;
            IS_DIPLAYED=false;
            window.close();
            method.execute();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label,input, hb);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20, 20,20,20));
        layout.setSpacing(12);


        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();// Waits for window to be closed then returns to previous window.

    }

}

