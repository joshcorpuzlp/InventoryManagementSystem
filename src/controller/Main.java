package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * This class creates the Inventory Management Application
 * FUTURE ENHANCEMENTS - One big enhancement that this application could implement would be to improve the aesthetic and
 * feel the GUI. Another update that would make the application truly powerful is if the initialized dataset and the inventory class could be saved in disk.
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