// autor: Tereza Buchníčková        //
// login: xbuchn00                  //
//      //

package ija.uml;

import java.io.IOException;

import ija.uml.items.ClassDiagram;
import ija.uml.items.UMLClass;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.fxml.FXML;

public class ClassDiagramUI extends ScrollPane {

    ClassDiagram classDiagram;
    double x_pos = 10; 
    double y_pos = 5; 
    double x_space = 170;
    double width = 150;
    double point_space = 20;
    boolean first_class = true;

    @FXML
    private Pane center_pane;
    
    public ClassDiagramUI(ClassDiagram classDiagram) { 
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("center_pane_ui.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.classDiagram = classDiagram; 
    }

    public void draw() {
        x_pos = 10; 
        y_pos = 5; 
        center_pane.getChildren().clear();
        for (UMLClass umlClass : classDiagram.classes) {
            addClass(umlClass);
        }
    }
    
    @FXML
    public void addClass(UMLClass umlClass) {
        UMLClassUI umlClassUI = new UMLClassUI(umlClass, x_pos, y_pos);
        umlClassUI.setLayoutX(x_pos);
        umlClassUI.setLayoutY(y_pos);
        center_pane.getChildren().add(umlClassUI);
        x_pos += x_space;
        first_class = false;
    } 

    
}
