// autor: Tereza Buchníčková        //
// login: xbuchn00                  //
//      //

package ija.uml;

import java.io.IOException;

import ija.uml.items.ClassDiagram;
import ija.uml.items.UMLClass;
import ija.uml.items.UMLRelation;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.fxml.FXML;

public class ClassDiagramUI extends ScrollPane {

    ClassDiagram classDiagram;
    double x_pos = 10; 
    double y_pos = 5; 
    double x_space = 170;
    double width = 150;
    double point_space = 20;
    boolean first_class = true;
    String classFrom;
    String classTo;

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
        for (UMLRelation umlRelation : classDiagram.getRelations()) {
            addRelation(umlRelation);
        }
    }
    
    @FXML
    private void addClass(UMLClass umlClass) {
        UMLClassUI umlClassUI = new UMLClassUI(umlClass);
        umlClass.setPosition(x_pos, y_pos);
        umlClassUI.setLayoutX(x_pos);
        umlClassUI.setLayoutY(y_pos);
        center_pane.getChildren().add(umlClassUI);
        x_pos += x_space;
        first_class = false;
    }
    
    @FXML
    private void addRelation(UMLRelation umlRelation) {
        UMLClass umlClass1 = classDiagram.findClass(umlRelation.getClassFrom());
        double x = umlClass1.getXPosition();
        double y = umlClass1.getYPosition();
        int startPos = umlClass1.getStart();
        x = x + (startPos * point_space);
        y = y + width;

        Line line = new Line();
        line.setStartX(x);
        line.setStartY(y);
        line.setEndX(x);
        line.setEndY(y + 30);
        
        center_pane.getChildren().add(line);

        umlClass1.setStart(startPos + 1);

        UMLClass umlClass2 = classDiagram.findClass(umlRelation.getClassTo());
    }

    //TODO vyřešit kreslení čar ve více řadách

    
}
