// autor: Tereza Buchníčková        //
// login: xbuchn00                  //
// vytváření hlavního okna aplikace //

package ija.uml;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("main_screen.fxml"));
        primaryStage.setTitle("UML diagramy");
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene); 
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.show();
        root.requestFocus();
    }


    public static void main(String[] args) {
        launch(args);
    }
}