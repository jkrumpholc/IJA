// autor: Tereza Buchníčková        //
// login: xbuchn00                  //
// konstruktor UML třídy            //

package ija.uml;

import java.io.IOException;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;


public class UMLClassUI extends VBox {

    @FXML
    private Button class_button;
    

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
}