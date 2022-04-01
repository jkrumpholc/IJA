package ija.uml;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class Controller {

    @FXML
    private JFXButton x, y, z;

    @FXML
    private AnchorPane xxx, yyy, zzz;

    @FXML
    public void hendleButtonAction(ActionEvent event) {
        if(event.getSource() == x) {
            xxx.toFront();
        }
        else if(event.getSource() == y) {
            yyy.toFront();
        }
        else if(event.getSource() == z) {
            zzz.toFront();
        }
    } 
    
    @FXML
    public void open() {
       System.out.println("otevřít"); 
    }
 
}
