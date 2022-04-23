// autor: Tereza Buchníčková        //
// login: xbuchn00                  //
// hlavní konstruktor aplikace      //

package ija.uml;

import java.io.Console;
import java.io.File;
import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.control.ScrollPane;
import java.util.ArrayList;

public class Controller implements EventHandler<ActionEvent> {

    ArrayList<CenterPaneUI> s_diagrams_array = new ArrayList<CenterPaneUI>(); 
    double x_pos = 10; 
    double y_pos = 5; 
    boolean first_class = true;
    int i = 0;
    JFXButton sd_button;
    

    @FXML
    private JFXButton dt_button, y, z;
    @FXML
    private Pane dt_pane;
    @FXML
    private ScrollPane dt_scroll;
    @FXML
    private AnchorPane center;
    @FXML
    private VBox left_menu;

    
    @FXML
    public void addClass() {
        UMLClassUI uml_class = new UMLClassUI();
        uml_class.setTitle("Název třídy ");
        uml_class.setLayoutX(x_pos);
        uml_class.setLayoutY(y_pos);
        dt_pane.getChildren().add(uml_class);
        if (!first_class) {
            Line line = new Line(x_pos, 80, x_pos - 20, 80);
            dt_pane.getChildren().add(line);
        }
       
        x_pos += 120;
        first_class = false;
    }
    
    @FXML
    public void sd() {
        CenterPaneUI s_diagram = new CenterPaneUI();
        String id = Integer.toString(i);
        s_diagram.setId(id);
        s_diagrams_array.add(s_diagram);
        center.getChildren().add(s_diagram);
        sd_button = new JFXButton();
        sd_button.setText("sekvenční diagram x");
        sd_button.setPrefWidth(200);
        sd_button.setOnAction(this); 
        sd_button.setId(id);
        left_menu.getChildren().add(sd_button);
        i++;
    }

 
    @FXML
    public void addClassWindow() {
        Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("add_class_ui.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Přidat třídu");
                stage.setScene(new Scene(root, 300, 400));
                stage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
    }
    
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

    @FXML
    public void open() {
        FileChooser file_chooser = new FileChooser();
        File selected_file = file_chooser.showOpenDialog(null);
        if (selected_file != null) {
            System.out.println(selected_file);
        } else {
            System.out.println("Chyba");
        }
    }

    @Override
    public void handle(ActionEvent event) {
        if(event.getSource() == dt_button) {
            dt_scroll.toFront();
        }
        if(event.getSource() == sd_button){
            JFXButton sd_button = (JFXButton) event.getTarget();
            for (CenterPaneUI sd: s_diagrams_array) {
                if (sd.getId() == sd_button.getId()) {
                    sd.toFront();
                }
            }

        }
    }
}
