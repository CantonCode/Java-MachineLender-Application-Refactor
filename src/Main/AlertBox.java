package Main;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class AlertBox {
    public static boolean DISPLAY_QUESTION_ANSWER = false;
    public static String DISPLAY_INPUT_TEXT="";
    static boolean IS_DIPLAYED=false;

    public static Stage createWindow(String title, int width, int height){

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);// Stops input into other windows.

        window.setTitle(title);
        window.setMinWidth(width);
        window.setMinHeight(height);

        return window;
    }

    public static VBox createVBox(double v, Label label, Button button){

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, button);
        layout.setAlignment(Pos.CENTER);

        return layout;
    }

    public static HBox createHBox(int spacing, Button yes, Button no){

        HBox hb = new HBox();
        hb.setSpacing(spacing);
        hb.setAlignment(Pos.CENTER_RIGHT);
        hb.getChildren().addAll(no, yes);

        return hb;
    }

    public static Button createCloseButton(Stage window, String s){
        Button closeButton = new Button(s);
        closeButton.setOnAction(e -> window.close());

        return closeButton;
    }

    public static void display(String title, String message){
        Stage window = createWindow(title, 250, 125);

        Label label = new Label();
        label.setText(message);

        Button closeButton = createCloseButton(window, "Close the window");

        VBox layout = createVBox(10, label, closeButton);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();// Waits for window to be closed then returns to previous window.
    }

    public static void  displayQuestion(String title, String message, String yesBtn, String noBtn, IMethod method){
        Stage window = createWindow(title, 250, 125);

        Label label = new Label();
        label.setText(message);

        Button closeButton = createCloseButton(window, "Close the window");

        Button noButton = new Button(noBtn);
        Button yesButton = new Button(yesBtn);

        HBox hb = createHBox(15, yesButton, noButton);

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
        Stage window = createWindow(title, 250, 125);

        Label label = new Label();
        label.setText(message);

        Button closeButton = createCloseButton(window, "Close the window");

        Button noButton = new Button(noBtn);
        Button yesButton = new Button(yesBtn);

        HBox hb = createHBox(15, yesButton, noButton);

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

