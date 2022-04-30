// autor: Tereza Buchníčková        //
// login: xbuchn00                  //
//      //

package ija.uml;

import java.io.IOException;

import ija.uml.items.SequenceDiagram;
import ija.uml.items.UMLObject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class SequenceDiagramUI extends ScrollPane {

    SequenceDiagram seqDiagram;

    double x_pos = 10; 
    double y_pos = 5; 
    double x_space = 140;

    @FXML
    private Pane center_pane;
    
    public SequenceDiagramUI(SequenceDiagram seqDiagram) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("center_pane_ui.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.seqDiagram = seqDiagram; 

    }

    public void draw() {
        center_pane.getChildren().clear();
        x_pos = 10; 
        y_pos = 5; 

        for (UMLObject object : seqDiagram.getObjects()) {
            //TODO vykresit jen objekty, které se mají hned nakreslit
            drawObj(object);
        } 
    }

    public void drawObj(UMLObject object) {
        TextArea umlObject = new TextArea(object.getName());
        //TODO přidat typ
        umlObject.setEditable(false);
        umlObject.setPrefSize(80, 20);
        umlObject.setLayoutX(x_pos);
        umlObject.setLayoutY(y_pos);
        center_pane.getChildren().add(umlObject);
        x_pos += x_space;

        Line line = new Line(50, 45, 50, 400);
        line.getStrokeDashArray().addAll(2d);
        center_pane.getChildren().add(line);
    }
}

