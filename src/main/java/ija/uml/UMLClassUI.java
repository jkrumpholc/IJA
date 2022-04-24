// autor: Tereza Buchníčková        //
// login: xbuchn00                  //
// konstruktor UML třídy            //

package ija.uml;

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.List;
import javafx.scene.control.ListView;


public class UMLClassUI extends VBox {

    @FXML
    private Button class_button;
    @FXML
    private ListView attributes;

    public UMLClassUI() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("uml_class_ui.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void setTitle(String value) {
        class_button.setText(value);
    }

    /* public void setAttributes(List value) {
        attributes.getItems().addAll(c);
    } */

    @FXML
    public void editClass() {
        Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("edit_window.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Upravit třídu");
                stage.setScene(new Scene(root, 300, 400));
                stage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
    }
}