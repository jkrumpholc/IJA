// autor: Tereza Buchníčková        //
// login: xbuchn00                  //
// konstruktor UML třídy            //

package ija.uml;

import java.io.IOException;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class UMLClassUI extends VBox {

    @FXML
    private Button class_button;
    @FXML
    private ListView<String> attributes;

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

    /* public void setAttributes() {
        attributes.getItems().addAll(c);
    } */

    @FXML
    public void editClass() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("edit_window.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Upravit třídu");
                stage.setScene(new Scene(loader.load(), 300, 400));
                stage.initModality(Modality.APPLICATION_MODAL);
                AddClassUI controller = loader.getController();
                stage.showAndWait();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
    }
}