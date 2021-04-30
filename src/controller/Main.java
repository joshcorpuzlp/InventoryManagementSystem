package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * This class creates the Inventory Management Application
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/mainMenu.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    /**
     * This is the main method.
     * This is the first method that gets called when your java application is run.
     */
    public static void main(String[] args) {
        launch(args);
    }
}

/**
 * This is creates a custom exception used in the application's input validation feature.
 */
class myExceptions extends Exception {
    public myExceptions() {
        super();
    }

    public myExceptions(String s) {
        super(s);
    }
}