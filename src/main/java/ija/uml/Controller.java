// autor: Tereza Buchníčková        //
// login: xbuchn00                  //
// hlavní konstruktor aplikace      //

package ija.uml;

import java.io.File;
import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import ija.uml.items.ClassDiagram;
import ija.uml.items.SequenceDiagram;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import java.util.ArrayList;

public class Controller implements EventHandler<ActionEvent> {

    ClassDiagram classDiagram;
    ArrayList<SequenceDiagramUI> s_diagrams_array_ui; 
    ArrayList<SequenceDiagram> s_diagrams_array; 
    int id_sd = 0;
    JFXButton sd_button;
    ClassDiagramUI c_diagram_UI;
    

    @FXML
    private JFXButton c_diagram_button;
    @FXML
    private AnchorPane center;
    @FXML
    private VBox left_menu;
    
    @FXML
    public void initialize() {
        classDiagram = new ClassDiagram("New");
        s_diagrams_array_ui = new ArrayList<SequenceDiagramUI>();
        s_diagrams_array = new ArrayList<SequenceDiagram>();
        c_diagram_UI = new ClassDiagramUI(classDiagram); 
        center.getChildren().add(c_diagram_UI); 
    }

    @FXML
    public void addClassWindow() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("add_edit_ui.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Přidat třídu");
                stage.setScene(new Scene(loader.load(), 400, 400));
                stage.initModality(Modality.APPLICATION_MODAL);
                AddEditUI controller = loader.getController();
                controller.init(classDiagram, false, null);
                stage.showAndWait();
                c_diagram_UI.draw(); //vykresledni diagramu trid
            }
            catch (IOException e) {
                e.printStackTrace();
            }
    }

    @FXML
    public void addRelWindow() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("add_edit_rel_ui.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Vztahy");
                stage.setScene(new Scene(loader.load(), 400, 400));
                stage.initModality(Modality.APPLICATION_MODAL);
                AddEditRelUI controller = loader.getController();
                controller.init(classDiagram);
                stage.showAndWait();
                c_diagram_UI.draw(); //vykresledni diagramu trid
            }
            catch (IOException e) {
                e.printStackTrace();
            }
    }

    @FXML
    public void addObjWindow() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("add_obj_ui.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Přidání objektu");
                stage.setScene(new Scene(loader.load(), 400, 400));
                stage.initModality(Modality.APPLICATION_MODAL);
                AddObjUI controller = loader.getController();
                //TODO poslat správný sekvenční diagram
                controller.init(s_diagrams_array.get(0), classDiagram);
                stage.showAndWait();
                s_diagrams_array_ui.get(0).draw(); //vykresledni diagramu trid
                //TODO poslat správný sekvenční diagram UI
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

        
        SequenceDiagram s_diagram = new SequenceDiagram("Sequence Diagram", id_sd);
        s_diagrams_array.add(s_diagram);
        SequenceDiagramUI s_diagram_ui = new SequenceDiagramUI(s_diagram);
        String id = Integer.toString(id_sd);
        s_diagram_ui.setId(id);
        s_diagrams_array_ui.add(s_diagram_ui);
        center.getChildren().add(s_diagram_ui);
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
            c_diagram_UI.toFront();
        }
        if(event.getSource() == sd_button){
            JFXButton sd_button = (JFXButton) event.getTarget();
            for (SequenceDiagramUI sd: s_diagrams_array_ui) {
                if (sd.getId() == sd_button.getId()) {
                    sd.toFront();
                }
            }

        }
    }
}
