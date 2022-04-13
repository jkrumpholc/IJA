// autor: Tereza Buchníčková        //
// login: xbuchn00                  //
//      //

package ija.uml;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class EditUI extends AnchorPane {
    
    public EditUI() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("edit_ui.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}

