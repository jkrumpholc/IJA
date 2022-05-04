// autor: Tereza Buchníčková        //
// login: xbuchn00                  //
//      //

package ija.uml;

import java.io.IOException;

import ija.uml.items.ClassDiagram;
import ija.uml.items.UMLClass;
import ija.uml.items.UMLRelation;
import ija.uml.items.UMLRelation.RelType;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
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
        center_pane.getChildren().clear();

        for (UMLClass umlClass : classDiagram.getClasses()) {
             umlClass.setStart(1);
        }
        x_pos = 10; 
        y_pos = 5; 
        for (UMLClass umlClass : classDiagram.getClasses()) {
            addClass(umlClass);
        }
        for (UMLRelation umlRelation : classDiagram.getRelations()) {
            addRelation(umlRelation);
        }
    }
    
    @FXML
    private void addClass(UMLClass umlClass) {
        UMLClassUI umlClassUI = new UMLClassUI(umlClass, classDiagram);
        umlClass.setPosition(x_pos, y_pos);
        umlClassUI.setLayoutX(x_pos);
        umlClassUI.setLayoutY(y_pos);
        center_pane.getChildren().add(umlClassUI);
        x_pos += x_space;
        first_class = false;
    }
    
    @FXML
    private void addRelation(UMLRelation umlRelation) {
        RelType type = umlRelation.getType();
        UMLClass umlClass1 = umlRelation.getClassFrom();
        double x = umlClass1.getXPosition();
        int point = umlClass1.getStart();
        if(point < 8){
            x = x + (point * point_space); //x pozice zacatku cary
        double y_end = y_pos + width; //konec ctverce 

        Line line1 = new Line();
        line1.setStartX(x);
        line1.setStartY(y_end);
        line1.setEndX(x);
        double y_end_line = y_end + (20 * point);
        line1.setEndY(y_end_line);
        center_pane.getChildren().add(line1);

        umlClass1.setStart(point + 1); //nastaveni mista, ze ktereho vychazi cara 
 
        UMLClass umlClass2 = umlRelation.getClassTo();
        double x2 = umlClass2.getXPosition();
        int point2 = umlClass2.getStart();
        x2 = x2 + (point2 * point_space); //x pozice konce cary

        Line line2 = new Line(x, y_end_line, x2, y_end_line);
        center_pane.getChildren().add(line2);

        Line line3 = new Line();
        line3.setStartX(x2);
        line3.setStartY(y_end_line);
        line3.setEndX(x2);
        y_end_line = y_end_line - (20 * point);
        line3.setEndY(y_end_line);
        center_pane.getChildren().add(line3);
        /* if(type == RelType.ASSOC) {
            Line arrow1 = new Line();
            arrow1.setStartX(x2);
            arrow1.setStartY(y_end_line);
            arrow1.setEndX(x2 - 5);
            arrow1.setEndY(y_end_line + 7);
            center_pane.getChildren().add(arrow1);
            Line arrow2 = new Line();
            arrow2.setStartX(x2);
            arrow2.setStartY(y_end_line);
            arrow2.setEndX(x2 + 5);
            arrow2.setEndY(y_end_line + 7);
            center_pane.getChildren().add(arrow2);
        } */
        if(type == RelType.COMPOS) {
            Polygon polygon = new Polygon();
            polygon.getPoints().addAll(new Double[]{
            x2, y_end_line,
            x2 - 5, y_end_line + 7,
            x2, y_end_line + 14,
            x2 + 5, y_end_line + 7});
            polygon.setFill(Color.BLACK);
            center_pane.getChildren().add(polygon);
        }
        if(type == RelType.AGGR) {
            Polygon polygon = new Polygon();
            polygon.getPoints().addAll(new Double[]{
            x2, y_end_line,
            x2 - 5, y_end_line + 7,
            x2, y_end_line + 14,
            x2 + 5, y_end_line + 7});
            polygon.setFill(Color.WHITE);
            polygon.setStroke(Color.BLACK);
            center_pane.getChildren().add(polygon);
        }
        if(type == RelType.GENER) {
            Polygon polygon = new Polygon();
            polygon.getPoints().addAll(new Double[]{
            x2, y_end_line,
            x2 - 5, y_end_line + 7,
            x2 + 5, y_end_line + 7 });
            polygon.setFill(Color.WHITE);
            polygon.setStroke(Color.BLACK);
            center_pane.getChildren().add(polygon);
        }
        umlClass2.setStart(point2 + 1);
        }
         
    }
}
