// autor: Tereza Buchníčková        //
// login: xbuchn00                  //
// hlavní konstruktor aplikace      //

package ija.uml;

import java.io.File;
import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import ija.uml.items.ClassDiagram;
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
import java.util.ArrayList;

public class Controller implements EventHandler<ActionEvent> {

    ClassDiagram classDiagram;
    ArrayList<SequenceDiagramUI> s_diagrams_array = new ArrayList<SequenceDiagramUI>(); 
    int id_sd = 0;
    JFXButton sd_button;
    ClassDiagramUI c_diagram;
    

    @FXML
    private JFXButton c_diagram_button;
    @FXML
    private AnchorPane center;
    @FXML
    private VBox left_menu;
    

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
    public void open() {
        FileChooser file_chooser = new FileChooser();
        File selected_file = file_chooser.showOpenDialog(null);
        if (selected_file != null) {
            System.out.println(selected_file);
        } else {
            System.out.println("Chyba při otevření souboru");
        }

        ClassDiagramUI c_diagram = new ClassDiagramUI(classDiagram); 
        center.getChildren().add(c_diagram);

        SequenceDiagramUI s_diagram = new SequenceDiagramUI();
        String id = Integer.toString(id_sd);
        s_diagram.setId(id);
        s_diagrams_array.add(s_diagram);
        center.getChildren().add(s_diagram);
        sd_button = new JFXButton();
        sd_button.setText("sekvenční diagram x");
        sd_button.setPrefWidth(200);
        sd_button.setOnAction(this); 
        sd_button.setId(id);
        left_menu.getChildren().add(sd_button);
        id_sd++; 

    }

    @Override
    public void handle(ActionEvent event) {
        if(event.getSource() == c_diagram_button) {
            c_diagram.toFront();
        }
        if(event.getSource() == sd_button){
            JFXButton sd_button = (JFXButton) event.getTarget();
            for (SequenceDiagramUI sd: s_diagrams_array) {
                if (sd.getId() == sd_button.getId()) {
                    sd.toFront();
                }
            }

        }
    }
}
