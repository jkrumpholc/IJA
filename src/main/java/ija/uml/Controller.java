// autor: Tereza Buchníčková        //
// login: xbuchn00                  //
// hlavní konstruktor aplikace      //

package ija.uml;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.control.ScrollPane;


public class Controller {

    double x_pos = 10; 
    double y_pos = 5; 
    boolean first_class = true;
    int i = 0;

    @FXML
    private JFXButton x, y, z;
    @FXML
    private Pane dt_pane;
    @FXML
    private ScrollPane dt_scroll;
    @FXML
    private AnchorPane center;
    @FXML
    private VBox left_menu;


    @FXML
    public void hendleButtonAction(ActionEvent event) {
        if(event.getSource() == x) {
            dt_scroll.toFront();
        }
       /*  else if(event.getSource() == y) {
            yyy.toFront();
        }
        else if(event.getSource() == z) {
            zzz.toFront();
        } */
    } 
    
    @FXML
    public void addClass() {
        UMLClassUI uml_class = new UMLClassUI();
        uml_class.setTitle("Název třídy " + i);
        uml_class.setLayoutX(x_pos);
        uml_class.setLayoutY(y_pos);
        dt_pane.getChildren().add(uml_class);
        if (!first_class) {
            Line line = new Line(x_pos, 80, x_pos - 20, 80);
            dt_pane.getChildren().add(line);
        }
       
        x_pos += 120;
        first_class = false;
        i++;
    }
    
    @FXML
    public void open() {
        CenterPaneUI s_diagram = new CenterPaneUI();
        center.getChildren().add(s_diagram);
        JFXButton sd_button = new JFXButton();
        sd_button.setText("sekvenční diagram x");
        left_menu.getChildren().add(sd_button);
    }
 
    @FXML
    public void edit() {
        Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("edit_window.fxml"));
                Stage stage = new Stage();
                stage.setTitle("My New Stage Title");
                stage.setScene(new Scene(root, 300, 400));
                stage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
    }
}
